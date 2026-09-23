package garage.domain;

import java.time.Duration;

public record Operation(Atelier atelier, Duration temps) {}
