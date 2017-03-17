import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ttomaka on 15.03.2017.
 */
public class Kontakt {
    int typeId;
    String value;
    int kontaktId;

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int saveContact(int userId) {
        try (Connection connection = new Database().getDBConnection();
             PreparedStatement ps = createPreparedSaveContact(connection);
             ResultSet resultSet = ps.executeQuery()) {
            if ( resultSet.next() ) {
                int rowId = resultSet.getInt(1);
                if ( rowId > 0 ) {
                    kontaktId = rowId;
                    connectKontaktToUser(userId);
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kontaktId;
    }
    public int saveContact2(int userId) {
        String sql = "INSERT INTO kontakt (typ_id, wartosc) VALUES (?,?) RETURNING id";
        Database db = new Database();
        JdbcTemplate template = new JdbcTemplate(db.getDBConnection2());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(sql, new String[] {"id"});
                        ps.setInt(1, getTypeId());
                        ps.setString(2, getValue());
                        return ps;
                    }
                },
                keyHolder);
        kontaktId = keyHolder.getKey().intValue();
        if ( kontaktId > 0 ) {
            String sql2 = "INSERT INTO kontakt_list (id_os, id_kontaktu) VALUES (?,?)";
            int[] paramObject = { userId, kontaktId};
            template.update(sql2, paramObject);

        }
        return kontaktId;
    }

    private PreparedStatement createPreparedSaveContact(Connection con) throws SQLException {
        String sql = "INSERT INTO kontakt (typ_id, wartosc) VALUES (?,?) RETURNING id";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, this.getTypeId());
        ps.setString(2, this.getValue());
        return ps;
    }

    private int connectKontaktToUser(int userId) {
        int recordId = 0;
        try (Connection connection = new Database().getDBConnection();
             PreparedStatement ps = createPreparedConnectKontaktToUser(connection, userId);
             ResultSet resultSet = ps.executeQuery()) {
            if ( resultSet.next() ) {
                recordId = resultSet.getInt(1);

            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recordId;
    }
    private PreparedStatement createPreparedConnectKontaktToUser(Connection con, int userId) throws SQLException {
        String sql = "INSERT INTO kontakt_list (id_os, id_kontaktu) VALUES (?,?) RETURNING id";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, userId);
        ps.setInt(2, kontaktId);
        return ps;
    }


}
