package com.example.october

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase

class MainActivity4 : AppCompatActivity() {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myRef = database.getReference("user_profiles")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        val registerButton: View = findViewById(R.id.registerButton)
        registerButton.setOnClickListener {
            if (validateFields()) {
                // All fields are filled, proceed to the next activity (MainActivity5)
                registerUser()
            } else {
                // Notify the user that some fields are empty
                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateFields(): Boolean {
        // Validate if all the fields are filled. Customize this based on your needs.
        return isFieldFilled(R.id.button1) &&
                isFieldFilled(R.id.button2) &&
                isFieldFilled(R.id.button3) &&
                isFieldFilled(R.id.button4) &&
                isFieldFilled(R.id.button5) &&
                isFieldFilled(R.id.button6)
    }

    private fun isFieldFilled(editTextId: Int): Boolean {
        val editText: View = findViewById(editTextId)
        if (editText is TextInputEditText) {
            return editText.text?.toString()?.isNotBlank() == true
        }
        return false
    }

    private fun registerUser() {
        // Retrieve data from TextInputEditText fields
        val name = findViewById<TextInputEditText>(R.id.button1).text.toString()
        val dob = findViewById<TextInputEditText>(R.id.button2).text.toString()
        val designation = findViewById<TextInputEditText>(R.id.button3).text.toString()
        val collegeName = findViewById<TextInputEditText>(R.id.button4).text.toString()
        val district = findViewById<TextInputEditText>(R.id.button5).text.toString()
        val phoneNumber = findViewById<TextInputEditText>(R.id.button6).text.toString()

        // Create user object
        val user = User(name, dob, designation, collegeName, district, phoneNumber)

        // Store user data in Firebase Realtime Database
        val userId = myRef.push().key
        userId?.let {
            myRef.child(it).setValue(user)
        }

        // Create an intent and pass data to MainActivity5
        val intent = Intent(this, MainActivity5::class.java).apply {
            putExtra("userId", userId)
        }

        startActivity(intent)
    }
}
