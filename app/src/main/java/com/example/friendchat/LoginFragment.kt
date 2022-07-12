package com.example.friendchat

import android.os.Bundle
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

class LoginFragment : Fragment() {


    var db : DatabaseReference? = null
    var mAuth : FirebaseAuth? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        (activity as AppCompatActivity).supportActionBar!!.show()
        (activity as AppCompatActivity).supportActionBar?.title = "Login"


        val etemail = view.findViewById<EditText>(R.id.loginemail)
        val etpasswd = view.findViewById<EditText>(R.id.loginpassword)
        val btn = view.findViewById<Button>(R.id.loginbtn)
        val login = view.findViewById<TextView>(R.id.logintvlogin)

        db = FirebaseDatabase.getInstance().reference
        mAuth = FirebaseAuth.getInstance()

        btn.setOnClickListener {
            val email = etemail.text.toString()
            val pass = etpasswd.text.toString()

            if(email.trim() <= " " || pass.trim() <= " ") {
                Toast.makeText(requireContext(), "fields cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            mAuth!!.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener {

                    if(it.isSuccessful) {
                        Toast.makeText(requireContext(), "Login Successful", Toast.LENGTH_SHORT).show()
                        val action = LoginFragmentDirections.actionLoginFragmentToMainFragment()
                        findNavController().navigate(action)
                    }
                    else {
                        Toast.makeText(requireContext(), it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }

                }
        }

        login.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }

        return view
    }

}