package study;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import study.javaoop.logic.BubbleSort;
import study.javaoop.logic.Sort;
import study.spring.config.Config;
import study.spring.service.SortService;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        SortService sortService = context.getBean(SortService.class);

        Sort<String> bubbleSort = new BubbleSort<>();

        System.out.println("result1 = " + bubbleSort.sort(Arrays.asList(args)));
        System.out.println("result2 = " + sortService.doSort(Arrays.asList(args)));
    }
}
