package com.example.client

import java.net.URI

import com.lightbend.lagom.scaladsl.client.{ LagomClientApplication, StaticServiceLocatorComponents }
import io.grpc.examples.helloworld.HelloRequest
import play.api.libs.ws.ahc.AhcWSComponents

import scala.concurrent.{ Await, Future }
import scala.concurrent.duration.Duration

object Main extends App {

  val clientApp = new LagomClientApplication("hello-grpc-experiment")
    with StaticServiceLocatorComponents
    with AhcWSComponents {
    override def staticServiceUri: URI = URI.create("http://localhost:50051")
  }
  private implicit val exCtx = clientApp.actorSystem.dispatcher

  private val httpbinService = clientApp.serviceClient.implement[HelloLagomService]
  private val sayHello = httpbinService.sayHello()
  private val manyCalls = Future.sequence((1 to 10).map(_ => sayHello.invoke(HelloRequest("bob"))))

  Await.result(manyCalls, Duration(3, "seconds"))

  println("Invoking 'client.stop()'...")
  clientApp.stop()
}
