package ir.zinutech.android.githubclient.features.commits

import com.squareup.moshi.Json

data class CommitEntity(
    @field:Json(name = "sha")
    val sha: String,

    @field:Json(name = "commit")
    val commitDetail: Detail
) {

  data class Detail(
      @field:Json(name = "message")
      val message: String,

      @field:Json(name = "author")
      val author: PersonEntity)

}