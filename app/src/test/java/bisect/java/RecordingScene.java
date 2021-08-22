package bisect.java;

import java.util.LinkedList;
import java.util.List;

public class RecordingScene implements Scene {

    private final List<Suspect> suspects;
    public final List<Version> checked = new LinkedList<>();

    public RecordingScene(List<Suspect> suspects) {
        this.suspects = suspects;
    }

    @Override
    public List<Suspect> suspects() {
        return suspects;
    }

    @Override
    public CheckResult check(Suspect suspect) {
        Version version = suspect.version();
        this.checked.add(version);
        String versionString = version.string();
        if (versionString.contains("good")) {
            return CheckResult.Good;
        }
        if (versionString.contains("bad")) {
            return CheckResult.Bad;
        }
        throw new RuntimeException(String.format("unhandled version '%s'", versionString));
    }
}
