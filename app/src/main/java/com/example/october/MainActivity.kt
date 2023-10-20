package com.example.october
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        val loginButton: MaterialButton = findViewById(R.id.loginButton)
        val signupButton: MaterialButton = findViewById(R.id.signUpButton)
        val usernameInputLayout: TextInputLayout = findViewById(R.id.usernameLayout)
        val passwordInputLayout: TextInputLayout = findViewById(R.id.passwordLayout)

        loginButton.setOnClickListener {
            val username = usernameInputLayout.editText?.text.toString().trim()
            val password = passwordInputLayout.editText?.text.toString().trim()

            if (isValidInput(username) && isValidPassword(password)) {
                signInUser(username, password)
            } else {
                if (!isValidInput(username)) {
                    usernameInputLayout.error = "Invalid username"
                }
                if (!isValidPassword(password)) {
                    passwordInputLayout.error = "Password should meet the requirements"
                }
            }
        }

        signupButton.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }
    }

    private fun signInUser(username: String, password: String) {
        mAuth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = mAuth.currentUser
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(
                        this, "Login successful.",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this, MainActivity2::class.java)
                    startActivity(intent)
                    finish()  // Optional: Close the login activity
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        this, "Login failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun isValidInput(input: String): Boolean {
        // Modify the conditions based on your username requirements
        return input.isNotEmpty()
    }

    private fun isValidPassword(password: String): Boolean {
        // Modify the regex pattern or conditions based on your password requirements
        return password.isNotEmpty()

    }
}