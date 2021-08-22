package bisect.java;

import java.util.Arrays;
import java.util.List;

import static bisect.java.Suspect.suspect;
import static bisect.java.Version.version;

public class RecordingScene implements Scene {
    @Override
    public List<Suspect> suspects() {
        return Arrays.asList(suspect(version("good")), suspect(version("bad")));
    }

    @Override
    public CheckResult check(Suspect suspect) {
        String version = suspect.version().string();
        if (version.contains("good")) {
            return CheckResult.Good;
        }
        if (version.contains("bad")) {
            return CheckResult.Bad;
        }
        throw new RuntimeException(String.format("unhandled version '%s'", version));
    }
}
