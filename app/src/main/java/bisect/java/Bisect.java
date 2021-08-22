package bisect.java;

import java.util.List;
import java.util.stream.IntStream;

import static bisect.java.BisectOutcome.bisectOutcome;
import static bisect.java.BisectResult.bisectResult;
import static bisect.java.Lists.splitOnCenter;

public class Bisect {

    public static BisectOutcome bisect(Version knownGood, Version knownBad, Scene scene) {
        if (knownGood.equals(knownBad)) {
            return BisectOutcome.error("knownGood and knowBad are the same");
        }

        List<Suspect> suspects = scene.suspects();
        int knownGoodIndex = IntStream.range(0, suspects.size()).filter(index -> suspects.get(index).version().equals(knownGood)).findFirst().orElse(-1);
        if (-1 == knownGoodIndex) {
            return BisectOutcome.error("good version not in suspects");
        }
        Suspect lastKnowGood = suspects.get(knownGoodIndex);

        int knownBadIndex = IntStream.range(0, suspects.size()).filter(index -> suspects.get(index).version().equals(knownBad)).findFirst().orElse(-1);
        if (-1 == knownBadIndex) {
            return BisectOutcome.error("bad version not in suspects");
        }
        Suspect firstKnowBad = suspects.get(knownBadIndex);
        if (knownBadIndex < knownGoodIndex) {
            return BisectOutcome.error("bad version before good version");
        }
        List<Suspect> candidates = suspects.subList(knownGoodIndex + 1, knownBadIndex);
        Split<Suspect> split = splitOnCenter(candidates);

        while (split.center().isPresent()) {
            Suspect toCheck = split.center().orElseThrow();
            CheckResult result = scene.check(toCheck);
            switch (result) {
                case Good -> lastKnowGood = toCheck;
                case Bad -> firstKnowBad = toCheck;
            }

            split = splitOnCenter(remainintSuspectsFrom(split, result));
        }
        return bisectOutcome(bisectResult(lastKnowGood, firstKnowBad));
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
