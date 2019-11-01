package Lesson_4;

public class Main {
    private final Object myObj = new Object();
    private volatile char currentLetter = 'A';
    final int cnt = 5;

    public static void main(String[] args) {
        Main curObj = new Main();
        Thread tA = new Thread(() -> curObj.printA());
        Thread tB = new Thread(() -> curObj.printB());
        Thread tC = new Thread(() -> curObj.printC());
        tA.start();
        tB.start();
        tC.start();
    }


    public void printA() {
        synchronized (myObj) {
            try {
                for (int i = 0; i < cnt; i++) {
                    while (currentLetter != 'A') {
                        myObj.wait();
                    }
                    System.out.print("А");
                    currentLetter = 'B';
                    myObj.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printB() {
        synchronized (myObj) {
            try {
                for (int i = 0; i < cnt; i++) {
                    while (currentLetter != 'B') {
                        myObj.wait();
                    }
                    System.out.print("B");
                    currentLetter = 'C';
                    myObj.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printC() {
        synchronized (myObj) {
            try {
                for (int i = 0; i < cnt; i++) {
                    while (currentLetter != 'C') {
                        myObj.wait();
                    }
                    System.out.print("C");
                    currentLetter = 'A';
                    myObj.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
