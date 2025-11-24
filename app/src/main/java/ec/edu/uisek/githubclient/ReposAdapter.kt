package ec.edu.uisek.githubclient

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReposAdapter(
    private var repos: MutableList<RepoItem>,
    private val onEditClick: (RepoItem) -> Unit,
    private val onDeleteClick: (RepoItem) -> Unit
) : RecyclerView.Adapter<ReposAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameInfo: TextView = view.findViewById(R.id.tv_name)
        val descInfo: TextView = view.findViewById(R.id.tv_description)
        val langInfo: TextView = view.findViewById(R.id.tv_language)
        val btnEdit: ImageView = view.findViewById(R.id.btn_edit)
        val btnDelete: ImageView = view.findViewById(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_repo_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = repos[position]
        holder.nameInfo.text = item.name
        holder.descInfo.text = item.description
        holder.langInfo.text = item.language
        holder.btnEdit.setOnClickListener { onEditClick(item) }
        holder.btnDelete.setOnClickListener { onDeleteClick(item) }
    }

    override fun getItemCount() = repos.size

    fun updateList(newRepos: List<RepoItem>) {
        repos.clear()
        repos.addAll(newRepos)
        notifyDataSetChanged()
    }
}