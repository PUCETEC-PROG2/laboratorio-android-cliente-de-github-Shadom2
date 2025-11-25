package ec.edu.uisek.githubclient

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ec.edu.uisek.githubclient.databinding.ActivityMainBinding
import ec.edu.uisek.githubclient.models.Repo
import ec.edu.uisek.githubclient.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ReposAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ReposAdapter(mutableListOf(),
            onEditClick = { repo ->
                val fragment = NewProjectFragment()

                val args = Bundle()
                args.putString("repo_name", repo.name)
                args.putString("repo_desc", repo.description)
                args.putString("repo_owner", repo.owner?.login) // Necesitamos el dueño para la API
                fragment.arguments = args

                fragment.show(supportFragmentManager, "NewProjectFragment")
            },
            onDeleteClick = { repo ->
                eliminarRepo(repo)
            }
        )

        binding.rvRepos.layoutManager = LinearLayoutManager(this)
        binding.rvRepos.adapter = adapter

        cargarRepos()

        binding.fabAdd.setOnClickListener {
            val fragment = NewProjectFragment()
            fragment.show(supportFragmentManager, "NewProjectFragment")
        }

        supportFragmentManager.setFragmentResultListener("repo_created", this) { _, _ ->
            cargarRepos()
        }
    }

    private fun cargarRepos() {
        RetrofitClient.instance.listRepos().enqueue(object : Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                if (response.isSuccessful) {
                    response.body()?.let { adapter.updateList(it) }
                } else {
                    Toast.makeText(this@MainActivity, "Error al cargar: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Fallo de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun eliminarRepo(repo: Repo) {
        val owner = repo.owner?.login

        if (owner == null) {
            Toast.makeText(this, "No se encontró el dueño del repo", Toast.LENGTH_SHORT).show()
            return
        }

        RetrofitClient.instance.deleteRepo(owner, repo.name).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful) {
                    Toast.makeText(this@MainActivity, "Eliminado correctamente", Toast.LENGTH_SHORT).show()
                    cargarRepos()
                } else {
                    Toast.makeText(this@MainActivity, "No se pudo eliminar: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error de red al eliminar", Toast.LENGTH_SHORT).show()
            }
        })
    }
}