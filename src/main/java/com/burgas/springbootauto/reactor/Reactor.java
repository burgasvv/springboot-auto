package com.burgas.springbootauto.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.IntStream;

public class Reactor {

    public static void main(String[] args) {

        Mono<String> mono = Mono.just("Mono just method!");
        mono.subscribe(System.out::println);

        Flux<Integer> range = Flux.range(1, 10);
        range.subscribe(System.out::println);

        Mono.create(sink -> sink.success("Mono create method!")).subscribe(System.out::println);

        new Thread(() -> {
            Flux
                    .generate(
                            synchronousSink -> synchronousSink.next("Flux generate method!")
                    )
                    .take(5).subscribe(s -> {
                        System.out.println(s);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });

            Flux
                    .generate(
                        () -> 500,
                        ((integer, synchronousSink) -> {
                            if (integer > 520) {
                                synchronousSink.complete();
                            } else {
                                synchronousSink.next("State: " + integer);
                            }
                            return integer + 3;
                        })
                    ).subscribe(System.out::println);
        }).start();

        IntStream.rangeClosed(0, 10).forEach(value -> {
            System.out.println(value);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
