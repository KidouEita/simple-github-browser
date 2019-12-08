package com.example.githubbrowser.view.repodetail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.githubbrowser.R
import kotlinx.android.synthetic.main.fragment_list.list_recycler as list


class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    fun setupListAdapter(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        list.adapter = adapter
    }

}
