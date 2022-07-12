package com.example.friendchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    var mAuth : FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navHostFragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.navhostfragment)
        val currentFragment = navHostFragment?.childFragmentManager?.fragments?.get(0)

        if(item.itemId == R.id.logout) {
            mAuth!!.signOut()
            var action = R.id.action_mainFragment_to_loginFragment
            if(currentFragment!!.javaClass == ChatFragment::class.java) {
                Log.i("tag", currentFragment.toString())
                action = R.id.action_chatFragment_to_loginFragment
            }

            findNavController(R.id.navhostfragment).navigate(action)
        }
        else {
            Toast.makeText(applicationContext, "inOnOptionsCreatedMenu", Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }
}