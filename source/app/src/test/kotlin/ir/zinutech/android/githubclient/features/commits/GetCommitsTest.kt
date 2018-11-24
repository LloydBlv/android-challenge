package ir.zinutech.android.githubclient.features.commits

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import ir.zinutech.android.githubclient.UnitTest
import ir.zinutech.android.githubclient.core.functional.Either.Right
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class GetCommitsTest : UnitTest() {

  private lateinit var getCommits: GetCommits

  @Mock
  private lateinit var commitRepository: CommitsRepository

  @Before
  fun setUp() {
    getCommits = GetCommits(commitRepository)
    given { commitRepository.commits("", "", "", 25) }.willReturn(Right(listOf(Commit.empty())))
  }

  @Test
  fun `should get data from repository`() {
    runBlocking { getCommits.run(GetCommits.Params("", "", "", 25)) }

    verify(commitRepository).commits("", "", "", 25)
    verifyNoMoreInteractions(commitRepository)
  }
}
