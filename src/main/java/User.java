import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ttomaka on 15.03.2017.
 */
public class User {

    String userFirstname;
    String userLastname;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    int userId;

    public String getUserFirstname() {
        return userFirstname;
    }

    public void setUserFirstname(String userFirstname) {
        this.userFirstname = userFirstname;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public void setUserLastname(String userLastname) {
        this.userLastname = userLastname;
    }

    public String getUserContacts(Integer userId) {

        try (Connection connection = new Database().getDBConnection();
             PreparedStatement ps = createPreparedStatementGetUserContacts(connection, userId);
             ResultSet resultSet = ps.executeQuery()) {

            String userContacts = new String();
            while ( resultSet.next() ) {
                userContacts += resultSet.getString("typ") + ": ";
                userContacts += resultSet.getString("wartosc") + "\n";
            }
            connection.close();
            return userContacts;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private PreparedStatement createPreparedStatementGetUserContacts(Connection con, int userId) throws SQLException {
        String sql = "SELECT t.wartosc as typ, k.wartosc as wartosc from kontakt k " +
                "JOIN kontakt_list kl on k.id=kl.id_kontaktu  " +
                "JOIN osoba os on os.id=kl.id_os JOIN typ t on t.id=k.typ_id WHERE os.id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, userId);
        return ps;
    }

    public int saveUser() {
        try (Connection connection = new Database().getDBConnection();
             PreparedStatement ps = createPreparedStatementSave(connection);
             ResultSet resultSet = ps.executeQuery()) {
            if ( resultSet.next() ) userId = resultSet.getInt(1);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }
    private PreparedStatement createPreparedStatementSave(Connection con) throws SQLException {
        String sql = "INSERT INTO osoba (imie, nazwisko) VALUES (?,?) RETURNING id";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, this.getUserFirstname());
        ps.setString(2, this.getUserLastname());
        return ps;
    }

    public List<User> findUsersByNameAndSurname(String userFirstname, String userLastname) {

        List<User> userList = new ArrayList<User>();
        try (Connection connection = new Database().getDBConnection();
             PreparedStatement ps = createPreparedStatementFindUsersByNameAndSurname(connection, userFirstname, userLastname);
             ResultSet resultSet = ps.executeQuery()) {

            while ( resultSet.next() ) {
                User userToAdd = new User();
                userToAdd.setUserId(resultSet.getInt("id"));
                userToAdd.setUserFirstname(resultSet.getString("imie"));
                userToAdd.setUserLastname(resultSet.getString("nazwisko"));
                userList.add(userToAdd);
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    private PreparedStatement createPreparedStatementFindUsersByNameAndSurname(Connection con, String firstname, String lastname) throws SQLException {
        String sql = "SELECT * from osoba where imie = ? and nazwisko = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, firstname);
        ps.setString(2, lastname);
        return ps;
    }

    public void transactionExample() {
        try (Connection connection = new Database().getDBConnection();) {
            connection.setAutoCommit(false);
            for (int i=0; i<5; i++) {
                PreparedStatement ps = createPreparedTransactionExample(connection);
                ResultSet resultSet = ps.executeQuery();
            }
            connection.rollback();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private PreparedStatement createPreparedTransactionExample(Connection con) throws SQLException {
        String sql = "INSERT INTO osoba (imie, nazwisko) VALUES (?,?) RETURNING id";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, UUID.randomUUID().toString());
        ps.setString(2, UUID.randomUUID().toString());
        return ps;
    }

}
