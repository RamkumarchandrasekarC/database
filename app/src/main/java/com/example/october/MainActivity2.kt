package com.example.october



import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2) // Set the correct layout resource

        // Example: If login is triggered programmatically, call the following function
        initiateLogin()
    }

    private fun initiateLogin() {
        // Simulating a login action (replace this with your actual login logic)
        // For demonstration purposes, a delay is added before moving to MainActivity4
        Handler().postDelayed({
            // After the delay, navigate to MainActivity4
            val intent = Intent(this@MainActivity2, MainActivity4::class.java)
            startActivity(intent)
            // Optional: finish MainActivity2 if you don't want to go back to it
            // finish()
        }, 2000) // 30 seconds delay
    }
}



