package com.example.netologiproject_xml.presentation.dilog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.netologiproject_xml.R

class NameDialogFragment : DialogFragment() {


    interface OnNamePassListener {
        fun onNamePass(name: String)
    }

    private var namePassListener: OnNamePassListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            namePassListener = targetFragment as? OnNamePassListener
            if (namePassListener == null) {
                throw ClassCastException("Target fragment must implement OnNamePassListener")
            }
        } catch (e: ClassCastException) {
            throw ClassCastException("${parentFragment?.javaClass?.simpleName} must implement OnNamePassListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_name_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val editTextName: EditText = view.findViewById(R.id.editNameText)
        val buttonConfirm: Button = view.findViewById(R.id.buttonConfirm)


        buttonConfirm.setOnClickListener {
            val name = editTextName.text.toString()
            namePassListener?.onNamePass(name)
            dismiss()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = NameDialogFragment()
    }
}
