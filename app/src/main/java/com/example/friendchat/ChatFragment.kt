package com.example.friendchat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatFragment : Fragment() {

    private var recyclerView : RecyclerView? = null
    private var msgAdapter : MessageAdapter? = null
    private var sendtext : EditText? = null
    private lateinit var msgList : ArrayList<Messages>
    private var db : DatabaseReference? = null

    private val args by navArgs<ChatFragmentArgs>()

    var receiverRoom : String? = null
    var senderRoom : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        (activity as AppCompatActivity).supportActionBar!!.show()
        (activity as AppCompatActivity).supportActionBar?.title = args.receiverdetails.name

        //(activity as AppCompatActivity).supportActionBar?.


        val btn = view.findViewById<Button>(R.id.chatsend)

        msgList = ArrayList()
        recyclerView = view.findViewById(R.id.chat_recyclerview)
        msgAdapter = MessageAdapter(requireContext(), msgList)
        recyclerView!!.adapter = msgAdapter
        recyclerView!!.layoutManager = LinearLayoutManager(requireContext())
        sendtext = view.findViewById(R.id.chatedtext)


        val receiveruid = args.receiverdetails.uid
        val senderuid = FirebaseAuth.getInstance().currentUser!!.uid
        senderRoom = receiveruid + senderuid
        receiverRoom = senderuid + receiveruid
        db = FirebaseDatabase.getInstance().getReference()

        db!!.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    msgList.clear()

                    for(postsnapchat in snapshot.children) {

                        val message = postsnapchat.child("msg").getValue(String::class.java)
                        val uid = postsnapchat.child("senderid").getValue(String::class.java)
                        val msg = Messages(message!!, uid!!)

                        //val msg = postsnapchat.getValue(Messages::class.java)
                        msgList.add(msg!!)
                    }
                    msgAdapter!!.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })


        btn.setOnClickListener {
            val msg = sendtext!!.text.toString()
            val msgobj = Messages(msg,senderuid)
            db!!.child("chats").child(senderRoom!!).child("messages").push().setValue(msgobj)
                .addOnCompleteListener {
                    db!!.child("chats").child(receiverRoom!!).child("messages").push().setValue(msgobj)

                }
            sendtext!!.setText("")

        }




        return view
    }
}