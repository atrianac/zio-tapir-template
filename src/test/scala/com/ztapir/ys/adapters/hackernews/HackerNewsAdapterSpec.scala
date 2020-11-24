package com.ztapir.ys.adapters.hackernews

import zio.test._
import zio.test.Assertion._
import zio.test.DefaultRunnableSpec
import zio.test.TestAspect._
import com.ztapir.ys.helpers.http.client.HttpClient
import org.http4s._
import org.http4s.implicits._

object HackerNewsAdapterSpec extends DefaultRunnableSpec {

  val layer = HackerNewsAdapter.live ++ HttpClient.live

  def spec =
    suite("HackerNewsAdapterSpec")(
      testM("Get Job by Id") {
        for {
          rjob <- HackerNewsAdapter.getJobById("192327")
        } yield assert(rjob.id)(equalTo(192327))
      }
    ).provideCustomLayerShared(layer).mapError(TestFailure.fail) @@ sequential
}
