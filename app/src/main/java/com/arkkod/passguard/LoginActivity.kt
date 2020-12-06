package com.arkkod.passguard

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var inputEmail: EditText
    private lateinit var inputPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var buttonLoginToRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        inputEmail = findViewById(R.id.inputEmail)
        inputPassword = findViewById(R.id.inputPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonLoginToRegister = findViewById(R.id.buttonLoginToRegister)
    }

    override fun onStart() {
        super.onStart()
        if (mAuth.currentUser !== null) {
            redirectHomepage()
        }
        buttonLogin.setOnClickListener() {
            var email = inputEmail.text.toString()
            var password = inputPassword.text.toString()
            if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                checkUserLogin(email, password)
            } else {
                showErrorToast(getString(R.string.login_error_fields_empty))
            }
        }
        buttonLoginToRegister.setOnClickListener() {
            redirectRegister()
        }
    }

    private fun checkUserLogin(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                redirectHomepage()
            } else {
                showErrorToast(getString(R.string.login_error_message))
            }
        }
    }

    private fun showErrorToast(message: String) {
        Toast.makeText(baseContext, message, Toast.LENGTH_LONG)
            .show()
    }

    private fun redirectHomepage() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    private fun redirectRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}