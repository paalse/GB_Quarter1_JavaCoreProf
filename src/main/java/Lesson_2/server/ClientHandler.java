package Lesson_2.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private String nick;

    List<String> blackList;

    public String getNick() {
        return nick;
    }

    public ClientHandler(Server server, Socket socket) {
        try {
            this.socket = socket;
            this.server = server;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.blackList = new ArrayList<>();
            new Thread(() -> {
                try {
                    while (true) {
                        String str = in.readUTF();
                        if (str.startsWith("/auth")) { // /auth login72 pass72
                            String[] tokens = str.split(" ");
                            String newNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]); // Получение имени пользователя из базы
                            if (newNick != null) {
                                if (!server.isNickBusy(newNick)) {
                                    sendMsg("/authok");
                                    nick = newNick;
                                    server.subscribe(this);
                                    blackList = AuthService.getBlackList(this.nick);// Загрузка черного списка из базы
                                    server.sendHistory();
                                    break;
                                } else {
                                    sendMsg("Учетная запись уже используется");
                                }
                            } else {
                                sendMsg("Неверный логин/пароль");
                            }
                        }
                    }
                    while (true) {
                        String str = in.readUTF();
                        if (str.startsWith("/")) {
                            if (str.equals("/end")) {
                                out.writeUTF("/serverclosed");
                                break;
                            }
                            // Персональное сообщение пользователю
                            if (str.startsWith("/w ")) {
                                String[] tokens = str.split(" ", 3);
                                server.sendPersonalMsg(this, tokens[1], tokens[2]);
                            }
                            // Добавление пользователя в черный список
                            if (str.startsWith("/blacklist")) {
                                String[] tokens = str.split(" ");
                                AuthService.addToBlackList(this.nick, tokens[1]);
                                sendMsg("Вы добавили пользователя " + tokens[1] + " в черный список");
                                blackList = AuthService.getBlackList(this.nick);// Загрузка черного списка из базы
                            }
                            // Очистка черного списка
                            if (str.startsWith("/clearblacklist")) {
                                AuthService.clearBlackList(this.nick);
                                sendMsg("Черный список очищен");
                                blackList = AuthService.getBlackList(this.nick);
                            }
                            // Получение черного списка
                            if (str.startsWith("/getblacklist")) {
                                sendMsg("Черный список:");
                                for (String bl: blackList) {
                                    sendMsg(bl);
                                }
                            }
                        } else {
                            // Рассылка сообщения всем
                            String currentDate = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date());
                            server.broadcastMsg(this, currentDate + " " + nick + ": " + str);

                            // Сохранение сообщения в истории
                            AuthService.saveMsgInHistory(this.nick, str);
                        }
                        System.out.println("Client: " + str);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    server.unsubscribe(this);
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkBlackList(String nick) {
        return blackList.contains(nick);
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
