package com.example.githubbrowser.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubbrowser.R
import com.example.githubbrowser.util.OnItemClickListener
import com.example.githubbrowser.util.addOnItemClickListener
import com.example.githubbrowser.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.home_list as list
import kotlinx.android.synthetic.main.fragment_home.home_progress_bar as progressBar


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

        with(list) {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
        }

        viewModel.repoList.observe(viewLifecycleOwner, Observer { repos ->
            list.adapter = RepoListAdapter(repos)
            progressBar.visibility = View.GONE

            list.addOnItemClickListener(object : OnItemClickListener {
                override fun onItemClicked(position: Int, view: View) {
                    val action = HomeFragmentDirections.actionHomeFragmentToRepoDetailFragment(
                        repos[position].getRepoName(),
                        repos[position].getAuthorName()
                    )
                    findNavController().navigate(action)
                }
            })
        })

        viewModel.loadError.observe(viewLifecycleOwner, Observer {
            Snackbar.make(view, it.message.toString(), Snackbar.LENGTH_LONG)
            progressBar.visibility = View.GONE
        })
    }

    override fun onResume() {
        super.onResume()
        // TODO : Reload
        viewModel.loadAllPublicRepoList()
    }

    // TODO
    private fun reloadScreen() {
        progressBar.visibility = View.VISIBLE
        viewModel.loadAllPublicRepoList()
    }
}