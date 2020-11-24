package com.ztapir.ys

import zio._
import zio.console._
import com.ztapir.ys.http.server.HttpServer._
import com.ztapir.ys.adapters.twitter.TweeterAdapter
import com.ztapir.ys.adapters.hackernews.HackerNewsAdapter
import com.ztapir.ys.helpers.http.client.HttpClient

object Main extends App {

  val adapterLayer = TweeterAdapter.inMemory ++ HackerNewsAdapter.live

  val helpersLayer = HttpClient.live

  def run(args: List[String]) =
    putStrLn("Running the server") *> server
      .provideCustomLayer(adapterLayer ++ helpersLayer)
      .exitCode

}
