package ir.zinutech.android.githubclient.features.commits

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CommitsApi {

  @GET("repos/{owner}/{repoName}/commits")
  fun getCommits(@Path("owner") owner: String,
      @Path("repoName") repoName: String,
      @Query("sha") branchName: String,
      @Query("per_page") limit: Int,
      @Query("last_sha") lastSha: String? = null): Call<List<CommitEntity>>
}