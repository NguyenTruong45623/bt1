package com.example.bt1

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.bt1.interfaces.IPassWordSender


class PassWordInputFragment : Fragment(R.layout.fragment_enter_pass_word), IPassWordSender {

    private lateinit var passWordSender: IPassWordSender

    override fun onAttach(context: Context) {
        super.onAttach(context)
        passWordSender = context as? IPassWordSender ?: throw ClassCastException("$context must implement OnDataPass")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button: Button = view.findViewById(R.id.btnSubmit2)

        button.setOnClickListener() {
            val data = view.findViewById<EditText>(R.id.edtInputPW)?.text.toString()
            sendDataToActivity(data)

            parentFragmentManager.beginTransaction()
                .remove(this)
                .commit()
        }
    }

    private fun sendDataToActivity(data : String) {
        passWordSender.sendPasswordToActivity(data)
    }

    override fun sendPasswordToActivity(data: String) {
        TODO("Not yet implemented")
    }


}