import java.util.Arrays;
import java.util.stream.Stream;

public class JavaApp {
    public static void main(String[] args) {
        Integer[] array = {1,2,3,4,5};
        Stream<Integer> stream = Arrays.stream(array);
        Stream<Integer> s2 = stream.map(item -> {
            System.out.println("double " + item);
            return item;
        });
        System.out.println("declared stream2");
        s2.forEach(item -> {
           System.out.println(item);
        });
    }
}
