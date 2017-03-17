package example.com.zadanie1.bean;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.persistence.*;

/**
 * Created by ttomaka on 15.03.2017.
 */
@Entity
@Table(name="osoba")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int userId;

    @Column(name = "imie")
    private String userFirstname;
    @Column(name = "nazwisko")
    private String userLastname;



    private void setUserId(int userId) {
        this.userId = userId;
    }

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

            String userContacts = "";
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

    public static String displayUserContacts( int userId ) {
        String sql = "SELECT t.wartosc as typ, k.wartosc as wartosc from kontakt k " +
                "JOIN kontakt_list kl on k.id=kl.id_kontaktu  " +
                "JOIN osoba os on os.id=kl.id_os JOIN typ t on t.id=k.typ_id WHERE os.id=?";
        Database db = new Database();
        JdbcTemplate template = new JdbcTemplate(db.getDBConnection2());
        String userContacts = "";

        //OLD way:
//        RowMapper kontaktMapper = new RowMapper() {
//            public example.com.zadanie1.bean.UserKontakt mapRow(ResultSet rs, int rowNum) throws SQLException {
//                example.com.zadanie1.bean.UserKontakt kontakt = new example.com.zadanie1.bean.UserKontakt();
//                kontakt.setType(rs.getString("typ"));
//                kontakt.setValue(rs.getString("wartosc"));
//                return kontakt;
//            }
//        };

        //Using labda
        RowMapper kontaktMapper = (rs, rowNum) -> {
            UserKontakt kontakt = new UserKontakt();
            kontakt.setType(rs.getString("typ"));
            kontakt.setValue(rs.getString("wartosc"));
            return kontakt;
        };
        List<UserKontakt> userKontakts = template.query(sql, kontaktMapper, userId);
        for (UserKontakt kontakt : userKontakts) {
            userContacts += kontakt.getType() + ": ";
            userContacts += kontakt.getValue() + "\n";
        }


        return userContacts;
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

    public int saveUser2() {
        String sql = "INSERT INTO osoba (imie, nazwisko) VALUES (?,?) RETURNING id";
        Database db = new Database();
        JdbcTemplate template = new JdbcTemplate(db.getDBConnection2());
        //not able to get id this way
        //String[] customerObject = { getUserFirstname(),getUserLastname()};
        //        template.update(sql, customerObject);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement(sql, new String[] {"id"});
                    ps.setString(1, getUserFirstname());
                    ps.setString(2, getUserLastname());
                    return ps;
                },
                keyHolder);
        userId = keyHolder.getKey().intValue();
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

        List<User> userList = new ArrayList<>();
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
        try (Connection connection = new Database().getDBConnection()) {
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
