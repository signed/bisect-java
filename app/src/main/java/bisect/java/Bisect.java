package bisect.java;

import java.util.List;
import java.util.Optional;

import static bisect.java.BisectOutcome.bisectOutcome;
import static bisect.java.BisectResult.bisectResult;

public class Bisect {

    public static BisectOutcome bisect(Version knownGood, Version knownBad, Scene scene) {
        List<Suspect> suspects = scene.suspects();

        Optional<Suspect> lastKnowGood = Optional.empty();
        Optional<Suspect> firstKnowBad = Optional.empty();

        for (Suspect suspect : suspects) {
            switch (scene.check(suspect)) {
                case Good -> lastKnowGood = Optional.of(suspect);
                case Bad -> firstKnowBad = firstKnowBad.or(() -> Optional.of(suspect));
            }
        }
        return bisectOutcome(bisectResult(lastKnowGood.orElseThrow(), firstKnowBad.orElseThrow()));
    }

    public static void main(String[] args) {
    }
}
