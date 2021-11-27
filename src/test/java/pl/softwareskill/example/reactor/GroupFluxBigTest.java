package pl.softwareskill.example.reactor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Map;

import static java.util.function.Function.identity;

@Slf4j
class GroupFluxBigTest {

    @Test
    void crashesWithBigKeySpace() {
        var flux = getData()
                .log()
                .groupBy(this::groupingFunction)
                .flatMap(GroupedFlux::collectList);

        StepVerifier.create(flux)
                .expectNextCount(1000)
                .verifyComplete();
    }

    @Test
    void completesWithBigKeySpace() {
        var flux = getData()
                .log()
                .collectMultimap(this::groupingFunction, identity())
                .flatMapIterable(Map::values);

        StepVerifier.create(flux)
                .expectNextCount(1000)
                .verifyComplete();
    }

    private Flux<Integer> getData() {
        return Flux.range(1, 10000)
                .timeout(Duration.ofSeconds(3));
    }

    private Integer groupingFunction(Integer integer) {
        return integer % 1000;
    }
}
