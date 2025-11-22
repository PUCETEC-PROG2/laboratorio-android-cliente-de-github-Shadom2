package ec.edu.uisek.githubclient

import com.google.gson.annotations.SerializedName

data class RepoItem(
    val name: String,
    val description: String?,
    val language: String?,

    val owner: Owner? = null
)

data class Owner(
    val login: String
)