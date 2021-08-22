package bisect.java;

import java.util.Optional;

class BisectOutcome {

    public static BisectOutcome error(String error) {
        return new BisectOutcome(Optional.of(error), Optional.empty());
    }

    public static BisectOutcome bisectOutcome(BisectResult result) {
        return new BisectOutcome(Optional.empty(), Optional.of(result));
    }

    public final Optional<String> error;
    public final Optional<BisectResult> result;

    BisectOutcome(Optional<String> error, Optional<BisectResult> outcome) {
        this.error = error;
        this.result = outcome;
    }
}
