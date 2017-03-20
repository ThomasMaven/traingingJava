package example.com.zadanie1.Repo;

import example.com.zadanie1.bean.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ttomaka on 17.03.2017.
 */
@Repository("UserRepoImpl")
@Transactional(propagation = Propagation.REQUIRED)
public class UserRepoImpl implements UserRepo {
    @PersistenceContext
    EntityManager entityManager;

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
        List<User> userList = new ArrayList<User>();

        Query query = entityManager.createQuery("Select o.id from User o where o.userFirstname = :firstname " +
                "and o.userLastname = :lastname" );
        query.setParameter("firstname", imie);
        query.setParameter("lastname", nazwisko);
        List<Integer> idList = query.getResultList();

        for(Integer userId : idList) {
            User tmpUser = findByPrimaryKey(userId);
            if(tmpUser != null) {
                userList.add(tmpUser);
            }
        }
        return userList;    }

    @Override
    public List<User> findByImie(String imie) {
        List<User> userList = new ArrayList<User>();

        Query query = entityManager.createQuery("Select o.id from User o where o.userFirstname = :firstname" );
        query.setParameter("firstname", imie);
        List<Integer> idList = query.getResultList();

        for(Integer userId : idList) {
            User tmpUser = findByPrimaryKey(userId);
            if(tmpUser != null) {
                userList.add(tmpUser);
            }
        }
        return userList;
    }
    @Override
    public List<User> findByNazwisko(String nazwisko) {
        List<User> userList = new ArrayList<User>();

        Query query = entityManager.createQuery("Select o.id from User o where o.userLastname = :lastname" );
        query.setParameter("lastname", nazwisko);
        List<Integer> idList = query.getResultList();

        for(Integer userId : idList) {
            User tmpUser = findByPrimaryKey(userId);
            if(tmpUser != null) {
                userList.add(tmpUser);
            }
        }
        return userList;
    }


    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
