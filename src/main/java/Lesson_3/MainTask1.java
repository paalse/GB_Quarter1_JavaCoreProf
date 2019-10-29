package Lesson_3;

import java.io.*;

/**
 * 1. Прочитать файл (около 50 байт) в байтовый массив и вывести этот массив в консоль;
 */

public class MainTask1 {
    public static void main(String[] args) {
        try (InputStreamReader isr = new InputStreamReader(new FileInputStream("TestFile/task1.txt"), "UTF-8")) {
            int x;
            while ((x = isr.read()) != -1) {
                System.out.print((char) x);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
