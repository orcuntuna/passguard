package com.arkkod.passguard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var inputEmail: EditText
    private lateinit var inputPassword: EditText
    private lateinit var inputPasswordAgain: EditText
    private lateinit var buttonRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.register_register)
        actionbar.setDisplayHomeAsUpEnabled(true)
        mAuth = FirebaseAuth.getInstance()
        inputEmail = findViewById(R.id.inputEmail)
        inputPassword = findViewById(R.id.inputPassword)
        inputPasswordAgain = findViewById(R.id.inputPasswordAgain)
        buttonRegister = findViewById(R.id.buttonRegister)
    }

    override fun onStart() {
        super.onStart()
        buttonRegister.setOnClickListener() {
            val email = inputEmail.text.toString()
            val password = inputPassword.text.toString()
            val passwordAgain = inputPasswordAgain.text.toString()
            if (!email.isNullOrEmpty() && !password.isNullOrEmpty() && !passwordAgain.isNullOrEmpty()) {
                if (password == passwordAgain) {
                    register(email, password)
                } else {
                    showErrorToast(getString(R.string.register_error_password_dont_match))
                }
            } else {
                showErrorToast(getString(R.string.login_error_fields_empty))
            }
        }
    }

    private fun register(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    redirectHomepage()
                } else {
                    showErrorToast(getString(R.string.register_error_message))
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


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}