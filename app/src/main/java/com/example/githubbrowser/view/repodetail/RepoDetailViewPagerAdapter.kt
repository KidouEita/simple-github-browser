package com.example.githubbrowser.view.repodetail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class RepoDetailViewPagerAdapter(
    fragmentManager: FragmentManager,
    private val pageTitle: Array<String>,
    private val fragList: List<Fragment>
) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int) = fragList[position]

    override fun getPageTitle(position: Int) = pageTitle[position]
    override fun getCount() = fragList.size
}