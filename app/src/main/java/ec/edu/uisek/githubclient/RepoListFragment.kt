package ec.edu.uisek.githubclient

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoListFragment : Fragment(R.layout.fragment_repo_list) {

    private lateinit var adapter: ReposAdapter
    private val misDatos = mutableListOf<RepoItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_repos)
        val fab = view.findViewById<FloatingActionButton>(R.id.fab_add)

        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = ReposAdapter(misDatos,
            onEditClick = { repo -> irAFormulario(repo) },
            onDeleteClick = { repo -> eliminarRepoApi(repo) }
        )
        recyclerView.adapter = adapter
        cargarReposDeApi()
        fab.setOnClickListener {
            irAFormulario(null)
        }
    }

    private fun cargarReposDeApi() {
        RetrofitClient.instance.listRepos().enqueue(object : Callback<List<RepoItem>> {
            override fun onResponse(call: Call<List<RepoItem>>, response: Response<List<RepoItem>>) {
                if (response.isSuccessful) {
                    response.body()?.let { repos ->
                        adapter.updateList(repos)
                    }
                } else {
                    Toast.makeText(context, "Error al cargar repos", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<RepoItem>>, t: Throwable) {
                Toast.makeText(context, "Fallo de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun eliminarRepoApi(repo: RepoItem) {
        val usuario = repo.owner?.login ?: return

        RetrofitClient.instance.deleteRepo(usuario, repo.name).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Eliminado", Toast.LENGTH_SHORT).show()
                    cargarReposDeApi()
                } else {
                    Toast.makeText(context, "No se pudo eliminar", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(context, "Error de red", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun irAFormulario(repo: RepoItem?) {
        val fragment = ProjectFormFragment()
        if (repo != null) {
            val bundle = Bundle()
            bundle.putString("repo_name", repo.name)
            bundle.putString("repo_desc", repo.description)
            bundle.putString("repo_owner", repo.owner?.login)
            fragment.arguments = bundle
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}