package ec.edu.uisek.githubclient

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class RepoListFragment : Fragment(R.layout.fragment_repo_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_repos)
        val fab = view.findViewById<FloatingActionButton>(R.id.fab_add)

        recyclerView.layoutManager = LinearLayoutManager(context)

        val misDatos = listOf(
            RepoItem("Repositorio de prueba", "Esto es para probar que funcione", "Kotlin", R.drawable.ic_launcher_foreground),
            RepoItem("Laboratorio 4", "Cliente de GitHub simulación", "Kotlin", R.drawable.ic_launcher_foreground),
            RepoItem("Laboratorio 5", "El siguiente lab", "Kotlin", R.drawable.ic_launcher_foreground)
        )

        recyclerView.adapter = ReposAdapter(misDatos)

        fab.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProjectFormFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}