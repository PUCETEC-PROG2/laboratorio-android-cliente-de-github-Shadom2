package ec.edu.uisek.githubclient.models

import com.google.gson.annotations.SerializedName

data class Repo(
    val name: String,
    val description: String?,
    val language: String?,
    @SerializedName("owner") val owner: RepoOwner?
)