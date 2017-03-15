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
