package study.spring.logic;

import java.util.List;

public interface Sort <T extends Comparable<T>> {
    List<T> sort(List<T> input);
}
