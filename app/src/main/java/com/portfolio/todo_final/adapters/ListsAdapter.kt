package com.portfolio.todo_final.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.todo_final.R
import com.portfolio.todo_final.models.Group
import com.portfolio.todo_final.models.Task
import java.nio.file.attribute.GroupPrincipal

class ListsAdapter(
    private var groups: List<Group>
): RecyclerView.Adapter<ListsAdapter.ViewHolder>() {
    var onItemClick: ((Group) -> Unit)? = null

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val itemGroup: CardView = view.findViewById(R.id.itemGroup)
        private val imageViewIcon: AppCompatImageView = view.findViewById(R.id.imageViewIcon)
        private val textViewLabel: AppCompatTextView = view.findViewById(R.id.textViewLabel)
        private val textViewItemCount: AppCompatTextView = view.findViewById(R.id.textViewItemCount)

        fun setData(group: Group) {
            textViewLabel.text = group.label
            textViewItemCount.text = "${group.itemCount} Items"
            imageViewIcon.setImageResource(group.icon)
        }
    }

    fun updateGroups(newGroups: List<Group>) {
        groups = newGroups
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_group, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int = groups.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentGroup = groups[position]
        holder.setData(currentGroup)

        holder.itemGroup.setOnClickListener {
            onItemClick?.invoke(currentGroup)
        }
    }
}
