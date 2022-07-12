package com.example.friendchat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class ChatRecyclerView(val context : Context, val user :List<User>, val chat : Chat) :
    RecyclerView.Adapter<ChatRecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.chat_list,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.setText(user[position].name)

        holder.itemView.setOnClickListener {
            chat.redirect(ChatDetails(user[position].name, user[position].uid))

        }

    }

    override fun getItemCount(): Int {
        return user.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.chatname)
    }
}

interface Chat {

    fun redirect(user: ChatDetails);


}