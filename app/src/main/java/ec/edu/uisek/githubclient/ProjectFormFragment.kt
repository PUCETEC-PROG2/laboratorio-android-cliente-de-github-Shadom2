package ec.edu.uisek.githubclient

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.google.android.material.textfield.TextInputEditText

class ProjectFormFragment : Fragment(R.layout.fragment_project_form) {

    private var isEditMode = false
    private var originalName: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etName = view.findViewById<TextInputEditText>(R.id.et_project_name)
        val etDesc = view.findViewById<TextInputEditText>(R.id.et_project_desc)
        val btnSave = view.findViewById<Button>(R.id.btn_save)
        val btnCancel = view.findViewById<Button>(R.id.btn_cancel)

        arguments?.let {
            val name = it.getString("repo_name")
            val desc = it.getString("repo_desc")
            if (name != null) {
                isEditMode = true
                originalName = name
                etName.setText(name)
                etDesc.setText(desc)
                btnSave.text = "Actualizar"
            }
        }

        btnCancel.setOnClickListener { parentFragmentManager.popBackStack() }

        btnSave.setOnClickListener {
            val name = etName.text.toString()
            val desc = etDesc.text.toString()

            val resultBundle = Bundle().apply {
                putString("repo_name", name)
                putString("repo_desc", desc)
                putString("repo_lang", "Kotlin")
                putBoolean("is_edit", isEditMode)
                putString("original_name", originalName)
            }

            setFragmentResult("request_key_repo", resultBundle)

            parentFragmentManager.popBackStack()
        }
    }
}