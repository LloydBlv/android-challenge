package ir.zinutech.android.githubclient.features.commits

import java.util.Date

data class PersonEntity(
    val name: String,
    val email: String,
    val date: Date
)
