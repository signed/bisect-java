package bisect.java;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static bisect.java.Bisect.bisect;
import static bisect.java.Suspect.suspect;
import static bisect.java.Version.version;
import static com.google.common.truth.Truth.assertThat;

class BisectTest {
    @Test
    void returnKnownBadIfAllPreviousVesionsAreGood() {
        BisectResult success = bisectSuccess("good", "bad", suspects("good", "bad"));

        assertThat(success.lastGood()).isEqualTo(suspect(version("good")));
        assertThat(success.firstBad()).isEqualTo(suspect(version("bad")));
    }

    @Test
    void onlyCheckVersionsBetweenKnownGoodAndKnownBad() {
        RecordingScene scene = suspects("good", "1st bad", "bad");
        BisectResult success = bisectSuccess("good", "bad", scene);

        assertThat(success.lastGood()).isEqualTo(suspect(version("good")));
        assertThat(success.firstBad()).isEqualTo(suspect(version("1st bad")));
    }

    @Test
    void testTheCenterSuspectInsteadOfEverySingleOne() {
        RecordingScene scene = suspects("good", "2nd good", "center good", "1st bad", "bad");
        BisectResult success = bisectSuccess("good", "bad", scene);

        assertThat(success.lastGood()).isEqualTo(suspect(version("center good")));
        assertThat(success.firstBad()).isEqualTo(suspect(version("1st bad")));
        assertThat(scene.checkedVersions).containsExactly(version("center good"), version("1st bad")).inOrder();
    }

    @Test
    void skipVersionswhereYouCanNotDecideIfItIsGoodOrBad() {
        RecordingScene scene = suspects("good", "2nd good", "center skip", "1st bad", "bad");
        BisectResult success = bisectSuccess("good", "bad", scene);

        assertThat(success.lastGood()).isEqualTo(suspect(version("2nd good")));
        assertThat(success.firstBad()).isEqualTo(suspect(version("1st bad")));
        assertThat(scene.checkedVersions).containsExactlyElementsIn(versions("center skip", "1st bad", "2nd good")).inOrder();
    }

    @SuppressWarnings("SameParameterValue")
    private BisectResult bisectSuccess(String knownGood, String knownBad, RecordingScene scene) {
        BisectOutcome outcome = bisect(version(knownGood), version(knownBad), scene);
        Optional<BisectResult> result = outcome.result;
        return result.orElseThrow();
    }

    private List<Version> versions(String... versionStrings) {
        return Arrays.stream(versionStrings).map(Version::version).collect(Collectors.toList());
    }

    private RecordingScene suspects(String... versions) {
        List<Suspect> suspects = Arrays.stream(versions).map(version -> suspect(version(version))).collect(Collectors.toList());
        return new RecordingScene(suspects);
    }
}
