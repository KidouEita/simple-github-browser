package com.example.githubbrowser.view.repodetail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.githubbrowser.R


// TODO : Adapter
class RepoDetailListFragment(
//    private val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        list.adapter = adapter
    }

}
