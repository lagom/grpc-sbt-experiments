package com.example.client

import com.lightbend.lagom.scaladsl.api.{ Service, ServiceCall, ServiceCallTransport }
import io.grpc.examples.helloworld.{ HelloReply, HelloRequest }


trait HelloLagomService extends Service {

  import Service._

  override def descriptor() = {
    named("helloworld.Greeter").withCalls(
      namedCall("SayHello", sayHello _)
    ).withServiceCallTransports(ServiceCallTransport.Grpc)
  }

  def sayHello(): ServiceCall[HelloRequest, HelloReply]

}
