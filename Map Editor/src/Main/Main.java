package Main;

import javax.swing.UIManager;

public class Main {
	
	public static void main(String[] args) {
	
		
		//Ziska aktualnu temu systemu uzivatela	
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		new Okno();
		
	}
}