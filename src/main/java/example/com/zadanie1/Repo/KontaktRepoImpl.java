package example.com.zadanie1.Repo;

import example.com.zadanie1.bean.Kontakt;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.SQLException;

/**
 * Created by ttomaka on 20.03.2017.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class KontaktRepoImpl implements KontaktRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Kontakt kontakt) {
        entityManager.persist(kontakt);
    }

    @Override
    public Kontakt findByPrimaryKey(int id) {
        Kontakt kontakt = entityManager.find(Kontakt.class, id);
        return kontakt;
    }

}

