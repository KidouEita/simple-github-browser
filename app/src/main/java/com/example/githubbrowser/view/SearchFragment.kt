package com.example.githubbrowser.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubbrowser.R
import com.example.githubbrowser.ui.RepoRecyclerViewAdapter
import com.example.githubbrowser.util.OnItemClickListener
import com.example.githubbrowser.util.addOnItemClickListener
import com.example.githubbrowser.viewmodel.SearchViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_search.search_list as list
import kotlinx.android.synthetic.main.fragment_search.search_progress_bar as progressBar
import kotlinx.android.synthetic.main.fragment_search.search_searchbar as searchBar
import kotlinx.android.synthetic.main.fragment_search.search_text as resultText

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel
    private lateinit var queryString: String
    private val args: SearchFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        queryString = args.queryString
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        with(searchBar) {
            setQuery(args.queryString, false)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.run { startSearch(query) }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
        }

        with(list) {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
        }

        setupObserver(view)
        startSearch(args.queryString)
    }

    private fun setupObserver(view: View) {
        with(viewModel) {

            repoList.observe(viewLifecycleOwner, Observer { repos ->
                list.adapter = RepoRecyclerViewAdapter(repos)

                resultText.text =
                    if (repos.isNotEmpty())
                        resources.getString(R.string.search_result, queryString)
                    else
                        resources.getString(R.string.search_no_match, queryString)

                progressBar.visibility = View.GONE

                list.addOnItemClickListener(object : OnItemClickListener {
                    override fun onItemClicked(position: Int, view: View) {
                        val action =
                            SearchFragmentDirections.actionSearchFragmentToRepoDetailFragment(
                                repos[position].title,
                                repos[position].owner.name
                            )
                        findNavController().navigate(action)
                    }
                })
            })

            loadError.observe(viewLifecycleOwner, Observer {
                Snackbar.make(view, it.message.toString(), Snackbar.LENGTH_LONG)
                progressBar.visibility = View.GONE
            })
        }
    }

    private fun startSearch(searchString: String) {
        viewModel.startSearch(searchString)
        queryString = searchString
        progressBar.visibility = View.VISIBLE
    }
}
