package example.com.zadanie1.impl;

import example.com.zadanie1.bean.User;
import example.com.zadanie1.dao.UserDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by ttomaka on 17.03.2017.
 */
@Repository("UserDaoImpl")
@Transactional(propagation = Propagation.REQUIRED)
public class UserDaoImpl implements UserDao{
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

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
