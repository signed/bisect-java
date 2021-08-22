package bisect.java;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth8.assertThat;

class ListsTest {

    @Test
    void splitEmptyArray() {
        Split<String> split = Lists.split(Collections.emptyList());
        assertThat(split.left()).isEmpty();
        assertThat(split.center()).isEmpty();
        assertThat(split.right()).isEmpty();
    }

    @Test
    void putSingleElementIntoCenter() {
        Split<String> split = Lists.split(List.of("one"));
        assertThat(split.left()).isEmpty();
        assertThat(split.center()).hasValue("one");
        assertThat(split.right()).isEmpty();
    }

    @Test
    void putExcessElementIntoLeft() {
        Split<String> split = Lists.split(List.of("one", "two"));
        assertThat(split.left()).containsExactly("one").inOrder();
        assertThat(split.center()).hasValue("two");
        assertThat(split.right()).isEmpty();
    }

    @Test
    void whenPossibleDistributeEvenlyBetweenLeftAndRight() {
        Split<String> split = Lists.split(List.of("left", "center", "right"));
        assertThat(split.left()).containsExactly("left").inOrder();
        assertThat(split.center()).hasValue("center");
        assertThat(split.right()).containsExactly("right").inOrder();
    }

    @Test
    void withMoreElements() {
        Split<String> split = Lists.split(List.of("left", "left", "center", "right", "right"));
        assertThat(split.left()).containsExactly("left", "left").inOrder();
        assertThat(split.center()).hasValue("center");
        assertThat(split.right()).containsExactly("right", "right").inOrder();
    }

    @Test
    void withMoreElementsUneven() {
        Split<String> split = Lists.split(List.of("left", "left", "left", "center", "right", "right"));
        assertThat(split.left()).containsExactly("left", "left", "left").inOrder();
        assertThat(split.center()).hasValue("center");
        assertThat(split.right()).containsExactly("right", "right").inOrder();
    }
}
