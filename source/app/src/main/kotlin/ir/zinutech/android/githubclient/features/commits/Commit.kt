package ir.zinutech.android.githubclient.features.commits

import java.util.Date


data class Commit(
    val message: String,
    val author: Author,
    val sha: String
) {
  companion object {
    fun empty() = Commit("", Author("", "", Date()), "")
  }
}