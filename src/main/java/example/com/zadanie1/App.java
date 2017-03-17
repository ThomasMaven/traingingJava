package example.com.zadanie1;

import example.com.zadanie1.bean.Kontakt;
import example.com.zadanie1.bean.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

/**
 * Created by ttomaka on 15.03.2017.
 */
public class App {

    public static void main(String[] args) {
//        int userId = addNewOsoba();
//        addNewKontakt(userId);
//        System.out.println(User.displayUserContacts(userId));

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-configuration.xml");
    }

    private static int addNewOsoba(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter person name");
        String userFirstname = scanner.nextLine();
        System.out.println("Enter person surname");
        String userLastname = scanner.nextLine();

        User user = new User();
        user.setUserFirstname(userFirstname);
        user.setUserLastname(userLastname);
        int recordId = user.saveUser2();

        System.out.println("\n\nexample.com.zadanie1.bean.User contacts:\n");
        System.out.println("example.com.zadanie1.bean.User added with ID: " + recordId);
        return recordId;
    }
    private static void addNewKontakt(int userId){
        Scanner scanner = new Scanner(System.in);

        int typeId = 0;
        while( typeId != -1 ) {
            System.out.println("Enter contact typeId (1=Telefon, 2=Email) or -1 to quit");
            try {
                typeId = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                System.out.println("Not a number");
                return;
            }
            if ( typeId !=-1 ) {
                System.out.println("Enter contact value");
                String contactValue = scanner.nextLine();

                Kontakt kontakt = new Kontakt();
                kontakt.setTypeId(typeId);
                kontakt.setValue(contactValue);
                kontakt.saveContact(userId);

                System.out.println("Contact added");
            }
        }
    }

}
