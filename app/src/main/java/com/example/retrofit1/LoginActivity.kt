package com.example.retrofit1

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.e
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var loginBtn : Button
    private lateinit var userName : EditText
    private lateinit var pass : EditText
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var pd : ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginBtn = findViewById(R.id.buttonLogin)
        userName = findViewById(R.id.editTextUsername)
        pass = findViewById(R.id.editTextPassword)
        pd = ProgressDialog(this)
        firebaseAuth = FirebaseAuth.getInstance()
        loginBtn.setOnClickListener {

            val username = userName.text.toString()
            val password = userName.text.toString()
            pd.setMessage("Please Wait !")
            pd.show()
            if(username.isNotEmpty() && password.isNotEmpty())
            {
                    val useremail = "$username@gmail.com"
                    firebaseAuth.createUserWithEmailAndPassword(useremail,password+"45678").addOnCompleteListener {
                        if(it.isSuccessful)
                        {
                            pd.dismiss()
                           val intent = Intent(this@LoginActivity,MainActivity::class.java)
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            startActivity(intent)
                            finish()
                        }else{
                            pd.dismiss()
                            Log.d("seeing", "onCreate: ${it.exception}")
                            Toast.makeText(this, "${it.exception}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
    }
}