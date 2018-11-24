package ir.zinutech.android.githubclient.features.commits

import androidx.annotation.VisibleForTesting
import ir.zinutech.android.githubclient.core.exception.Failure
import ir.zinutech.android.githubclient.core.functional.Either
import ir.zinutech.android.githubclient.core.interactor.UseCase
import javax.inject.Inject

@VisibleForTesting
class GetCommits @Inject constructor(private val commitsRepository: CommitsRepository) :
    UseCase<List<Commit>, GetCommits.Params>() {
  override suspend fun run(
      params: Params): Either<Failure, List<Commit>> = commitsRepository.commits(
      owner = params.owner,
      repoName = params.repoName,
      branchName = params.branchName,
      limit = params.limit,
      lastSha = params.lastSha
  )

  data class Params(
      val owner: String,
      val repoName: String,
      val branchName: String,
      val limit: Int,
      val lastSha: String? = null
  )

}