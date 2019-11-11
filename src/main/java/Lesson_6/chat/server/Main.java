package Lesson_6.Chat.server;


import org.apache.log4j.Logger;

public class Main {
    public static Logger logFile = Logger.getLogger("file");
    public static void main(String[] args) {
        new Server();
    }
}
