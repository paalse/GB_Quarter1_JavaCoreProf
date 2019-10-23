package Lesson_2.server;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuthService {
    private static Connection connection;
    private static Statement stmt;

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:users.db");
            stmt = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addUser(String login, String pass, String nick) {
        try {
            String query = "INSERT INTO main (login, password, nickname) VALUES (?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, login);
            ps.setInt(2, pass.hashCode());
            ps.setString(3, nick);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getNickByLoginAndPass(String login, String pass) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT nickname, password FROM main WHERE login = '" + login + "'");
            int myHash = pass.hashCode();
            if (rs.next()) {
                String nick = rs.getString(1);
                int dbHash = rs.getInt(2);
                if (myHash == dbHash) {
                    return nick;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Получение черного списка пользователя
     *
     * @param nick - имя пользователя
     * @return
     */
    public static List<String> getBlackList(String nick) {
        List<String> result = new ArrayList<String>();
        try {
            ResultSet rs = stmt.executeQuery("select a.nickname from main a where a.id in  (select b.id_block_user from main m \n" +
                    "join blacklist b on b.id_user = m.id\n" +
                    "where m.nickname = '" + nick + "')");
            while (rs.next()) {
                result.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Получение ID пользователя по имени
     *
     * @param nick - имя пользователя
     * @return
     */
    public static int getUserId(String nick) {
        int result = -1;
        try {
            ResultSet rs = stmt.executeQuery("SELECT id FROM main WHERE nickname = '" + nick + "'");
            if (rs.next()) {
                result = Integer.parseInt(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Добавление в черный список
     *
     * @param nick
     */
    public static void addToBlackList(String nick, String block_nick) {
        int userId = getUserId(nick);
        int userIdBlock = getUserId(block_nick);
        try {
            String query = "INSERT INTO blacklist (id_user, id_block_user) VALUES (?, ?);";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setInt(2, userIdBlock);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Очистить весь черный список
     *
     * @param nick - имя пользователя черный список которого очищается
     */
    public static void clearBlackList(String nick) {
        int userId = getUserId(nick);
        try {
            String query = "delete from blacklist where id_user = " + userId + ";";
            PreparedStatement ps = connection.prepareStatement(query);
            //ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveMsgInHistory(String nick, String msg) {
        int userId = getUserId(nick);
        java.util.Date dateNow = new Date();

        try {
            String query = "INSERT INTO msghistory (user_id, msg_date, message) values (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setDate(2, new java.sql.Date(dateNow.getTime()));
            ps.setString(3, msg);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String loadMsgInHistory() {
        String result = "";
        try {
            ResultSet rs = stmt.executeQuery("SELECT h.msg_date, m.nickname, h.message FROM msghistory h JOIN main m on h.user_id = m.id");
            while (rs.next()) {
                //!!! Разобраться с преобразованием даты !!!
                String msgDate = ""; //= new SimpleDateFormat("dd.MM.yyyy HH:mm").format(rs.getString(1));
                result = result + msgDate + " " + rs.getString(2) + ": " + rs.getString(3) + "\n";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}