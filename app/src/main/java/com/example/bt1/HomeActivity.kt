package com.example.bt1

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeActivity : AppCompatActivity() {
    private lateinit var titleText: TextView

    private val requestMultiplePermissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.entries.all { it.value }
        if (allGranted) {
            titleText.text = "Đã cấp tất cả các quyền"
        } else {
            titleText.text = "Một hoặc nhiều quyền chưa được cấp"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Set the content view *before* accessing any views
        setContentView(R.layout.activity_home)

        titleText = findViewById(R.id.title)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        checkAndRequestPermissions()
    }

    private fun checkAndRequestPermissions() {
        val permissionsArray = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE)
        when {
            permissionsArray.all {  ContextCompat.checkSelfPermission(this, it) ==
                    PackageManager.PERMISSION_GRANTED }-> {
                titleText.text = "Tất cả các quyền đã được cấp"
            }
            permissionsArray.any { ActivityCompat.shouldShowRequestPermissionRationale(this, it) } -> {
                showPermissionRationale(this, permissionsArray)
            }
            else -> {
                requestMultiplePermissionsLauncher.launch(permissionsArray)
            }
        }
    }


    private fun showPermissionRationale(context: Context, permissionsArray: Array<String>) {
        AlertDialog.Builder(context)
            .setTitle("Yêu cầu quyền")
            .setMessage("Ứng dụng cần quyền này để hoạt động đúng. Vui lòng cấp quyền để tiếp tục.")
            .setPositiveButton("OK") { _, _ ->
                requestMultiplePermissionsLauncher.launch(permissionsArray)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}