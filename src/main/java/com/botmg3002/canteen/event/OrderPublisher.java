package com.botmg3002.canteen.event;

import lombok.Getter;
import org.springframework.stereotype.Component;

import com.botmg3002.canteen.schema.order.OrderResponse;

import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitResult;

@Getter
@Component
public class OrderPublisher {
    private final Sinks.Many<OrderResponse> sink = Sinks.many().multicast().onBackpressureBuffer();

    public EmitResult publish(OrderResponse order) {
        return sink.tryEmitNext(order);
    }

}
