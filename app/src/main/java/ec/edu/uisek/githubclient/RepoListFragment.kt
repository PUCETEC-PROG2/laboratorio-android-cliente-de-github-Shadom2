package ec.edu.uisek.githubclient

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RepoListFragment : Fragment(R.layout.fragment_repo_list) {

    private lateinit var adapter: ReposAdapter

    private val misDatos = mutableListOf(
        RepoItem("Lab 4", "Simulador de GitHub", "Kotlin"),
        RepoItem("Lab 5", "Añadir repositorios", "Kotlin"),
        RepoItem("Proyecto Web", "E-commerce con React", "Java"),
        RepoItem("Prueba", "Una prueba de repo", "Python")
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_repos)
        val fab = view.findViewById<FloatingActionButton>(R.id.fab_add)

        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = ReposAdapter(misDatos,
            onEditClick = { repo -> irAFormulario(repo) },
            onDeleteClick = { repo -> eliminarRepoLocal(repo) }
        )
        recyclerView.adapter = adapter

        setFragmentResultListener("request_key_repo") { _, bundle ->
            val name = bundle.getString("repo_name") ?: ""
            val desc = bundle.getString("repo_desc") ?: ""
            val lang = bundle.getString("repo_lang") ?: "Desconocido"
            val isEdit = bundle.getBoolean("is_edit")
            val originalName = bundle.getString("original_name")

            if (isEdit) {
                val index = misDatos.indexOfFirst { it.name == originalName }
                if (index != -1) {
                    misDatos[index].name = name
                    misDatos[index].description = desc
                    adapter.notifyItemChanged(index)
                    Toast.makeText(context, "Repositorio actualizado", Toast.LENGTH_SHORT).show()
                }
            } else {
                val nuevoRepo = RepoItem(name, desc, lang)
                misDatos.add(nuevoRepo)
                adapter.notifyItemInserted(misDatos.size - 1)
                Toast.makeText(context, "Repositorio creado", Toast.LENGTH_SHORT).show()
            }
        }

        fab.setOnClickListener {
            irAFormulario(null) // null = Crear nuevo
        }
    }

    private fun eliminarRepoLocal(repo: RepoItem) {
        val index = misDatos.indexOf(repo)
        if (index != -1) {
            misDatos.removeAt(index)
            adapter.notifyItemRemoved(index)
            Toast.makeText(context, "Eliminado: ${repo.name}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun irAFormulario(repo: RepoItem?) {
        val fragment = ProjectFormFragment()
        if (repo != null) {
            val bundle = Bundle()
            bundle.putString("repo_name", repo.name)
            bundle.putString("repo_desc", repo.description)
            fragment.arguments = bundle
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}