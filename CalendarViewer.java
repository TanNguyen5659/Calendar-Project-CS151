import javax.swing.JFrame;

public class CalendarViewer {
	/**
	 * Main method will initialize Application and enable to work with it
	 * @param args arguments for main method
	 */
	public static void main(String args[]) {
		Application app = new Application();
		app.pack();
		app.setVisible(true);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}