package bisect.java;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static bisect.java.Bisect.bisect;
import static bisect.java.BisectResult.bisectResult;
import static bisect.java.Suspect.suspect;
import static bisect.java.Version.version;
import static com.google.common.truth.Truth8.assertThat;

class BisectTest {
    @Test
    void returnKnownBadIfAllPreviousVesionsAreGood() {
        BisectOutcome outcome = bisect(version("good"), version("bad"), suspects("good", "bad"));
        assertThat(outcome.result).hasValue(bisectResult(suspect(version("good")), suspect(version("bad"))));
    }

    @Test
    void testTheCenterSuspectInsteadOfEverySingleOne(){
        RecordingScene scene = suspects("good", "2nd good", "center good", "1st bad", "bad");
        BisectOutcome outcome = bisect(version("good"), version("bad"), scene);
        assertThat(outcome.result).hasValue(bisectResult(suspect(version("center good")), suspect(version("1st bad"))));
    }

    private RecordingScene suspects(String ... versions) {
        List<Suspect> suspects = Arrays.stream(versions).map(version -> suspect(version(version))).collect(Collectors.toList());
        return new RecordingScene(suspects);
    }
}
