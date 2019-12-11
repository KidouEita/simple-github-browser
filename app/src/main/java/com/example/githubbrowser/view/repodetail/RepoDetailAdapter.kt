package com.example.githubbrowser.view.repodetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubbrowser.R
import com.example.githubbrowser.model.Commit
import com.example.githubbrowser.model.User

class CommitAdapter(
    private val context: Context,
    private val data: List<Commit>
) :
    RecyclerView.Adapter<CommitAdapter.CommitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_commit, parent, false)
        val vh = CommitViewHolder(item)
        item.layoutParams.height = FrameLayout.LayoutParams.WRAP_CONTENT

        // UI Binding
        with(vh) {
            message = item.findViewById(R.id.item_commit_message)
            avatar = item.findViewById(R.id.item_commit_avatar)
            name = item.findViewById(R.id.item_commit_name)
            date = item.findViewById(R.id.item_commit_date)
        }
        return vh
    }

    override fun onBindViewHolder(holder: CommitViewHolder, position: Int) {
        val model = data[position]
        with(holder) {
            message.text = model.getMessage()
            Glide.with(context)
                .load(model.author?.avatarUrl)
                .error(R.drawable.github_octocat)
                .into(avatar)
            name.text = model.author?.name
            date.text = model.getDate()
        }
    }

    override fun getItemCount() = data.size

    class CommitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var message: TextView
        lateinit var avatar: ImageView
        lateinit var name: TextView
        lateinit var date: TextView
    }
}

class CollaboratorAdapter(
    private val context: Context,
    private val data: List<User>
) :
    RecyclerView.Adapter<CollaboratorAdapter.CollaboratorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollaboratorViewHolder {
        val item =
            LayoutInflater.from(parent.context).inflate(R.layout.item_collaborator, parent, false)
        val vh = CollaboratorViewHolder(item)
        item.layoutParams.height = 300

        // UI Binding
        with(vh) {
            avatar = item.findViewById(R.id.item_col_avatar)
            name = item.findViewById(R.id.item_col_name)
        }
        return vh
    }

    override fun onBindViewHolder(holder: CollaboratorViewHolder, position: Int) {
        val model = data[position]
        with(holder) {
            Glide.with(context)
                .load(model.avatarUrl)
                .error(R.drawable.github_octocat)
                .into(avatar)
            name.text = model.name
        }
    }

    override fun getItemCount() = data.size

    class CollaboratorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var avatar: ImageView
        lateinit var name: TextView
    }
}