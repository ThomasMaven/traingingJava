package example.com.zadanie1.impl;

import example.com.zadanie1.bean.Kontakt;
import example.com.zadanie1.dao.KontaktDao;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.SQLException;

/**
 * Created by ttomaka on 20.03.2017.
 */
@Repository("KontaktDaoImpl")
@Transactional(propagation = Propagation.REQUIRED)
public class KontaktDaoImpl implements KontaktDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Kontakt kontakt) throws SQLException {
        entityManager.persist(kontakt);
    }

    @Override
    public Kontakt findByPrimaryKey(int id) throws SQLException {
        Kontakt kontakt = entityManager.find(Kontakt.class, id);
        return kontakt;
    }


    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
