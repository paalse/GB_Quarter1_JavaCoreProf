package Lesson_2;

import java.sql.*;

public class MainDB {
    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement pstmt;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        connect();
        create();
        System.out.println("-----------Insert------------");
        insert("Ivan", 30);
        insert("Petr", 40);
        selectAll();
        System.out.println("-----------Update------------");
        update("Ivan", 60);
        select("Ivan");
        System.out.println("-----------Delete------------");
        delete("Ivan");
        selectAll();
        System.out.println("-----------------------------");
        drop();
        close();
    }

    /**
     * Создание соединения с БД
     *
     * @throws ClassNotFoundException
     */
    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");                                       // Драйвер БД
        System.out.println("Драйвер подключен");
        connection = DriverManager.getConnection("jdbc:sqlite:main.db");    // К какой БД подключаться
        System.out.println("Соединение установлено");
        stmt = connection.createStatement();                                    // Инициализация стайтмента
    }

    /**
     * Закрытие соединения с БД
     *
     * @throws SQLException
     */
    public static void close() throws SQLException {
        connection.close();
    }

    /**
     * Создание таблицы в БД
     */
    public static void create() throws SQLException {
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS mytable (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "score INTEGER)");
        System.out.println("Таблица создана");
    }

    /**
     * Вставка записи в таблицу БД
     */
    public static void insert(String name, int score) throws SQLException {
        pstmt = connection.prepareStatement("INSERT INTO mytable (name, score) values (?, ?)");
        pstmt.setString(1, name);
        pstmt.setInt(2, score);
        pstmt.execute();
        System.out.println("Данные вставлены в таблицу");
    }

    /**
     * Чтение данных из таблицы БД по имени
     */
    public static void select(String name) throws SQLException {
        ResultSet rs = stmt.executeQuery(String.format("SELECT * FROM mytable WHERE name ='%s'", name));
        while (rs.next()) {
            System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3));
        }
        rs.close();
    }


    /**
     * Чтение данных из таблицы БД по имени
     */
    public static void selectAll() throws SQLException {
        ResultSet rs1 = stmt.executeQuery("SELECT * FROM mytable");
        while (rs1.next()) {
            System.out.println(rs1.getInt(1) + " " + rs1.getString(2) + " " + rs1.getInt(3));
        }
        rs1.close();
    }

    /**
     * Изменение записи в таблице БД
     */
    public static void update(String name, int score) throws SQLException {
        pstmt = connection.prepareStatement("UPDATE mytable SET score = ? WHERE name = ?");
        pstmt.setInt(1, score);
        pstmt.setString(2, name);
        pstmt.execute();
        System.out.println("Данные изменены");
    }

    /**
     * Удаление записи из таблицы БД
     */
    public static void delete(String name) throws SQLException {
        stmt.executeUpdate(String.format("DELETE FROM mytable WHERE name = '%s'", name));
        System.out.println("Данные удалены из таблицы");
    }

    /**
     * Удаление таблицы из БД
     */
    public static void drop() throws SQLException {
        stmt.execute("DROP TABLE IF EXISTS mytable");
        System.out.println("Таблица удалена");
    }
}