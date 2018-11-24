package ir.zinutech.android.githubclient.core.platform

import ir.zinutech.android.githubclient.core.exception.Failure

enum class Status {
  RUNNING,
  SUCCESS,
  FAILED
}

@Suppress("DataClassPrivateConstructor")
data class NetworkState private constructor(
    val status: Status,
    val failure: Failure? = null) {
  companion object {
    val LOADED = NetworkState(
        Status.SUCCESS
    )
    val LOADING = NetworkState(
        Status.RUNNING
    )

    fun error(failure: Failure) = NetworkState(
        Status.FAILED, failure)
  }
}