package com.example.githubbrowser.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.githubbrowser.R
import com.example.githubbrowser.util.OnItemClickListener
import com.example.githubbrowser.util.addOnItemClickListener
import com.example.githubbrowser.ui.RepoRecyclerViewAdapter
import com.example.githubbrowser.viewmodel.AuthViewModel
import com.example.githubbrowser.viewmodel.AuthorViewModel
import kotlinx.android.synthetic.main.fragment_author.author_avatar as avatarView
import kotlinx.android.synthetic.main.fragment_author.author_list as list
import kotlinx.android.synthetic.main.fragment_author.author_name as nameTextView

class MyRepoFragment : Fragment() {

    private lateinit var viewModel: AuthorViewModel
    private val authViewModel by activityViewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProviders.of(this).get(AuthorViewModel::class.java)
        return inflater.inflate(R.layout.fragment_author, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(list) {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
        }

        viewModel.repoList.observe(viewLifecycleOwner, Observer { repos ->
            list.adapter = RepoRecyclerViewAdapter(repos)

            list.addOnItemClickListener(object : OnItemClickListener {
                override fun onItemClicked(position: Int, view: View) {
                    val action = MyRepoFragmentDirections.actionMyRepoFragmentToRepoDetailFragment(
                        repos[position].title,
                        repos[position].owner.name
                    )
                    findNavController().navigate(action)
                }
            })
        })
    }

    override fun onResume() {
        super.onResume()

        authViewModel.userData.value?.run {

            Glide.with(context!!)
                .load(avatarUrl)
                .error(R.drawable.github_octocat)
                .into(avatarView)

            nameTextView.text = name
        }

        viewModel.loadRepos()

    }
}