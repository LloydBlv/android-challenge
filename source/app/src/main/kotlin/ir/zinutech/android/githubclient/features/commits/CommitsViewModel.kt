package ir.zinutech.android.githubclient.features.commits

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.zinutech.android.githubclient.core.exception.Failure
import ir.zinutech.android.githubclient.core.platform.NetworkState
import ir.zinutech.android.githubclient.core.utils.Constants
import javax.inject.Inject

class CommitsViewModel
@Inject constructor(private val getCommits: GetCommits) : ViewModel() {

  val commits = MutableLiveData<List<Commit>>()

  val networkState = MutableLiveData<NetworkState>()
  val refreshState = MutableLiveData<NetworkState>()

  fun refresh() {
    refreshState.value = NetworkState.LOADING
    loadCommits()
  }

  fun loadCommits(lastSha: String? = null) {
    networkState.value = NetworkState.LOADING
    getCommits(GetCommits.Params(
        owner = Constants.AOSP,
        repoName = Constants.PLATFORM_BUILD,
        branchName = Constants.MASTER_BRANCH,
        limit = Constants.PAGING_LIMIT,
        lastSha = lastSha
    )) {

      it.either(::handleFailure, ::handleCommitsListing)
    }

  }

  fun retry() {
    if (commits.value?.isNotEmpty() == true) {
      loadCommits()
    } else {
      refresh()
    }
  }


  private fun handleFailure(failure: Failure) {
    networkState.value = NetworkState.error(failure)
    refreshState.value = NetworkState.error(failure)
  }

  private fun handleCommitsListing(commitsData: List<Commit>) {
    networkState.value = NetworkState.LOADED
    refreshState.value = NetworkState.LOADED
    commits.value = commitsData
  }


}