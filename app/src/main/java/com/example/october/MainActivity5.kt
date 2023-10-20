package com.example.october
import android.os.Bundle
import android.widget.Button

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.*
import com.example.october.User

// Use the User class in an activity
val user = User("John Doe", "1990-01-01", "Developer", "University", "District A", "123456789")




class MainActivity5 : AppCompatActivity() {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myRef: DatabaseReference = database.getReference("user_profiles")

    private lateinit var userId: String
    private lateinit var nameEditText: TextInputEditText
    private lateinit var dobEditText: TextInputEditText
    private lateinit var designationEditText: TextInputEditText
    private lateinit var collegeNameEditText: TextInputEditText
    private lateinit var districtEditText: TextInputEditText
    private lateinit var phoneNumberEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        userId = intent.getStringExtra("userId") ?: ""
        if (userId.isEmpty()) {
            // Handle the case where user ID is not available
            Toast.makeText(this, "User ID not available", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        nameEditText = findViewById(R.id.textViewName)
        dobEditText = findViewById(R.id.textViewDOB)
        designationEditText = findViewById(R.id.textViewDesignation)
        collegeNameEditText = findViewById(R.id.textViewCollegeName)
        districtEditText = findViewById(R.id.textViewDistrict)
        phoneNumberEditText = findViewById(R.id.textViewPhoneNumber)

        val updateButton: Button = findViewById(R.id.updateButton)

        updateButton.setOnClickListener {
            updateUserData()
            Toast.makeText(this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show()
        }

        // Load existing user profile data
        loadUserProfileData()
    }

    private fun loadUserProfileData() {
        myRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                user?.let { updateUI(it) }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
                Toast.makeText(this@MainActivity5, "Failed to load user data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUI(user: User) {
        nameEditText.setText(user.name)
        dobEditText.setText(user.dob)
        designationEditText.setText(user.designation)
        collegeNameEditText.setText(user.collegeName)
        districtEditText.setText(user.district)
        phoneNumberEditText.setText(user.phoneNumber)
    }

    private fun updateUserData() {
        // Retrieve updated data from TextInputEditText fields
        val newName = nameEditText.text.toString().trim()
        val newDob = dobEditText.text.toString().trim()
        val newDesignation = designationEditText.text.toString().trim()
        val newCollegeName = collegeNameEditText.text.toString().trim()
        val newDistrict = districtEditText.text.toString().trim()
        val newPhoneNumber = phoneNumberEditText.text.toString().trim()

        // Update user object
        val updatedUser = User(newName, newDob, newDesignation, newCollegeName, newDistrict, newPhoneNumber)

        // Update the user data in Firebase Realtime Database
        myRef.child(userId).setValue(updatedUser)
    }
}
