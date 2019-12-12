package com.example.githubbrowser.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubbrowser.R
import com.example.githubbrowser.model.User

class CollaboratorRecyclerViewAdapter(
    private val context: Context,
    private val data: List<User>
) :
    RecyclerView.Adapter<CollaboratorRecyclerViewAdapter.CollaboratorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollaboratorViewHolder {
        val item =
            LayoutInflater.from(parent.context).inflate(R.layout.item_collaborator, parent, false)
        val vh =
            CollaboratorViewHolder(
                item
            )
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