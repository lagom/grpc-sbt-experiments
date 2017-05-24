package io.grpc.example.helloworld

/**
  *
  */
class HelloLagomService extends Service {

  override def descriptor(): Unit ={
    named("hello").withCalls(
      namedCall
    )
  }

}
