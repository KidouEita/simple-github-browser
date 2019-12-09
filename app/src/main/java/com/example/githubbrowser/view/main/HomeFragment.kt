package com.example.githubbrowser.view.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubbrowser.R
import com.example.githubbrowser.entity.RepoVO
import com.example.githubbrowser.util.OnItemClickListener
import com.example.githubbrowser.util.addOnItemClickListener
import com.example.githubbrowser.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.home_list as list

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        with(list) {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
            }

            // TODO : Get Data from api
            adapter = RepoListAdapter(listOf(RepoVO("a", "b"), RepoVO("c", "d")))
            list.addOnItemClickListener(object: OnItemClickListener {
                override fun onItemClicked(position: Int, view: View) {
                    Log.d(javaClass.simpleName, position.toString())
                    findNavController().navigate(R.id.action_nav_home_to_repoDetailFragment)
                }
            })
        }
    }
}