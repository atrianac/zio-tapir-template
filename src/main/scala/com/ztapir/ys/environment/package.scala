package com.ztapir.ys

import com.ztapir.ys.adapters.twitter.TweeterAdapter
import com.ztapir.ys.adapters.hackernews.HackerNewsAdapter
import com.ztapir.ys.helpers.http.client.HttpClient

package object environment {
  type ApplicationEnvironment = AdaptersEnvironment with HelpersEnvironment

  type AdaptersEnvironment = TweeterAdapter with HackerNewsAdapter

  type HelpersEnvironment = HttpClient
}
