package com.example.october

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class MainActivity3 : AppCompatActivity() {

    private lateinit var emailTextView: EditText
    private lateinit var passwordTextView: EditText
    private lateinit var btnRegister: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        // Initializing FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance()

        // Initializing all views through id defined above
        emailTextView = findViewById(R.id.usernameEditText)
        passwordTextView = findViewById(R.id.passwordEditText)
        btnRegister = findViewById(R.id.createButton) // Initialize the button
        progressBar = findViewById(R.id.progressBar) // Initialize the progress bar

        // Set on Click Listener on Registration button
        btnRegister.setOnClickListener(View.OnClickListener {
            registerNewUser()
        })
    }

    private fun registerNewUser() {
        // Show the visibility of progress bar to show loading
        progressBar.visibility = View.VISIBLE

        // Take the value of two edit texts in Strings
        val email = emailTextView.text.toString()
        val password = passwordTextView.text.toString()

        // Validations for input email and password
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                applicationContext,
                "Please enter email and password!",
                Toast.LENGTH_LONG
            ).show()
            progressBar.visibility = View.GONE
            return
        }

        // Create new user or register new user
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, object : OnCompleteListener<AuthResult> {
                override fun onComplete(task: Task<AuthResult>) {
                    if (task.isSuccessful) {
                        Toast.makeText(
                            applicationContext,
                            "Registration successful!",
                            Toast.LENGTH_LONG
                        ).show()

                        // Hide the progress bar
                        progressBar.visibility = View.GONE

                        // If the user created intent to login activity
                        val intent = Intent(this@MainActivity3, MainActivity::class.java)
                        startActivity(intent)
                        finish()  // Optional: Close the registration activity
                    } else {
                        // Registration failed
                        Toast.makeText(
                            applicationContext,
                            "Registration failed!! Please try again later",
                            Toast.LENGTH_LONG
                        ).show()

                        // Hide the progress bar
                        progressBar.visibility = View.GONE
                    }
                }
            })
    }
}
