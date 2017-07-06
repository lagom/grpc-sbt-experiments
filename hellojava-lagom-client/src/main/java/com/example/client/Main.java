package com.example.client;

import com.lightbend.lagom.javadsl.client.integration.LagomClientFactory;
import io.grpc.examples.HelloReply;
import io.grpc.examples.HelloRequest;

import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        LagomClientFactory clientFactory = LagomClientFactory.create(
                "hello-grpc-experiment-java",
                LagomClientFactory.class.getClassLoader()
        );

        HelloLagomService client = clientFactory.createClient(HelloLagomService.class, URI.create("http://localhost:50051"));
        HelloReply helloReply =
                client
                        .sayHello()
                        .invoke(HelloRequest.newBuilder().setName("Steve").build())
                        .toCompletableFuture()
                        .get(5, TimeUnit.SECONDS);
        System.out.println(helloReply.toString());
        clientFactory.close();

    }
}