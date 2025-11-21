package ec.edu.uisek.githubclient

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReposAdapter(private val repos: List<RepoItem>) :
    RecyclerView.Adapter<ReposAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameInfo: TextView = view.findViewById(R.id.tv_name)
        val descInfo: TextView = view.findViewById(R.id.tv_description)
        val langInfo: TextView = view.findViewById(R.id.tv_language)
        val iconImage: ImageView = view.findViewById(R.id.iv_icon)
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
        holder.iconImage.setImageResource(item.iconResId)
    }

    override fun getItemCount() = repos.size
}