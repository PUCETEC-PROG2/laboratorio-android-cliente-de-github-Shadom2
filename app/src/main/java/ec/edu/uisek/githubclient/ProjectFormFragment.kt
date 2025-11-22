package ec.edu.uisek.githubclient

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectFormFragment : Fragment(R.layout.fragment_project_form) {

    private var isEditMode = false
    private var ownerName: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etName = view.findViewById<TextInputEditText>(R.id.et_project_name)
        val etDesc = view.findViewById<TextInputEditText>(R.id.et_project_desc)
        val btnSave = view.findViewById<Button>(R.id.btn_save)
        val btnCancel = view.findViewById<Button>(R.id.btn_cancel)

        arguments?.let {
            val name = it.getString("repo_name")
            val desc = it.getString("repo_desc")
            ownerName = it.getString("repo_owner")

            if (name != null) {
                isEditMode = true
                etName.setText(name)
                etDesc.setText(desc)

                etName.isEnabled = false
                btnSave.text = "Actualizar Repositorio"
            }
        }

        btnCancel.setOnClickListener { parentFragmentManager.popBackStack() }

        btnSave.setOnClickListener {
            val name = etName.text.toString()
            val desc = etDesc.text.toString()

            if (isEditMode) {
                actualizarRepo(name, desc)
            } else {
                crearRepo(name, desc)
            }
        }
    }

    private fun crearRepo(name: String, desc: String) {
        val newRepo = RepoItem(name, desc, "Kotlin", null)
        RetrofitClient.instance.createRepo(newRepo).enqueue(object : Callback<RepoItem> {
            override fun onResponse(call: Call<RepoItem>, response: Response<RepoItem>) {
                if(response.isSuccessful) {
                    Toast.makeText(context, "Creado exitosamente", Toast.LENGTH_SHORT).show()
                    volverALista()
                } else {
                    Toast.makeText(context, "Error al crear: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<RepoItem>, t: Throwable) {
                Toast.makeText(context, "Fallo de red", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun actualizarRepo(name: String, desc: String) {
        if (ownerName == null) return

        val updatedData = RepoItem(name, desc, null, null)
        RetrofitClient.instance.updateRepo(ownerName!!, name, updatedData).enqueue(object : Callback<RepoItem> {
            override fun onResponse(call: Call<RepoItem>, response: Response<RepoItem>) {
                if(response.isSuccessful) {
                    Toast.makeText(context, "Actualizado", Toast.LENGTH_SHORT).show()
                    volverALista()
                } else {
                    Toast.makeText(context, "Error al actualizar", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<RepoItem>, t: Throwable) {
                Toast.makeText(context, "Fallo de red", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun volverALista() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, RepoListFragment())
            .commit()
    }
}