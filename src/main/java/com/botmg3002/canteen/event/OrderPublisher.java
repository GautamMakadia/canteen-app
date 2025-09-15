package com.botmg3002.canteen.event;

import org.springframework.stereotype.Component;

import com.botmg3002.canteen.schema.order.OrderResponse;

import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;

@Component
public class OrderPublisher {
    private final Sinks.Many<OrderResponse> sink = Sinks.many().multicast().onBackpressureBuffer();

    public EmitResult publish(OrderResponse order) {
        return sink.tryEmitNext(order);
    }

    public Sinks.Many<OrderResponse> getSink() {
        return sink;
    }
}
