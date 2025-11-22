package ec.edu.uisek.githubclient

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RepoListFragment : Fragment(R.layout.fragment_repo_list) {

    // Hacemos la lista variable global para poder modificarla
    // Usamos 'mutableListOf' para poder agregar cosas
    private val misDatos = mutableListOf(
        RepoItem("Lab 3", "Prueba de desc", "Kotlin", R.drawable.ic_launcher_foreground),
        RepoItem("Lab 4", "Cliente de GitHub simulación", "Kotlin", R.drawable.ic_launcher_foreground),
        RepoItem("Lab 5", "Próximo a hacer", "Kotlin", R.drawable.ic_launcher_foreground)
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_repos)
        val fab = view.findViewById<FloatingActionButton>(R.id.fab_add)

        recyclerView.layoutManager = LinearLayoutManager(context)

        val adapter = ReposAdapter(misDatos)
        recyclerView.adapter = adapter

        setFragmentResultListener("nuevo_repo") { key, bundle ->
            val nombreRecibido = bundle.getString("nuevo_nombre") ?: "Sin nombre"
            val descRecibida = bundle.getString("nueva_desc") ?: "Sin descripción"

            val nuevoRepo = RepoItem(nombreRecibido, descRecibida, "Nuevo", R.drawable.ic_launcher_foreground)

            misDatos.add(nuevoRepo)
            adapter.notifyItemInserted(misDatos.size - 1)
        }

        fab.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProjectFormFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}