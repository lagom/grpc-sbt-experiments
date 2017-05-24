package io.grpc.example.helloworld

import com.lightbend.lagom.scaladsl.api.{ Service, ServiceCall }
import com.lightbend.lagom.scaladsl.api.Service._

import io.grpc.examples.helloworld.{ HelloReply, HelloRequest }

/**
  *
  */
class HelloLagomService extends Service {

  override def descriptor(): Unit ={
    named("hello").withCalls(
      namedCall("sayHello", theHello _)
    )
  }

  def theHello(): ServiceCall[HelloRequest, HelloReply]

}
