package example.com.zadanie1.Repo;

import example.com.zadanie1.bean.Kontakt;

import java.sql.SQLException;

/**
 * Created by ttomaka on 20.03.2017.
 */
public interface KontaktRepo {
    void save(Kontakt kontakt) throws SQLException;
    Kontakt findByPrimaryKey(int id) throws SQLException;
}
