package bisect.java;

import java.util.List;

interface Scene {
    List<Suspect> suspects();

    CheckResult check(Suspect suspect);
}
