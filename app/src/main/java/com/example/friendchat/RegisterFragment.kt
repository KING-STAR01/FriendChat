package com.example.friendchat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterFragment : Fragment() {

    var database : DatabaseReference? = null
    var mAuth : FirebaseAuth? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        (activity as AppCompatActivity).supportActionBar!!.show()
        (activity as AppCompatActivity).supportActionBar?.title = "Register"


        val name = view.findViewById<EditText>(R.id.regname)
        val email = view.findViewById<EditText>(R.id.regemail)
        val pass = view.findViewById<EditText>(R.id.regpassword)
        val reg = view.findViewById<TextView>(R.id.regtvregister)
        val btn = view.findViewById<Button>(R.id.regbtn)

        database = FirebaseDatabase.getInstance().reference
        mAuth = FirebaseAuth.getInstance()


        btn.setOnClickListener {
            val n = name.text.toString()
            val e = email.text.toString()
            val p = pass.text.toString()

            if(n <= " " || e <= " " || p <= " ") {
                Toast.makeText(requireContext(), "all fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            mAuth!!.createUserWithEmailAndPassword(e, p).addOnCompleteListener {
                if(it.isSuccessful) {
                    Toast.makeText(requireContext(), "user registration successful", Toast.LENGTH_SHORT).show()
                    database!!.child("users").child(mAuth!!.currentUser!!.uid).setValue(User(n,e,mAuth!!.currentUser!!.uid))

                    val action = RegisterFragmentDirections.actionRegisterFragmentToMainFragment()
                    findNavController().navigate(action)
                }
                else {
                    Log.i("Tag", it.exception.toString())
                    Toast.makeText(requireContext(), it.exception.toString(), Toast.LENGTH_SHORT).show()

                }
            }


        }

        reg.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }


        return view
    }

}