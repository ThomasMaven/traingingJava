import java.util.Scanner;

/**
 * Created by ttomaka on 15.03.2017.
 */
public class App {

    public static void main(String[] args) {
        //int userid = addNewOsoba();
        System.out.println(User.displayUserContacts(1));
    }

    private static int addNewOsoba(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter person name");
        String userFirstname = scanner.nextLine();
        System.out.println("Enter person surname");
        String userLastname = scanner.nextLine();

        User user = new User();
        user.setUserFirstname(userFirstname);
        user.setUserLastname(userFirstname);
        int recordId = user.saveUser2();

        System.out.println("User added with ID: " + recordId);
        return recordId;
    }
    private static void addNewKontakt(int userId){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter contact typeId (1=Telefon, 2=Email)");
        int typeId = scanner.nextInt();
        System.out.println("Enter contact value");
        String contactValue = scanner.nextLine();

        Kontakt kontakt = new Kontakt();
        kontakt.setTypeId(typeId);
        kontakt.setValue(contactValue);

        System.out.println("Contact added" );
    }

}
