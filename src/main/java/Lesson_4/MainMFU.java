package Lesson_4;

/**
 * 2. Создать модель MFU с возможностью печати и сканирования (данные процессы могут происходить параллельно).
 */
public class MainMFU {

    public static void main(String[] args) {
        MainMFU myMFU = new MainMFU();
        Thread sc = new Thread(() -> myMFU.scan());
        Thread pr = new Thread(() -> myMFU.print());
        sc.start();
        pr.start();
    }

    public static void scan () {
        System.out.println("Сканирование документа");
    }

    public static void print () {
        System.out.println("Распечатка документа");
    }
}
