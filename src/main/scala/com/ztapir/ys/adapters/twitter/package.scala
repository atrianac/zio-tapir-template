package com.ztapir.ys.adapters

import zio._

package object twitter {
  type TweeterAdapter = Has[TweeterAdapter.Service]

  object TweeterAdapter {

    trait Service {
      def getTweet(tweetId: Int): UIO[Tweet]
      def getPopularTweets(): UIO[Seq[Tweet]]
    }

    def getTweet(tweetId: Int): RIO[TweeterAdapter, Tweet]  = ZIO.accessM(_.get.getTweet(tweetId))
    def getPopularTweets(): RIO[TweeterAdapter, Seq[Tweet]] = ZIO.accessM(_.get.getPopularTweets())

    val inMemory: Layer[Nothing, TweeterAdapter] = ZLayer.succeed(
      new Service {
        def getTweet(tweetId: Int): UIO[Tweet] = ZIO.succeed(Tweet(1, "zio-tweet"))
        def getPopularTweets(): UIO[Seq[Tweet]] =
          ZIO.succeed(Seq(Tweet(1, "zio-tweet-1"), Tweet(2, "zio-tweet-2")))
      }
    )
  }

  case class Tweet(id: Int, message: String)
}
