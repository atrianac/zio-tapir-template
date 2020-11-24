package com.ztapir.ys.adapters.hackernews

import io.circe._

case class Job(by: String, id: Int, score: Short, text: String, time: Long, ftype: String, url: String)

object implicits {
  implicit val encodeJob: Encoder[Job] = new Encoder[Job] {
    def apply(job: Job): Json = Json.obj(
      ("by", Json.fromString(job.by)),
      ("id", Json.fromBigInt(job.id)),
      ("score", Json.fromInt(job.score)),
      ("text", Json.fromString(job.text)),
      ("time", Json.fromLong(job.time)),
      ("type", Json.fromString(job.ftype)),
      ("url", Json.fromString(job.url))
    )
  }

  implicit val decodeJob: Decoder[Job] = new Decoder[Job] {
    def apply(c: HCursor): Decoder.Result[Job] =
      for {
        by    <- c.downField("by").as[String]
        id    <- c.downField("id").as[Integer]
        score <- c.downField("score").as[Short]
        text  <- c.downField("text").as[String]
        time  <- c.downField("time").as[Long]
        ftype <- c.downField("type").as[String]
        url   <- c.downField("url").as[String]
      } yield {
        Job(by, id, score, text, time, ftype, url)
      }
  }
}
