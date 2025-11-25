package ec.edu.uisek.githubclient.services

import ec.edu.uisek.githubclient.models.Repo
import retrofit2.Call
import retrofit2.http.*

interface GitHubApiService {

    @GET("user/repos?visibility=all&per_page=100&sort=updated")
    fun listRepos(): Call<List<Repo>>

    @POST("user/repos")
    fun createRepo(@Body repo: Repo): Call<Repo>

    @DELETE("repos/{owner}/{repo}")
    fun deleteRepo(@Path("owner") owner: String, @Path("repo") repo: String): Call<Void>

    @PATCH("repos/{owner}/{repo}")
    fun updateRepo(
        @Path("owner") owner: String,
        @Path("repo") repoName: String,
        @Body repoData: Repo
    ): Call<Repo>
}