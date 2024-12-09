package com.example.bt1

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.bt1.interfaces.OnDataEmail
import com.example.bt1.interfaces.OnDataPassWord


class EnterPassWordFragment : Fragment(R.layout.fragment_enter_pass_word), OnDataPassWord {

    private lateinit var dataPassWord: OnDataPassWord

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPassWord = context as? OnDataPassWord ?: throw ClassCastException("$context must implement OnDataPass")
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
        dataPassWord.onDataPassWord(data)
    }

    override fun onDataPassWord(data: String) {
        TODO("Not yet implemented")
    }
}