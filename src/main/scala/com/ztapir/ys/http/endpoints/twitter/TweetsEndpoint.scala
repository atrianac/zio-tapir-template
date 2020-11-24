package com.ztapir.ys.http.endpoints.twitter

import org.http4s.{ EntityEncoder, HttpRoutes }
import org.http4s.dsl.Http4sDsl
import org.http4s.circe._
import org.http4s.implicits._
import zio.interop.catz._
import zio._
import com.ztapir.ys.adapters.twitter._

import com.ztapir.ys.environment.ApplicationEnvironment
import io.circe.generic.auto._

case class TweetsEndpoint[R <: ApplicationEnvironment]() {

  type EndpointTask[A] = RIO[R, A]

  private val dsl = Http4sDsl[EndpointTask]
  import dsl._

  implicit def tweetEncoder: EntityEncoder[EndpointTask, Tweet]         = jsonEncoderOf[EndpointTask, Tweet]
  implicit def tweetSeqEncoder: EntityEncoder[EndpointTask, Seq[Tweet]] = jsonEncoderOf[EndpointTask, Seq[Tweet]]

  val service = HttpRoutes.of[EndpointTask] {
    case GET -> Root / "popular" =>
      ZIO.environment[R].flatMap { _.get[TweeterAdapter.Service].getPopularTweets().flatMap(Ok(_)) }
    case GET -> Root / IntVar(tweetId) =>
      ZIO.environment[R].flatMap { _.get[TweeterAdapter.Service].getTweet(tweetId).flatMap(Ok(_)) }
  }

}
