package ir.zinutech.android.githubclient.features.commits

import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.given
import ir.zinutech.android.githubclient.AndroidTest
import ir.zinutech.android.githubclient.core.functional.Either.Right
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import java.util.Date

class CommitsViewModelTest : AndroidTest() {

  private lateinit var commitsViewModel: CommitsViewModel

  @Mock
  private lateinit var getCommits: GetCommits

  @Before
  fun setUp() {
    commitsViewModel = CommitsViewModel(getCommits)
  }

  @Test
  fun `loading commits should update live data`() {
    val commitsList = listOf(Commit("Commit1_Message", Author("Name", "Email", Date()), "Sha"),
        Commit("Commit2_Message", Author("Name", "Email", Date()), "Sha1"))
    given { runBlocking { getCommits.run(eq(GetCommits.Params("", "", "", 25))) } }.willReturn(
        Right(commitsList))

    commitsViewModel.commits.observeForever {
      it!!.size shouldEqualTo 2
      it[0].sha shouldEqualTo "Sha"
      it[0].message shouldEqualTo "Commit1_Message"
      it[1].sha shouldEqualTo "Sha1"
      it[1].message shouldEqualTo "Commit2_Message"
    }

    runBlocking { commitsViewModel.loadCommits() }
  }
}