package bisect.java;

import java.util.List;

public class RecordingScene implements Scene {

    private final List<Suspect> suspects;

    public RecordingScene(List<Suspect> suspects) {
        this.suspects = suspects;
    }

    @Override
    public List<Suspect> suspects() {
        return suspects;
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
