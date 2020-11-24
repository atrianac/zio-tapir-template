package com.ztapir.ys.adapters

import org.http4s._
import org.http4s.implicits._
import org.http4s.circe._
import zio.interop.catz._
import zio._
import zio.interop.catz
import com.ztapir.ys.helpers.http.client.HttpClient
import com.ztapir.ys.adapters.hackernews.implicits._

package object hackernews {

  type HackerNewsAdapter = Has[HackerNewsAdapter.Service]

  object HackerNewsAdapter {

    val baseUrl = uri"https://hacker-news.firebaseio.com/v0/item"

    val url = (id: String) => baseUrl / id + ".json?print=pretty"

    trait Service {
      def getJobById(id: String): RIO[HttpClient, Job]
    }

    def getJobById(id: String): RIO[HackerNewsAdapter with HttpClient, Job] = ZIO.accessM(_.get.getJobById(id))

    def live: Layer[Nothing, HackerNewsAdapter] =
      ZLayer.succeed(new Service {
        def getJobById(id: String): zio.RIO[HttpClient, Job] =
          for {
            httpClient <- HttpClient.client
            job        <- httpClient.get(url(id))(_.as[Job])
          } yield job
      })
  }

  implicit def jobDecoder: EntityDecoder[Task, Job] = jsonOf[Task, Job]
}
