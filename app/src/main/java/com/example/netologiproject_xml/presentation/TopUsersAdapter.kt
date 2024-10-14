package com.example.netologiproject_xml.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.netologiproject_xml.R
import com.example.netologiproject_xml.domain.dto.TopUsersDto



class TopUsersAdapter(private var topUsers: List<TopUsersDto>) : RecyclerView.Adapter<TopUsersAdapter.TopUserViewHolder>() {

    class TopUserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById<TextView>(R.id.nameStatisticCard)
        private val pointsTextView: TextView = itemView.findViewById<TextView>(R.id.countPointStatisticCard)

        fun bind(topUser: TopUsersDto) {
            nameTextView.text = topUser.name
            pointsTextView.text = topUser.point.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopUserViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.statistic_card, parent, false)
        return TopUserViewHolder(view)
    }

    override fun getItemCount(): Int = topUsers.size

    override fun onBindViewHolder(holder: TopUserViewHolder, position: Int) {
        holder.bind(topUsers[position])
    }

    fun updateData(newTopUsers: List<TopUsersDto>) {
        topUsers = newTopUsers
        notifyDataSetChanged() // Уведомляем адаптер о изменении данных
    }
}

    class TopUserDiffCallback : DiffUtil.ItemCallback<TopUsersDto>() {
        override fun areItemsTheSame(oldItem: TopUsersDto, newItem: TopUsersDto): Boolean {
            return oldItem.game_id == newItem.game_id
        }

        override fun areContentsTheSame(oldItem: TopUsersDto, newItem: TopUsersDto): Boolean {
            return oldItem == newItem
        }
    }


