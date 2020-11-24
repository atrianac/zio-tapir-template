package com.ztapir.ys.http.server

import cats.effect.{ ExitCode => CatsExitCode }
import org.http4s.implicits._
import org.http4s.server.blaze._
import zio._
import zio.clock.Clock
import zio.interop.catz._
import com.ztapir.ys.http.endpoints.twitter.TweetsEndpoint
import com.ztapir.ys.http.endpoints.echo.EchoEndpoint
import com.ztapir.ys.http.endpoints.hackernews.HackerNewsEndpoint
import org.http4s.server.middleware.CORS
import org.http4s.server.Router
import com.ztapir.ys.environment.ApplicationEnvironment
object HttpServer {

  type HttpEnvironment = Clock with ApplicationEnvironment
  type AppTask[A]      = RIO[HttpEnvironment, A]

  val httpApp = Router[AppTask](
    "tweets"      -> TweetsEndpoint().service,
    "health-check" -> EchoEndpoint().service,
    "hacker-news"  -> HackerNewsEndpoint().service
  ).orNotFound

  val server = ZIO.runtime[HttpEnvironment].flatMap { implicit rts =>
    BlazeServerBuilder[AppTask]
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(CORS(httpApp))
      .serve
      .compile[AppTask, AppTask, CatsExitCode]
      .drain
  }
}
