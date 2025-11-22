package ec.edu.uisek.githubclient

import retrofit2.Call
import retrofit2.http.*

interface GitHubApiService {
    @GET("user/repos")
    fun listRepos(): Call<List<RepoItem>>

    @POST("user/repos")
    fun createRepo(@Body repo: RepoItem): Call<RepoItem>

    @DELETE("repos/{owner}/{repo}")
    fun deleteRepo(@Path("owner") owner: String, @Path("repo") repo: String): Call<Void>

    @PATCH("repos/{owner}/{repo}")
    fun updateRepo(
        @Path("owner") owner: String,
        @Path("repo") repoName: String,
        @Body repoData: RepoItem
    ): Call<RepoItem>
}