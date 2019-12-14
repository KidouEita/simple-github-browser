package com.example.githubbrowser.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubbrowser.R
import com.example.githubbrowser.ui.RepoRecyclerViewAdapter
import com.example.githubbrowser.util.OnItemClickListener
import com.example.githubbrowser.util.addOnItemClickListener
import com.example.githubbrowser.util.makeSnackErrorRetryable
import com.example.githubbrowser.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.home_list as list
import kotlinx.android.synthetic.main.fragment_home.home_progress_bar as progressBar
import kotlinx.android.synthetic.main.fragment_home.home_search as searchView

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.run {
                    val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment(query)
                    findNavController().navigate(action)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return searchView.query != newText
            }
        })

        with(list) {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
        }

        setupObserver()
    }

    override fun onResume() {
        super.onResume()
        loadView()
    }

    private fun loadView() {
        progressBar.visibility = View.VISIBLE
        viewModel.loadAllPublicRepos()
    }

    private fun setupObserver() {
        with(viewModel) {
            repoList.observe(viewLifecycleOwner, Observer { repos ->
                list.adapter = RepoRecyclerViewAdapter(repos)
                progressBar.visibility = View.GONE

                list.addOnItemClickListener(object : OnItemClickListener {
                    override fun onItemClicked(position: Int, view: View) {
                        val action = HomeFragmentDirections.actionHomeFragmentToRepoDetailFragment(
                            repos[position].title,
                            repos[position].owner.name
                        )
                        findNavController().navigate(action)
                    }
                })
            })

            loadError.observe(viewLifecycleOwner, Observer {
                makeSnackErrorRetryable(this@HomeFragment, it, View.OnClickListener { loadView() })
                progressBar.visibility = View.GONE
            })
        }
    }
}