package com.example.githubbrowser.view.repodetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.githubbrowser.R
import com.example.githubbrowser.viewmodel.RepoDetailViewModel
import kotlinx.android.synthetic.main.fragment_repo_detail.repo_detail_pager as pager
import kotlinx.android.synthetic.main.fragment_repo_detail.repo_detail_tabs as tabs


class RepoDetailFragment : Fragment() {

    companion object {
        fun newInstance() = RepoDetailFragment()
    }

    private val args: RepoDetailFragmentArgs by navArgs()
    private val viewModel by viewModels<RepoDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_repo_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup Pager of TabLayout
        pager.adapter = RepoDetailViewPagerAdapter(
            childFragmentManager,
            resources.getStringArray(R.array.detail_page_title)
        )
        tabs.setupWithViewPager(pager)
    }
}