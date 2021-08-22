package bisect.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Optional.empty;

public class Lists {

    public static <T> Split<T> split(List<T> input) {
        int size = input.size();
        if (size == 0) {
            return new Split<>(emptyList(), empty(), emptyList());
        }
        boolean evenNumberOfElements = (size % 2) == 0;
        int leftSize = evenNumberOfElements ? size / 2 : (size - 1) / 2;
        List<T> left = input.subList(0, leftSize);
        Optional<T> center = Optional.of(input.get(leftSize));
        List<T> right = input.subList(leftSize + 1, size);
        return new Split<>(left, center, right);
    }
}
