package pl.softwareskill.example.reactor.zip;

import lombok.Value;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Value
class Summary {
    BigDecimal summary;
    List<UUID> recentTransactions;
}
