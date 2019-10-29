package Lesson_3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Написать консольное приложение, которое умеет постранично читать текстовые файлы (размером > 10 mb).
 * Вводим страницу (за страницу можно принять 1800 символов), программа выводит ее в консоль.
 * Контролируем время выполнения: программа не должна загружаться дольше 10 секунд, а чтение – занимать свыше 5 секунд.
 */
public class MainTask3 {
    public static void main(String[] args) {

        try {
            // Создание файла
//            FileOutputStream outFile = new FileOutputStream("TestFile/task3.txt");
//            byte[] arr = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', '\n'};
//            for (int i = 0; i < 1000000; i++) {
//                outFile.write(arr);
//            }
//            outFile.close();

            // Чтение из файла
            long t = System.currentTimeMillis();
            FileInputStream inFile = new FileInputStream("TestFile/task3.txt");
            int x;
            byte[] arr1 = new byte[1800];

            while ((x = inFile.read(arr1)) != -1) {
                  System.out.print(new String(arr1, 0, x, "UTF-8"));

            }
            inFile.close();
            System.out.println(System.currentTimeMillis() - t);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}