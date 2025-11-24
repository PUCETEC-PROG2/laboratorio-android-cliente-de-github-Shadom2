package ec.edu.uisek.githubclient

data class RepoItem(
    var name: String,
    var description: String,
    var language: String,
    val iconResId: Int = R.drawable.ic_launcher_foreground
)