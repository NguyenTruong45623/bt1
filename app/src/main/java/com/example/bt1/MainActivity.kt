package com.example.bt1

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.os.Environment
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
import java.io.File
import java.io.FileWriter
import java.io.IOException
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
        setLocale(currentLanguage)
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
            setLocale(newLanguage)

            recreate()
        }

        if (savedInstanceState != null) {
            super.onRestoreInstanceState(savedInstanceState)
        }

//        login luu vao file
        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            clickRequestPersssion()
            val getContent = "email: ${email.text}\npassword: ${passWord.text}\n"
            writeToFile("test.txt", getContent)

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
        // Call superclass to save any view hierarchy.
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

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun clickRequestPersssion() {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Đã cấp quyền", Toast.LENGTH_SHORT).show()
        } else {
            val permission = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(this, permission, 1)
        }
    }

    private fun writeToFile(fileName: String, content: String)  {
        try {
            val publicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

            val file = File(publicDir, fileName)
            val fileWrite = FileWriter(file, true)
            fileWrite.write(content)

            fileWrite.close()

            Toast.makeText(this, "Ghi file thành công", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Ghi file thất bại", Toast.LENGTH_SHORT).show()
        }

    }

}