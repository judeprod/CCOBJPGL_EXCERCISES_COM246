import java.io.File;
import java.util.Scanner;

public class App {
    public static void main (String args[])throws Exception{
        System.out.println("Enter Username: ");

        Scanner loginScanner = new Scanner (System.in);

        String username_from_input = loginScanner.nextLine();

        System.out.println("Enter Password: ");

        String password_from_input = loginScanner.nextLine();
        
        User me = new User(username_from_input, password_from_input);
        
        System.out.println("Your username is " + username_from_input );
        System.out.println("Your password is " + password_from_input );
        
        File myfile = new File("accounts.txt");
        
        if (myfile.exists()){
            System.out.println("File exist");
        }

        Scanner fileScanner = new Scanner(myfile);

        Boolean accountexists= false;
         while (fileScanner.hasNextLine()) {

            String filedata = fileScanner.nextLine();
            System.out.println(filedata);

            String username_from_file = filedata.split(",") [0];
            String password_from_file = filedata.split(",") [1];
            
            if (username_from_file.equals(username_from_input) && password_from_file.equals(password_from_input)){
                accountexists = true;
                break;
            } 
        }

        if(accountexists){
            System.out.println("Login Successful, Hello " + me.getUsername());
        }
        else{
            System.out.println("Account does not exist");
        }
        fileScanner.close();
    }
}
