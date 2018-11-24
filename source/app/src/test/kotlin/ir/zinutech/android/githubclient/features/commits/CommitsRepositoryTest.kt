package ir.zinutech.android.githubclient.features.commits

import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import ir.zinutech.android.githubclient.UnitTest
import ir.zinutech.android.githubclient.core.exception.Failure.NetworkConnection
import ir.zinutech.android.githubclient.core.exception.Failure.ServerError
import ir.zinutech.android.githubclient.core.functional.Either
import ir.zinutech.android.githubclient.core.functional.Either.Right
import ir.zinutech.android.githubclient.core.platform.NetworkHandler
import ir.zinutech.android.githubclient.features.commits.CommitsRepository.Network
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import retrofit2.Call
import retrofit2.Response
import java.util.Date

class CommitsRepositoryTest : UnitTest() {

  private lateinit var networkRepository: CommitsRepository.Network
  @Mock
  private lateinit var networkHandler: NetworkHandler
  @Mock
  private lateinit var service: CommitsService

  @Mock
  private lateinit var commitsCall: Call<List<CommitEntity>>
  @Mock
  private lateinit var commitsResponse: Response<List<CommitEntity>>


  @Before
  fun setUp() {
    networkRepository = Network(networkHandler, service)
  }


  @Test
  fun `should return empty list by default`() {
    given { networkHandler.isConnected }.willReturn(true)
    given { commitsResponse.body() }.willReturn(null)
    given { commitsResponse.isSuccessful }.willReturn(true)
    given { commitsCall.execute() }.willReturn(commitsResponse)
    given { service.getCommits("", "", "", 25) }.willReturn(commitsCall)

    val commits = networkRepository.commits("", "", "", 25)

    commits shouldEqual Right(emptyList<Commit>())
    verify(service).getCommits("", "", "", 25)
  }

  @Test
  fun `should get commits list from service`() {
    given { networkHandler.isConnected }.willReturn(true)
    val date = Date()
    given { commitsResponse.body() }.willReturn(listOf(
        CommitEntity("Sha", CommitEntity.Detail("Message", PersonEntity("Name", "Email", date)))))
    given { commitsResponse.isSuccessful }.willReturn(true)
    given { commitsCall.execute() }.willReturn(commitsResponse)
    given { service.getCommits("", "", "", 25) }.willReturn(commitsCall)

    val commits = networkRepository.commits("", "", "", 25)

    commits shouldEqual Right(listOf(Commit("Message", Author("Name", "Email", date), "Sha")))
    verify(service).getCommits("", "", "", 25)
  }

  @Test
  fun `commits service should return network failure when no connection`() {
    given { networkHandler.isConnected }.willReturn(false)

    val commits = networkRepository.commits("", "", "", 25)

    commits shouldBeInstanceOf Either::class.java
    commits.isLeft shouldEqual true
    commits.either({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
    verifyZeroInteractions(service)
  }

  @Test
  fun `commits service should return network failure when undefined connection`() {
    given { networkHandler.isConnected }.willReturn(null)

    val commits = networkRepository.commits("", "", "", 25)

    commits shouldBeInstanceOf Either::class.java
    commits.isLeft shouldEqual true
    commits.either({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
    verifyZeroInteractions(service)
  }

  @Test
  fun `commits service should return server error if no successful response`() {
    given { networkHandler.isConnected }.willReturn(true)

    val commits = networkRepository.commits("", "", "", 25)

    commits shouldBeInstanceOf Either::class.java
    commits.isLeft shouldEqual true
    commits.either({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
  }

  @Test
  fun `commits request should catch exceptions`() {
    given { networkHandler.isConnected }.willReturn(true)

    val commits = networkRepository.commits("", "", "", 25)

    commits shouldBeInstanceOf Either::class.java
    commits.isLeft shouldEqual true
    commits.either({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
  }
}