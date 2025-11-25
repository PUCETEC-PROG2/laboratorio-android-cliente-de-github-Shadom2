package ec.edu.uisek.githubclient

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ec.edu.uisek.githubclient.models.Repo

class ReposAdapter(
    private var repos: MutableList<Repo>,
    private val onEditClick: (Repo) -> Unit,
    private val onDeleteClick: (Repo) -> Unit
) : RecyclerView.Adapter<ReposAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tv_name)
        val desc: TextView = view.findViewById(R.id.tv_description)
        val lang: TextView = view.findViewById(R.id.tv_language)
        val btnDelete: ImageView = view.findViewById(R.id.btn_delete)
        val btnEdit: ImageView = view.findViewById(R.id.btn_edit)
        val ivIcon: ImageView = view.findViewById(R.id.iv_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_repo_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = repos[position]

        holder.name.text = item.name
        holder.desc.text = item.description ?: "Sin descripción"
        holder.lang.text = "Lenguaje: ${item.language ?: "N/A"}"

        holder.btnDelete.setOnClickListener { onDeleteClick(item) }
        holder.btnEdit.setOnClickListener { onEditClick(item) } // <--- NUEVO: Activar el lápiz

        val username = item.owner?.login
        if (username != null) {
            Glide.with(holder.itemView.context)
                .load("https://github.com/$username.png")
                .circleCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.ivIcon)
        }
    }

    override fun getItemCount() = repos.size

    fun updateList(newRepos: List<Repo>) {
        repos.clear()
        repos.addAll(newRepos)
        notifyDataSetChanged()
    }
}