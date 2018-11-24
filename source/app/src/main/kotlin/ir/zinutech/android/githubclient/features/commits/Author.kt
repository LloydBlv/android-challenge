package ir.zinutech.android.githubclient.features.commits

import java.util.Date

data class Author(
    val name: String,
    val email: String,
    val date: Date
)