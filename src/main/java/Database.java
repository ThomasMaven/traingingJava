import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * Created by ttomaka on 15.03.2017.
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;

@Configuration
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
    public boolean testDBConnection2() {
        boolean isConnectionOk = false;
        JdbcTemplate template = new JdbcTemplate(getDBConnection2());
        String sql = "Select 1";
        List<Map<String, Object>> list = template.queryForList(sql);
        return !list.isEmpty();
    }

    @Bean
    public DataSource getDBConnection2() {
        SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://"+dbHost+":"+dbPort+"/"+dbName);
        dataSource.setUsername(dbUser);
        dataSource.setPassword(dbPassword);
        return dataSource;
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
