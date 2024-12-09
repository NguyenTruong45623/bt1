package com.example.bt1

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.bt1.interfaces.OnDataEmail


class EnterEmailFragment : Fragment(R.layout.fragment_enter_email),OnDataEmail {

    private lateinit var dataEmail: OnDataEmail

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataEmail = context as? OnDataEmail ?: throw ClassCastException("$context must implement OnDataPass")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button: Button = view.findViewById(R.id.btnSubmit)
        button.setOnClickListener {
            val data = view.findViewById<EditText>(R.id.edtInputEmail)?.text.toString()
            sendDataToActivity(data)

            parentFragmentManager.beginTransaction()
                .remove(this)
                .commit()
        }
    }

    private fun sendDataToActivity(data : String) {
        dataEmail.onDataEmail(data)
    }

    override fun onDataEmail(data: String) {

    }

}