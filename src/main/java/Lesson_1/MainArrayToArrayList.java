package Lesson_1;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 2. Написать метод, который преобразует массив в ArrayList;
 */
public class MainArrayToArrayList {

    public static <T> ArrayList<T> ArrayToArrayList(T[] array) {
        return new ArrayList<T>(Arrays.asList(array));
    }

    public static void main(String[] args) {
        String[] arr = {"A", "B", "C", "D"};
        ArrayList<?> arrList = ArrayToArrayList(arr);
    }
}
