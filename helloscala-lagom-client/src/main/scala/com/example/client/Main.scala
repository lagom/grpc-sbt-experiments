package com.example.client

import java.net.URI

import com.lightbend.lagom.scaladsl.client.{ LagomClientApplication, StaticServiceLocatorComponents }
import io.grpc.examples.helloworld.HelloRequest
import play.api.libs.ws.ahc.AhcWSComponents

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Main extends App {

  val clientApp = new LagomClientApplication("hello-grpc-experiment")
    with StaticServiceLocatorComponents
    with AhcWSComponents {
    override def staticServiceUri: URI = URI.create("http://localhost:50051")
  }

  private val httpbinService = clientApp.serviceClient.implement[HelloLagomService]

  private val helloReply = Await.result(httpbinService.sayHello().invoke(HelloRequest("bob")), Duration(3, "seconds"))

  println(helloReply)
  println("Invoking 'client.stop()'...")
  clientApp.stop()
}
