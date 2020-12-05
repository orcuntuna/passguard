package com.arkkod.passguard

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var inputEmail: EditText
    private lateinit var inputPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        inputEmail = findViewById(R.id.inputEmail)
        inputPassword = findViewById(R.id.inputPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonRegister = findViewById(R.id.buttonRegister)
    }
    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        buttonLogin.setOnClickListener(){
            var email = inputEmail.text.toString()
            var password = inputPassword.text.toString()
            checkUserLogin(email, password)
        }
    }
    fun checkUserLogin(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){ task ->
            if (task.isSuccessful){
                val user = mAuth.currentUser
                redirectHomepage(user)
            }else{
                Toast.makeText(baseContext, getString(R.string.login_error_message), Toast.LENGTH_LONG).show()
            }
        }
    }
    fun redirectHomepage(user: FirebaseUser?){
        // anasayfaya gönderme yapılacak
    }
}