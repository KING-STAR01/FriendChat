package com.example.friendchat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val msgList: ArrayList<Messages>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if(viewType == 1) {
            val view = LayoutInflater.from(context).inflate(R.layout.receivedmsgs, parent, false)
            return ReceiveViewHolder(view)
        }
        else {
            val view = LayoutInflater.from(context).inflate(R.layout.sentmsg, parent, false)
            return SentViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentMsg = msgList[position]

        if(holder.javaClass == SentViewHolder::class.java) {
            val viewHolder = holder as SentViewHolder
            holder.msgtv.text = currentMsg.msg
        }
        else {
            val viewHolder = holder as ReceiveViewHolder
            holder.msgtv.text = currentMsg.msg
        }


    }

    override fun getItemViewType(position: Int): Int {

        if (msgList[position].senderid.equals(FirebaseAuth.getInstance().currentUser!!.uid))
            return ITEM_SENT
        else
            return ITEM_RECEIVE
    }


    override fun getItemCount(): Int {
        return msgList.size
    }

    class SentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val msgtv = itemView.findViewById<TextView>(R.id.sentmsgtv)
    }

    class ReceiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val msgtv = itemView.findViewById<TextView>(R.id.receivedmsgtv)
    }

}
