package bisect.java;

import java.util.LinkedList;
import java.util.List;

public class RecordingScene implements Scene {

    private final List<Suspect> suspects;
    public final List<Version> checkedVersions = new LinkedList<>();

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
        String versionString = version.string();
        if (versionString.contains("before")) {
            throw new RuntimeException("you should not check suspects before knowGood");
        }
        if (versionString.contains("after")) {
            throw new RuntimeException("you should not check suspects after known bad");
        }
        if ("good".equals(versionString) ) {
            throw new RuntimeException("you should not check known good");
        }
        if ("bad".equals(versionString) ) {
            throw new RuntimeException("you should not check known bad");
        }
        this.checkedVersions.add(version);
        if (versionString.contains("good")) {
            return CheckResult.Good;
        }
        if (versionString.contains("bad")) {
            return CheckResult.Bad;
        }
        throw new RuntimeException(String.format("unhandled version '%s'", versionString));
    }
}
