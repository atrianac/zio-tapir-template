package com.ztapir.ys.helpers.http

import zio._
import org.http4s.client.Client
import org.http4s.client.blaze.BlazeClientBuilder
import zio.interop.catz._
import zio.interop.catz.implicits._
import cats.effect.ContextShift
import zio.interop.catz

package object client {

  implicit val cs: ContextShift[Task] = zioContextShift

  def clientManaged: ZManaged[Any, Throwable, Client[Task]] = {
    val zioManaged: ZIO[Any, Throwable, ZManaged[Any, Throwable, Client[Task]]] =
      ZIO.runtime[Any].map { rts =>
        val exec = rts.platform.executor.asEC

        implicit def rr = rts
        catz.catsIOResourceSyntax(BlazeClientBuilder[Task](exec).resource).toManaged
      }
    zioManaged.toManaged_.flatten
  }

  type HttpClient = Has[HttpClient.Service]

  object HttpClient {

    trait Service {
      def client: Client[Task]
    }

    def client: RIO[HttpClient, Client[Task]] = ZIO.access(_.get.client)

    val live: Layer[Throwable, HttpClient] = ZLayer.fromManaged(
      clientManaged.map(
        httpc =>
          new Service {
            def client: Client[zio.Task] = httpc
          }
      )
    )
  }
}
