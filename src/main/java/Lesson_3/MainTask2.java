package Lesson_3;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 2. Последовательно сшить 5 файлов в один (файлы примерно 100 байт).
 * Может пригодиться следующая конструкция:
 * ArrayList<InputStream> al = new ArrayList<>(); ... Enumeration<InputStream> e = Collections.enumeration(al);
 */
public class MainTask2 {
    public static void main(String[] args) {
        ArrayList<InputStream> allFiles = new ArrayList<>();
        try {
            allFiles .add(new FileInputStream("TestFile/task2_1.txt"));
            allFiles .add(new FileInputStream("TestFile/task2_2.txt"));
            allFiles .add(new FileInputStream("TestFile/task2_3.txt"));
            allFiles .add(new FileInputStream("TestFile/task2_4.txt"));
            allFiles .add(new FileInputStream("TestFile/task2_5.txt"));
            SequenceInputStream in = new SequenceInputStream(Collections.enumeration(allFiles));
            int x;
            byte[] arr = new byte[512];
            while ((x = in.read(arr)) != -1) {
                System.out.print(new String(arr, 0, x, "UTF-8"));
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}