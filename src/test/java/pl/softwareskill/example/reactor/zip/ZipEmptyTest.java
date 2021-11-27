package pl.softwareskill.example.reactor.zip;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static reactor.function.TupleUtils.function;

class ZipEmptyTest {

    @Test
    void unfinishedZip() {
        var flux = Mono.zip(getSummary(), getRecentTransactions())
                .map(function(this::toSummary));

        StepVerifier.create(flux)
                .assertNext(summary -> assertThat(summary).isNotNull())
                .verifyComplete();
    }

    private Summary toSummary(BigDecimal summary, List<UUID> transactions) {
        return new Summary(summary, transactions);
    }

    private Mono<List<UUID>> getRecentTransactions() {
        return Mono.justOrEmpty(Optional.empty());
    }

    private Mono<BigDecimal> getSummary() {
        return Mono.just(BigDecimal.TEN);
    }
}
