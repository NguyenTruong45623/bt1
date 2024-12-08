package com.example.bt1

import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.io.File
import java.io.FileInputStream


class Home : Fragment(R.layout.fragment_home) {

    fun RedToFile () : String {
        val publicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)

        val file = File(publicDir, "test.txt")
        val fileInputStream = FileInputStream(file)

        val content = fileInputStream.bufferedReader().use { it.readText() }
        fileInputStream.close()

        return content
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val text : TextView = view.findViewById(R.id.textHome)
        text.text = RedToFile()
    }
}