package example.com.zadanie1.dao;

import example.com.zadanie1.bean.User;

import java.sql.SQLException;

/**
 * Created by ttomaka on 17.03.2017.
 */
public interface UserDao {
    void save(User user) throws SQLException;
    User findByPrimaryKey(int id) throws SQLException;
}
