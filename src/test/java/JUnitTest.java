/**
 * Created by ttomaka on 15.03.2017.
 */

import example.com.zadanie1.bean.Database;
import example.com.zadanie1.bean.Kontakt;
import example.com.zadanie1.bean.User;
import junit.framework.TestCase;

import java.util.List;

public class JUnitTest extends TestCase {

    public void testTrue() {
        assertTrue(true);
    }

    public void testGetUserContacts() {
        User user = new User();
        String userContacts = user.getUserContacts(1);
        assertNotNull(userContacts);
    }

    public void testDBConnection() {
        Database db = new Database();
        boolean isConnectionOk = db.testDBConnection();
        assertTrue(isConnectionOk);
    }
    public void testUserSave() {
        User user = new User();
        user.setUserFirstname("Tester");
        user.setUserLastname("Testowicz");
        int recordId = user.saveUser();
        assertTrue(recordId>0);
    }

    public void testSaveKontakt() {
        Kontakt kontakt = new Kontakt();
        kontakt.setTypeId(1);
        kontakt.setValue("123456789");
        int recordId = kontakt.saveContact(1);
        assertTrue(recordId>0);
    }

    public void testFindUser() {
        User user = new User();
        List<User> findResult = user.findUsersByNameAndSurname("Jan", "Kowalski");
        for(User currentUser: findResult) {
            System.out.println("Found: " + currentUser.getUserFirstname() + " " + currentUser.getUserLastname());
        }
        assertTrue(findResult.size()>0);
    }
    public void testTransactionExample() {
        User user = new User();
        user.transactionExample();

    }


    //JdbcTemplate
    public void testUserSave2() {
        User user = new User();
        user.setUserFirstname("Jan");
        user.setUserLastname("Nowak");
        int recordId = user.saveUser2();
        assertTrue(recordId>0);
    }

    public void testDBConnection2() {
        Database db = new Database();
        boolean isConnectionOk = db.testDBConnection2();
        assertTrue(isConnectionOk);
    }
    public void testSaveKontakt2() {
        Kontakt kontakt = new Kontakt();
        kontakt.setTypeId(1);
        kontakt.setValue("321321321");
        int recordId = kontakt.saveContact(1);
        assertTrue(recordId>0);
    }
}
