package com.example.bt1

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.bt1.interfaces.IEmailSender


class EmailInputFragment : Fragment(R.layout.fragment_enter_email),IEmailSender {

    private lateinit var emailSender: IEmailSender

    override fun onAttach(context: Context) {
        super.onAttach(context)
        emailSender = context as? IEmailSender ?: throw ClassCastException("$context must implement OnDataPass")

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
        emailSender.sendEmailToActivity(data)
    }

    override fun sendEmailToActivity(data: String) {
        TODO("Not yet implemented")
    }

}