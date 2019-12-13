package com.example.githubbrowser.view.repodetail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubbrowser.R
import com.example.githubbrowser.ui.CollaboratorRecyclerViewAdapter
import com.example.githubbrowser.util.OnItemClickListener
import com.example.githubbrowser.util.addOnItemClickListener
import com.example.githubbrowser.util.makeSnackError
import com.example.githubbrowser.viewmodel.RepoDetailViewModel
import kotlinx.android.synthetic.main.fragment_repo_detail_collaborator.repo_detail_collaborator_error_text as errorTextView
import kotlinx.android.synthetic.main.fragment_repo_detail_collaborator.repo_detail_collaborator_list as list
import kotlinx.android.synthetic.main.fragment_repo_detail_collaborator.repo_detail_collaborator_progress as progressBar


class RepoDetailCollaboratorFragment(
    private val viewModel: RepoDetailViewModel,
    private val args: RepoDetailFragmentArgs
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repo_detail_collaborator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(list) {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
        }

        viewModel.collaboratorList.observe(viewLifecycleOwner, Observer { collaborators ->
            context?.run {
                list.adapter =
                    CollaboratorRecyclerViewAdapter(this, collaborators)
                progressBar.visibility = View.GONE
                list.addOnItemClickListener(object : OnItemClickListener {
                    override fun onItemClicked(position: Int, view: View) {
                        val action =
                            RepoDetailFragmentDirections.actionRepoDetailFragmentToAuthorFragment(
                                collaborators[position].name,
                                collaborators[position].avatarUrl
                            )
                        findNavController().navigate(action)
                    }
                })
            }
        })

        viewModel.loadCollaboratorError.observe(viewLifecycleOwner, Observer {
            progressBar.visibility = View.GONE
            errorTextView.text = it.message.toString()
            makeSnackError(this, it)
        })

        viewModel.loadAllCollaborators(args.repoAuthor, args.repoName)
    }
}
