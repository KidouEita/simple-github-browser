package com.example.githubbrowser.view.repodetail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubbrowser.R
import com.example.githubbrowser.ui.CommitRecyclerViewAdapter
import com.example.githubbrowser.viewmodel.RepoDetailViewModel
import kotlinx.android.synthetic.main.fragment_repo_detail_commit.repo_detail_commit_error_text as errorTextView
import kotlinx.android.synthetic.main.fragment_repo_detail_commit.repo_detail_commit_list as list
import kotlinx.android.synthetic.main.fragment_repo_detail_commit.repo_detail_commit_progress as progressBar


class RepoDetailCommitFragment(
    private val viewModel: RepoDetailViewModel,
    private val args: RepoDetailFragmentArgs
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repo_detail_commit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(list) {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
        }

        viewModel.commitList.observe(viewLifecycleOwner, Observer {
            context?.run {
                list.adapter =
                    CommitRecyclerViewAdapter(this, it)
                progressBar.visibility = View.GONE
            }
        })

        viewModel.loadCommitError.observe(viewLifecycleOwner, Observer {
            progressBar.visibility = View.GONE
            errorTextView.text = it.message.toString()
        })

        viewModel.loadAllCommits(args.repoAuthor, args.repoName)
    }
}