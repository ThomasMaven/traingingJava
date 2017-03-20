package example.com.zadanie1.Repo;

import example.com.zadanie1.bean.Kontakt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ttomaka on 20.03.2017.
 */
@Service
@Transactional
public class UserService {
    @Autowired
    KontaktRepo kontaktRepo;

    @Autowired
    UserRepo userRepo;

    public void test() {
        Kontakt kontakt = kontaktRepo.findByPrimaryKey(69);

        System.out.println(kontakt.getValue());

        kontakt.setValue("AAAAA");


    }
}
