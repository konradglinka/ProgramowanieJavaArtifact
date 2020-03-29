import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class JDBC { //Klasa sluzy do laczenia sie z baza danych
    private static Connection connection; //Polaczenie z baza danych
    private static String dbDriverName ="com.mysql.jdbc.Driver"; // driver jdbc
    private static String database = "jdbc:mysql://localhost:3306/weatherapp"; //bazadanych


    public Connection getDbConnection() { //Metoda sluzy do polaczenia sie z bazadanych
        try {
            Class.forName(dbDriverName);
            try {
                connection = DriverManager.getConnection(database, "root", "");

                System.out.println("Udalo sie polaczyc z baza");
            } catch (SQLException e) {
                System.out.println("Nie udało się nawiązać połączenia");

            }
        } catch (ClassNotFoundException e) {
            System.out.println("Problem ze sterownikiem bazy danych");
        }

        return connection;

    }

    public Connection getConnection() { //Metoda zwraca polaczenie
        return connection;
    }
}
