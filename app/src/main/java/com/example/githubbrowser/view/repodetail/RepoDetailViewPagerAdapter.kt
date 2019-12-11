package com.example.githubbrowser.view.repodetail

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class RepoDetailViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val pageTitle: Array<String>
) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragList =
        listOf(RepoDetailListFragment(), RepoDetailListFragment())

    override fun getItem(position: Int) = fragList[position]
    override fun getCount() = fragList.size
    override fun getPageTitle(position: Int) = pageTitle[position]
}