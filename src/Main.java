import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[]args) {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Entrez votre nom d'utilisateur: ");
		String userName = scan.nextLine();
		
		char password[] = null;
		try {
			password = PasswordField.getPassword(System.in, "Entrez votre mot de passe: ");
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		if(password == null) {
			System.out.println("Aucun mot de passe n'a été entré.");
		} else {
			System.out.println("Le mot de passe entré est: " + String.valueOf(password));
		}
		
	}
}
