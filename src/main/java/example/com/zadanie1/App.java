package example.com.zadanie1;

import example.com.zadanie1.bean.Kontakt;
import example.com.zadanie1.bean.User;
import example.com.zadanie1.Repo.KontaktRepo;
import example.com.zadanie1.Repo.UserRepo;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by ttomaka on 15.03.2017.
 */
public class App {

    public static void main(String[] args) {
//        int userId = addNewOsoba();
//        addNewKontakt(userId);
//        System.out.println(User.displayUserContacts(userId));


        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("spring-configuration.xml");
            UserRepo userRepo = context.getBean("UserRepoImpl", UserRepo.class);
            User user = new User("Gal", "Anonim");

            //Save user to DB
            userRepo.save(user);
            System.out.println("User saved with ID= " + user.getUserId());

            //find user
            User foundUser = userRepo.findByPrimaryKey(101);
            System.out.println(foundUser);

            List<User> foundUsers = userRepo.findByImie("Tomasz");
            List<User> foundUsers2 = userRepo.findByNazwisko("Tomaka");
            List<User> foundUsers3 = userRepo.findByImieAndNazwisko("Tomasz", "Tomaka");


            KontaktRepo kontaktRepo = context.getBean("KontaktRepoImpl", KontaktRepo.class);
            Kontakt kontakt = new Kontakt(1, "999000999");
            kontaktRepo.save(kontakt);
            System.out.println("Kontakt saved with ID= "+kontakt.getKontaktId());



        } catch (SQLException e) {
            e.printStackTrace();
        }

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
