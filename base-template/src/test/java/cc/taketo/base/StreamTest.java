package cc.taketo.base;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTest {

    @Test
    public void test() {
        // 创建 Stream
        List<String> list = Arrays.asList("a", "b", "c", "d");
        Stream<String> stream = list.stream();
        System.out.println("stream = " + stream);

        // 过滤出符合条件的元素。
        List<String> filteredList = list.stream()
                .filter(s -> s.startsWith("a"))
                .collect(Collectors.toList());
        System.out.println("filteredList = " + filteredList);// filteredList = [a]

        // 将流中的元素转换成其他对象。
        List<Integer> lengths = list.stream()
                .map(String::length)
                .collect(Collectors.toList());
        System.out.println("lengths = " + lengths);// lengths = [1, 1, 1, 1]

        // 对流中的元素进行排序。
        List<String> sortedList = list.stream()
                .sorted()
                .collect(Collectors.toList());
        System.out.println("sortedList = " + sortedList);// sortedList = [a, b, c, d]

        // 对流中的元素进行聚合操作，如求和、求最大值等。
        Optional<Integer> sum = list.stream()
                .map(String::length)
                .reduce(Integer::sum);
        System.out.println("sum = " + sum); // sum = Optional[4]
        if (sum.isPresent()) {
            System.out.println("Sum of lengths: " + sum.get());
        }

        // 对流中的元素进行匹配操作
        boolean anyStartsWithA = list.stream()
                .anyMatch(s -> s.startsWith("a"));
        System.out.println("anyStartsWithA = " + anyStartsWithA); // anyStartsWithA = true
        boolean allStartWithA = list.stream()
                .allMatch(s -> s.startsWith("a"));
        System.out.println("allStartWithA = " + allStartWithA); // allStartWithA = false
        boolean noneStartsWithZ = list.stream()
                .noneMatch(s -> s.startsWith("z"));
        System.out.println("noneStartsWithZ = " + noneStartsWithZ); // noneStartsWithZ = true

        // 将流中的元素收集到新的集合或其他形式的汇总结果中
        List<String> collectedList = list.stream()
                .collect(Collectors.toList());
        System.out.println("collectedList = " + collectedList); // collectedList = [a, b, c, d]
        Set<String> collectedSet = list.stream()
                .collect(Collectors.toSet());
        System.out.println("collectedSet = " + collectedSet); // collectedSet = [a, b, c, d]
        Map<Character, Long> charCount = list.stream()
                .flatMap(s -> s.chars().mapToObj(c -> (char) c))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println("charCount = " + charCount); // charCount = {a=1, b=1, c=1, d=1}

        // 将流转换为数组
        String[] array = list.stream()
                .toArray(String[]::new);
        System.out.println("array = " + array);

        // 过调用 parallelStream 方法创建并行流，以利用多核处理器进行并行处理
        List<String> parallelFilteredList = list.parallelStream()
                .filter(s -> s.startsWith("a"))
                .collect(Collectors.toList());
        System.out.println("parallelFilteredList = " + parallelFilteredList);
    }
}
