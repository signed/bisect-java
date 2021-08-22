package bisect.java;

import java.util.List;
import java.util.Optional;

import static bisect.java.BisectOutcome.bisectOutcome;
import static bisect.java.BisectResult.bisectResult;
import static bisect.java.Lists.splitOnCenter;

public class Bisect {

    public static BisectOutcome bisect(Version knownGood, Version knownBad, Scene scene) {
        List<Suspect> extendedSuspects = scene.suspects();

        Optional<Suspect> lastKnowGood = extendedSuspects.stream().filter(suspect -> knownGood.equals(suspect.version()) ).findFirst();
        if (lastKnowGood.isEmpty()) {
            return BisectOutcome.error("good version not in suspects");
        }
        Optional<Suspect> firstKnowBad = extendedSuspects.stream().filter(suspect -> knownBad.equals(suspect.version())).findFirst();
        if (firstKnowBad.isEmpty()) {
            return BisectOutcome.error("bad version not in suspects");
        }
        List<Suspect> suspects = extendedSuspects.subList(1, extendedSuspects.size() - 1);
        Split<Suspect> split = splitOnCenter(suspects);

        while (split.center().isPresent()) {
            Suspect toCheck = split.center().orElseThrow();
            CheckResult result = scene.check(toCheck);
            switch (result) {
                case Good -> lastKnowGood = Optional.of(toCheck);
                case Bad -> firstKnowBad = Optional.of(toCheck);
            }

            split = splitOnCenter(remainintSuspectsFrom(split, result));
        }
        return bisectOutcome(bisectResult(lastKnowGood.orElseThrow(), firstKnowBad.orElseThrow()));
    }

    private static List<Suspect> remainintSuspectsFrom(Split<Suspect> split, CheckResult result) {
        return switch (result) {
            case Skip -> Lists.concat(split.left(), split.right());
            case Bad -> split.left();
            case Good -> split.right();
        };
    }

    public static void main(String[] args) {
    }
}
