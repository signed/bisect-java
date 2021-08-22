package bisect.java;

import java.util.List;
import java.util.Optional;

public record Split<T>(List<T> left, Optional<T> center, List<T>right) {
}
