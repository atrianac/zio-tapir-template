package com.ztapir.ys.http.endpoints.hackernews

import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.circe._
import org.http4s.implicits._
import zio._
import zio.interop.catz._
import com.ztapir.ys.environment.ApplicationEnvironment
import com.ztapir.ys.adapters.hackernews.HackerNewsAdapter
import com.ztapir.ys.adapters.hackernews.implicits._
import com.ztapir.ys.adapters.hackernews.Job
import org.http4s.EntityEncoder

case class HackerNewsEndpoint[R <: ApplicationEnvironment]() {

  type EndpointTask[A] = RIO[R, A]

  private val dsl = Http4sDsl[EndpointTask]
  import dsl._

  implicit def tweetEncoder: EntityEncoder[EndpointTask, Job] = jsonEncoderOf[EndpointTask, Job]

  val service = HttpRoutes.of[EndpointTask] {
    case GET -> Root / jobId =>
      ZIO.environment[R].flatMap { _.get[HackerNewsAdapter.Service].getJobById(jobId).flatMap(Ok(_)) }

  }
}
