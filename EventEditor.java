import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class will collect informations for new event
 */
public class EventEditor extends JPanel{
	/**
	 * This enum will store attributes that need to be colected for event
	 */
	enum FieldTitle{
		NAME("Name"), 
		YEAR("Year"), 
		MONTH("Month"), 
		DAY("Day"),
		TIME("Time");
		
		private String title;
		
		private FieldTitle(String title) {
			this.title = title;
		}
		
		public String getTitle() {
	         return title;
	    }
	}
	
	private static final Insets WEST_INSETS = new Insets(5, 0, 5, 5);
	private static final Insets EAST_INSETS = new Insets(5, 5, 5, 0);
	
	private Map<FieldTitle, JTextField> fieldMap = new HashMap<FieldTitle, JTextField>();
	
	/**
	 * Default constructor for EventEditor it sets all look for this window
	 */
	public EventEditor() {
	      setLayout(new GridBagLayout());
	      setBorder(BorderFactory.createCompoundBorder(
	            BorderFactory.createTitledBorder("Event Editor"),
	            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	      GridBagConstraints gbc;
	      for (int i = 0; i < FieldTitle.values().length; i++) {
	    	  	FieldTitle fieldTitle = FieldTitle.values()[i];
	         	gbc = createGbc(0, i);
	         	add(new JLabel(fieldTitle.getTitle() + ":", JLabel.LEFT), gbc);
	         	gbc = createGbc(1, i);
	         	JTextField textField = new JTextField(10);
	         	add(textField, gbc);

	         	fieldMap.put(fieldTitle, textField);
	      }
	}
	
	/**
	 * This will construct new GridBagConstraints in given position
	 * @param x position on x axis
	 * @param y position on y axis
	 * @return constructed object
	 */
	private GridBagConstraints createGbc(int x, int y) {
	      GridBagConstraints gbc = new GridBagConstraints();
	      gbc.gridx = x;
	      gbc.gridy = y;
	      gbc.gridwidth = 1;
	      gbc.gridheight = 1;

	      gbc.anchor = (x == 0) ? GridBagConstraints.WEST : GridBagConstraints.EAST;
	      gbc.fill = (x == 0) ? GridBagConstraints.BOTH
	            : GridBagConstraints.HORIZONTAL;

	      gbc.insets = (x == 0) ? WEST_INSETS : EAST_INSETS;
	      gbc.weightx = (x == 0) ? 0.1 : 1.0;
	      gbc.weighty = 1.0;
	      return gbc;
	   }
	
	/**
	 * This method will return text from selected text field
	 * @param fieldTitle tells what text field to choose
	 * @return text from text field
	 */
	public String getFieldText(FieldTitle fieldTitle) {
		return fieldMap.get(fieldTitle).getText();
	}
}
