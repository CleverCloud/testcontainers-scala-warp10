package com.clevercloud.testcontainers.scala

import akka.http.scaladsl.model.{ HttpEntity, HttpMethods }
import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.actor.typed.ActorSystem
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.unmarshalling.Unmarshal
import io.moia.scalaHttpClient._
import org.scalatest.concurrent.Eventually
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import com.dimafeng.testcontainers.ForAllTestContainer
import java.time.Clock
import scala.concurrent.{ Await, ExecutionContextExecutor, Future }
import scala.concurrent.duration.{ Deadline, DurationInt }

class Warp10ContainerSpecs extends AnyWordSpec with Matchers with Eventually with ForAllTestContainer {

  private val Warp10AuthHeader = "X-Warp10-Token"
  private val Warp10FetchAPI = "/api/v0/exec"
  private val Warp10FetchGTS = "[ '%s' 'test' {} NOW -1 ] FETCH"
  private val Warp10FetchedHeader = "X-Warp10-Fetched"
  private val Warp10GTS = "1// test{} 42"
  private val Warp10UpdateAPI = "/api/v0/update"
  private val Warp10Version = "3.4.1-ubuntu-ci"

  val container: Warp10Container = Warp10Container(Warp10Version)

  private val testKit = ActorTestKit()

  implicit val system: ActorSystem[Nothing]     = testKit.system
  implicit val ec    : ExecutionContextExecutor = system.executionContext

  "warp10 testcontainer" should {

    "give write token" in {
      container.container.isRunning shouldBe (true)

      assert(container.readToken != null)
      assert(container.writeToken != null)
    }

    "write to and read from warp10" in {
      Await.result(warp10Request(container, Warp10UpdateAPI, Warp10GTS, Some(container.writeToken)), 10.seconds) match {
        case HttpClientSuccess(_) =>
          Await.result(warp10Request(container, Warp10FetchAPI, String.format(Warp10FetchGTS, container.readToken), None), 5.seconds) match {
            case HttpClientSuccess(content) =>
              content.status.isSuccess() shouldBe (true)
              content.headers.find(_.lowercaseName() == Warp10FetchedHeader.toLowerCase()).map(_.value().toInt).getOrElse(0) shouldBe (1)
            case _  => assert(false)
          }
        case DomainError(content) =>
          val result = Await.result(Unmarshal(content).to[String], 10.seconds)
          assert(false, result)
        case _ => assert(false)
      }
    }
  }

  def warp10Request(
    container: Warp10Container,
    path: String,
    body: String,
    auth: Option[String],
  ): Future[HttpClientResponse] = {
    val httpClient = new HttpClient(
      config = HttpClientConfig("http", container.httpHost, container.httpPort),
      name = "TestClient",
      httpMetrics = HttpMetrics.none,
      retryConfig = RetryConfig.default,
      clock = Clock.systemUTC(),
      awsRequestSigner = None
    )

    httpClient.request(
      method = HttpMethods.POST,
      entity = HttpEntity(body),
      path = path,
      headers = Seq(auth.map(RawHeader(Warp10AuthHeader, _))).flatten,
      deadline = Deadline.now + 10.seconds,
    )
  }
}
