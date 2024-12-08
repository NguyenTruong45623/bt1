package com.example.bt1

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment


class EnterEmail : Fragment(R.layout.fragment_enter_email) {
    interface OnDataPass {
        fun onDataEmail(data: String)
    }

    private lateinit var dataPasser: OnDataPass

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = context as? OnDataPass ?: throw ClassCastException("$context must implement OnDataPass")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button: Button = view.findViewById(R.id.btnSubmit)
        button.setOnClickListener {
            val data = view.findViewById<EditText>(R.id.etInput)?.text.toString()
            sendDataToActivity(data)

            parentFragmentManager.beginTransaction()
                .remove(this)
                .commit()
        }
    }

    fun sendDataToActivity(data : String) {
        dataPasser.onDataEmail(data)
    }
}