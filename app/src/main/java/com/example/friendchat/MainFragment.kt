package com.example.friendchat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainFragment : Fragment(),Chat {

    var db : DatabaseReference? = null
    var adapter : ChatRecyclerView? = null
    var recyclerView : RecyclerView? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        (activity as AppCompatActivity).supportActionBar!!.show()

        (activity as AppCompatActivity).supportActionBar?.title = "Chats"

        db = FirebaseDatabase.getInstance().getReference()
        db = db!!.child("users")

        val userlist = ArrayList<User>()
        getData(userlist, db!!)
        adapter = ChatRecyclerView(view.context,userlist,this)
        recyclerView = view.findViewById(R.id.mainrecyclerview)

        recyclerView!!.layoutManager = LinearLayoutManager(requireContext())
        recyclerView!!.adapter = adapter



        return view
    }

    private fun getData(userlist: ArrayList<User>, db: DatabaseReference) {

        db.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                userlist.clear()
                for (postsnapchat in snapshot.children) {

                    val name = postsnapchat.child("name").getValue(String::class.java)
                    val email = postsnapchat.child("email").getValue(String::class.java)
                    val user = User(name!!,email!!,postsnapchat.key!!)
                    if(!user.uid.equals(FirebaseAuth.getInstance().currentUser!!.uid))
                        userlist.add(user!!)
                    Log.i("Tag", postsnapchat.key!!)
                }
                adapter!!.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


    }

    override fun redirect(user: ChatDetails) {
        val action = MainFragmentDirections.actionMainFragmentToChatFragment(user)
        findNavController().navigate(action)
    }
}