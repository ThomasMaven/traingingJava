package example.com.zadanie1.Repo;

import example.com.zadanie1.bean.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ttomaka on 17.03.2017.
 */
public interface UserRepo {
    void save(User user);

    User findByPrimaryKey(int id);

    List<User> findByImieAndNazwisko(String imie, String nazwisko);

    List<User> findByImie(String imie);

    List<User> findByNazwisko(String nazwisko);
}
