package ec.edu.uisek.githubclient

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RepositoryListFragment : Fragment(R.layout.fragment_repository_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repositoryList = listOf(
            Repository("mi-proyecto-android", "Cliente de GitHub para el laboratorio de UI."),
            Repository("algoritmos-java", "Implementación de algoritmos de búsqueda y ordenamiento."),
            Repository("web-personal", "Código fuente de mi portafolio web personal."),
            Repository("scripts-python", "Pequeños scripts para automatizar tareas."),
            Repository("api-rest-demo", "Un proyecto de ejemplo de API REST con Spring Boot.")
        )

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_repos)
        val adapter = RepositoryAdapter(repositoryList)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        recyclerView.adapter = adapter
    }
}