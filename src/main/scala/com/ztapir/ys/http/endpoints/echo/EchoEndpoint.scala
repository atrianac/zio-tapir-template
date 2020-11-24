package com.ztapir.ys.http.endpoints.echo

import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.implicits._
import zio._
import zio.interop.catz._
import com.ztapir.ys.environment.ApplicationEnvironment

case class EchoEndpoint[R <: ApplicationEnvironment]() {

  type EndpointTask[A] = RIO[R, A]

  private val dsl = Http4sDsl[EndpointTask]
  import dsl._

  val service = HttpRoutes.of[EndpointTask] {
    case GET -> Root / name => {
      ZIO.accessM[R](_ => Ok("Service Working"))
    }
  }
}
