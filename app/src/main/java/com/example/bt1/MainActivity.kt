package com.example.bt1

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.example.bt1.interfaces.IEmailSender
import com.example.bt1.interfaces.IPassWordSender
import java.util.Locale

class MainActivity : AppCompatActivity(), IPassWordSender, IEmailSender {
    private lateinit var btnEmail: Button
    private lateinit var btnPW : Button
    private lateinit var passWord : TextView
    private lateinit var email : TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        var currentLanguage = sharedPreferences.getString("language", "en") ?: "en"

        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnEmail = findViewById(R.id.btnEmail)
        btnPW = findViewById(R.id.btnPW)
        email = findViewById(R.id.tvEmail)
        passWord = findViewById(R.id.tvPW)

        btnEmail.setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(android.R.id.content, EmailInputFragment())
                addToBackStack(null)
            }
        }

        btnPW.setOnClickListener {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(android.R.id.content, PassWordInputFragment())
                addToBackStack(null)
            }
        }

        findViewById<Button>(R.id.btnLG).setOnClickListener {
            val newLanguage = if (currentLanguage == "en") "vi" else "en"

            with(sharedPreferences.edit()) {
                putString("language", newLanguage)
                apply()
            }
            currentLanguage = newLanguage

            recreate() // Tạo lại Activity để áp dụng ngôn ngữ mới
        }

        if (savedInstanceState != null) {
            super.onRestoreInstanceState(savedInstanceState)
        }

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        email.text = savedInstanceState.getString("email")
        passWord.text = savedInstanceState.getString("password")
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString("email", email.text.toString())
            putString("password", passWord.text.toString())
        }
        super.onSaveInstanceState(outState)
    }

    override fun sendEmailToActivity(data: String) {
        Toast.makeText(this, "du lieu da luu $data", Toast.LENGTH_SHORT).show()
        if(data != "") {
            email.text = data
        }
    }

    override fun sendPasswordToActivity(data: String) {
        Toast.makeText(this, "du lieu da luu $data", Toast.LENGTH_SHORT).show()
        if(data != "") {
            passWord.text = data
        }
    }

    override fun attachBaseContext(newBase: Context) {
        val sharedPreferences = newBase.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val language = sharedPreferences.getString("language", "en") ?: "en"

        val locale = Locale(language)
        val config = Configuration(newBase.resources.configuration)
        config.setLocale(locale)

        val context = newBase.createConfigurationContext(config)
        super.attachBaseContext(context)
    }

}
