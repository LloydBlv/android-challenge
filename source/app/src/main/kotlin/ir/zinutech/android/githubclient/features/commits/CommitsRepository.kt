package ir.zinutech.android.githubclient.features.commits

import ir.zinutech.android.githubclient.core.exception.Failure
import ir.zinutech.android.githubclient.core.functional.Either
import ir.zinutech.android.githubclient.core.platform.NetworkHandler
import retrofit2.Call
import javax.inject.Inject

interface CommitsRepository {
  fun commits(
      owner: String,
      repoName: String,
      branchName: String,
      limit: Int,
      lastSha: String? = null
  ): Either<Failure, List<Commit>>

  class Network
  @Inject constructor(
      private val networkHandler: NetworkHandler,
      private val service: CommitsService
  ) : CommitsRepository {
    override fun commits(
        owner: String,
        repoName: String,
        branchName: String,
        limit: Int,
        lastSha: String?
    ): Either<Failure, List<Commit>> {
      return when (networkHandler.isConnected) {
        true -> request(
            call = service.getCommits(
                owner = owner,
                repoName = repoName,
                branchName = branchName,
                limit = limit,
                lastSha = lastSha
            ),
            transform = {
              it.map {
                Commit(
                    message = it.commitDetail.message,
                    author = Author(
                        name = it.commitDetail.author.name,
                        email = it.commitDetail.author.email,
                        date = it.commitDetail.author.date
                    ),
                    sha = it.sha
                )
              }
            },
            default = arrayListOf()
        )

        false, null -> Either.Left(Failure.NetworkConnection())
      }
    }


    private fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
      return try {
        val response = call.execute()
        when (response.isSuccessful) {
          true -> Either.Right(transform((response.body() ?: default)))
          false -> Either.Left(Failure.ServerError())
        }
      } catch (exception: Throwable) {
        Either.Left(Failure.ServerError())
      }
    }
  }
}