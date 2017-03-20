package example.com.zadanie1.Repo;

import example.com.zadanie1.bean.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ttomaka on 17.03.2017.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class UserRepoImpl implements UserRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public User findByPrimaryKey(int id) {
        User user = entityManager.find(User.class, id);
        return user;
    }

    @Override
    public List<User> findByImieAndNazwisko(String imie, String nazwisko) {
        TypedQuery<User> query = entityManager.createQuery("Select o from User o where o.userFirstname = :firstname " +
                "and o.userLastname = :lastname", User.class);
        query.setParameter("firstname", imie);
        query.setParameter("lastname", nazwisko);
        return query.getResultList();
    }

    @Override
    public List<User> findByImie(String imie) {
        TypedQuery<User> query = entityManager.createQuery("Select o.id from User o where o.userFirstname = :firstname", User.class);
        query.setParameter("firstname", imie);
        return query.getResultList();
    }

    @Override
    public List<User> findByNazwisko(String nazwisko) {
        Query query = entityManager.createQuery("Select o.id from User o where o.userLastname = :lastname");
        query.setParameter("lastname", nazwisko);
        return query.getResultList();
    }

}
