package ec.edu.uisek.githubclient

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.google.android.material.textfield.TextInputEditText

class ProjectFormFragment : Fragment(R.layout.fragment_project_form) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnCancel = view.findViewById<Button>(R.id.btn_cancel)
        val btnSave = view.findViewById<Button>(R.id.btn_save)
        val etName = view.findViewById<TextInputEditText>(R.id.et_project_name)
        val etDesc = view.findViewById<TextInputEditText>(R.id.et_project_desc)

        btnCancel.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        btnSave.setOnClickListener {
            val nombre = etName.text.toString()
            val descripcion = etDesc.text.toString()

            val bundle = Bundle()
            bundle.putString("nuevo_nombre", nombre)
            bundle.putString("nueva_desc", descripcion)

            setFragmentResult("nuevo_repo", bundle)

            parentFragmentManager.popBackStack()
        }
    }
}