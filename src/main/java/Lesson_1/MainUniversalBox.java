package Lesson_1;

/**
 * 1. Написать метод, который меняет два элемента массива местами (массив может быть любого ссылочного типа);
 */
public class MainUniversalBox {
    public static void main(String[] args) {
        Integer[] inums = {1, 2, 3, 4, 5};
        Double[] idouble = {1.0, 2.5, 3.0, 4.0, 5.0};
        String[] istring = {"A", "B", "C", "D"};

        UniversalBox<Integer> intObj = new UniversalBox<Integer>(inums);
        UniversalBox<Double> doubleObj = new UniversalBox<Double>(idouble);
        UniversalBox<String> strObj = new UniversalBox<String>(istring);

        System.out.println("Origin arrays:");
        intObj.info();
        doubleObj.info();
        strObj.info();

        intObj.SwapElementOfArray(0, 3);
        doubleObj.SwapElementOfArray(0, 3);
        strObj.SwapElementOfArray(0, 3);

        System.out.println("Changed arrays:");
        intObj.info();
        doubleObj.info();
        strObj.info();

    }
}