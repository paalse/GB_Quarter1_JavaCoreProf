package Lesson_1;

/**
 * 1. Написать метод, который меняет два элемента массива местами
 * (массив может быть любого ссылочного типа);
 */
public class MainChangeElemArray {

    public static void chElemArray(Object[] array, int pos1, int pos2) {
        Object tmp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = tmp;
    }

    public static void main(String[] args) {
        String[] arr = {"A", "B", "C", "D"};

        System.out.println("Origin array:");
        for (String s : arr) {
            System.out.print(s + " " );
        }

        chElemArray(arr, 0, 3);

        System.out.println("\nChanged array:");
        for (String s : arr) {
            System.out.print(s + " " );
        }
    }
}