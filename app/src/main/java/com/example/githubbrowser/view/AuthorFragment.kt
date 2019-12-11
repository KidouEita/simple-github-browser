package com.example.githubbrowser.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.githubbrowser.R
import com.example.githubbrowser.viewmodel.AuthorViewModel


class AuthorFragment : Fragment() {

    companion object {
        fun newInstance() = AuthorFragment()
    }

    private lateinit var viewModel: AuthorViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_author, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AuthorViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
