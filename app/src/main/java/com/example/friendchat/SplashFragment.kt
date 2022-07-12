package com.example.friendchat

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth


class SplashFragment : Fragment() {

    var mAuth : FirebaseAuth? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar!!.hide()

        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        val btn = view.findViewById<Button>(R.id.splashButton)
        mAuth = FirebaseAuth.getInstance()
        btn.setOnClickListener {

            if(mAuth?.currentUser == null) {
                val action = SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                findNavController().navigate(action)
            }
            else {
                val action = SplashFragmentDirections.actionSplashFragmentToMainFragment()
                findNavController().navigate(action)
            }

        }
        return view
    }


}