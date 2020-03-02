package com.omty.damo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private val RC_SIGN_IN = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPreviousLogin()


    }

    private fun checkPreviousLogin(){
        val user = FirebaseAuth.getInstance().currentUser
        Log.d("testt","testt : " + user)


        if(user == null) showLoginWindow()
        else moveToHomeActivtity()
    }

    private fun moveToHomeActivtity(){
        startActivity(Intent(this,HomeActivity::class.java))
    }

    private fun showLoginWindow(){
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build())

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser

                Log.d("test","testt: "+user)
                startActivity(Intent(this, HomeActivity::class.java))
                // ...
            } else {
                Toast.makeText(this, "로그인 실패, 로그인을 다시 시도해주세요", Toast.LENGTH_LONG).show()
            }
        }
    }
}
