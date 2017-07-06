package com.example.client;

import static com.lightbend.lagom.javadsl.api.Service.*;

import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import io.grpc.examples.HelloReply;
import io.grpc.examples.HelloRequest;


public interface HelloLagomService extends Service {

    ServiceCall<HelloRequest, HelloReply> sayHello();

    default Descriptor descriptor() {
        return named("helloworld.Greeter.java").withCalls(
                namedCall("SayHello", this::sayHello)
        )
                // TODO: support ServiceCallTransport.Grpc in javadsl
                //.withServiceCallTransports(ServiceCallTransport.Grpc)
                ;
    }
}