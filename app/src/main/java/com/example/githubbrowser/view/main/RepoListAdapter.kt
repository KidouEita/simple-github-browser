package com.example.githubbrowser.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.githubbrowser.R
import com.example.githubbrowser.model.Repo

class RepoListAdapter(private val data: List<Repo>) :
    RecyclerView.Adapter<RepoListAdapter.RepoViewHolder>(), View.OnClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_repo, parent, false)
        val vh = RepoViewHolder(item)
        item.layoutParams.height = 300

        // UI Binding
        with(vh) {
            title = item.findViewById(R.id.item_repo_title)
            author = item.findViewById(R.id.item_repo_author)
        }
        return vh
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val model = data[position]
        with(holder) {
            title.text = model.title
            author.text = model.owner.name
        }
    }

    override fun getItemCount() = data.size

    class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var title: TextView
        lateinit var author: TextView
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}