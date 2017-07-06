package io.grpc.example.helloworld

import io.grpc.Server
import io.grpc.ServerBuilder
import java.util.logging.Logger

import io.grpc.examples.helloworld.{ GreeterGrpc, HelloReply, HelloRequest }

import scala.concurrent.{ ExecutionContext, Future }


class HelloWorldServer() {

  val logger = Logger.getLogger(HelloWorldServer.getClass.getName)

  /* The port on which the server should run */
  val port = 50051
  val server: Server =
    ServerBuilder.forPort(port)
      .addService(
        GreeterGrpc.bindService(new GreeterImpl(), ExecutionContext.global)
      )
      .build()
      .start()

  Runtime.getRuntime().addShutdownHook(new Thread() {
    override def run() {
      // Use stderr here since the logger may have been reset by its JVM shutdown hook.
      System.err.println("*** shutting down gRPC server since JVM is shutting down")
      HelloWorldServer.this.stop()
      System.err.println("*** server shut down")
    }
  })

  def stop() {
    if (server != null) {
      server.shutdown()
    }
  }

  /**
    * Await termination on the main thread since the grpc library uses daemon threads.
    */
  def blockUntilShutdown() {
    if (server != null) {
      server.awaitTermination()
    }
  }

}

object HelloWorldServer extends App {

  val server = new HelloWorldServer()
  server.blockUntilShutdown()

}

class GreeterImpl extends GreeterGrpc.Greeter {
  override def sayHello(req: HelloRequest): Future[HelloReply] = {
    val reply = HelloReply(message = "Hello " + req.name)
    print(reply.toString)
    Future.successful(reply)
  }
}
