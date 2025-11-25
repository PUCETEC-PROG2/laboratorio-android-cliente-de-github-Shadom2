package ec.edu.uisek.githubclient

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import ec.edu.uisek.githubclient.databinding.FragmentNewProjectBinding
import ec.edu.uisek.githubclient.models.Repo
import ec.edu.uisek.githubclient.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewProjectFragment : DialogFragment() {

    private lateinit var binding: FragmentNewProjectBinding

    private var isEditMode = false
    private var originalOwner: String? = null
    private var originalName: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentNewProjectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let { args ->
            val name = args.getString("repo_name")
            val desc = args.getString("repo_desc")
            originalOwner = args.getString("repo_owner")

            if (name != null) {
                isEditMode = true
                originalName = name

                binding.etProjectName.setText(name)
                binding.etProjectDesc.setText(desc)
                binding.btnSave.text = "Actualizar"

                binding.etProjectName.isEnabled = false
            }
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnSave.setOnClickListener {
            val name = binding.etProjectName.text.toString()
            val desc = binding.etProjectDesc.text.toString()

            if (isEditMode) {
                actualizarRepo(name, desc)
            } else {
                crearRepo(name, desc)
            }
        }
    }

    private fun crearRepo(name: String, desc: String) {
        val repo = Repo(name, desc, "Kotlin", null)
        RetrofitClient.instance.createRepo(repo).enqueue(object : Callback<Repo> {
            override fun onResponse(call: Call<Repo>, response: Response<Repo>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Creado", Toast.LENGTH_SHORT).show()
                    cerrarYActualizar()
                } else {
                    Toast.makeText(context, "Error al crear", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Repo>, t: Throwable) {}
        })
    }

    private fun actualizarRepo(name: String, desc: String) {
        if (originalOwner == null || originalName == null) return

        val repoData = Repo(name, desc, null, null)

        RetrofitClient.instance.updateRepo(originalOwner!!, originalName!!, repoData).enqueue(object : Callback<Repo> {
            override fun onResponse(call: Call<Repo>, response: Response<Repo>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Actualizado!", Toast.LENGTH_SHORT).show()
                    cerrarYActualizar()
                } else {
                    Toast.makeText(context, "Error al actualizar: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Repo>, t: Throwable) {
                Toast.makeText(context, "Error de red", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun cerrarYActualizar() {
        setFragmentResult("repo_created", Bundle())
        dismiss()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}