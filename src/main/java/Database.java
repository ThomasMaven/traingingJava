import java.sql.*;

/**
 * Created by ttomaka on 15.03.2017.
 */
public class Database {

    public static final String dbHost = "127.0.0.1";
    public static final String dbPort = "5432";
    public static final String dbName = "zadanie1";
    public static final String dbUser = "postgres";
    public static final String dbPassword = "pgspgs";




    public boolean testDBConnection() {

        boolean isConnectionOk = false;
        try (Connection connection = getDBConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT 1");) {

            if (resultSet.next()) isConnectionOk = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isConnectionOk;
    }


    public Connection getDBConnection() {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://"+dbHost+":"+dbPort+"/"+dbName,dbUser, dbPassword);

            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }




}
