package com.example.githubbrowser.view.repodetail

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class RepoDetailViewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragList = listOf(DescriptionFragment(), ListFragment(), ListFragment())

    override fun getItem(position: Int) = fragList[position]

    override fun getCount() = 3

}