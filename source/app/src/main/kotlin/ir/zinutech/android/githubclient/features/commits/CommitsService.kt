package ir.zinutech.android.githubclient.features.commits

import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CommitsService @Inject constructor(val commitsApi: CommitsApi) : CommitsApi {
//    private val commitsApi: CommitsApi by lazy { retrofit.create(CommitsApi::class.java) }


  override fun getCommits(
      owner: String,
      repoName: String,
      branchName: String,
      limit: Int,
      lastSha: String?
  ): Call<List<CommitEntity>> = commitsApi.getCommits(owner, repoName, branchName, limit, lastSha)
}