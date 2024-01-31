package com.example.shifttest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shifttest.databinding.ItemBinding
import com.squareup.picasso.Picasso

class UsersAdapter(private val userList: ArrayList<Users>, private val listener: (Int) -> Unit): RecyclerView.Adapter<UsersAdapter.UserHolder>() {

    class UserHolder(item: View): RecyclerView.ViewHolder(item){
        val binding = ItemBinding.bind(item)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return UserHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        Picasso.get().load(userList[position].picture).into(holder.binding.imageItem)
        holder.binding.tvName.text =  userList[position].name
        holder.binding.tvEmail.text = userList[position].email
        holder.binding.cardId.setOnClickListener { listener(position) }
    }
}

