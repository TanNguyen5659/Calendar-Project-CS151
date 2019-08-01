import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * This class will produce center part of window. All tabs will be here
 *
 */
public class Planner extends JTabbedPane{
	private Calendar date;
	private JComponent dayTab;
	private JComponent weekTab;
	private JComponent monthTab;


	private JLabel monthLabel;

	private JTable daySchedule;
	private JTable weekSchedule;
	private JPanel monthSchedule;

	private JTextField startDateTextField;
	private JTextField endDateTextField;
	private JTextArea resultTextField;

	private JButton confirmButton;

	/**
	 * Default constructor that will set all elements in Planner
	 */
	public Planner() {
		// create day tab
		dayTab = new JPanel();

		date = Calendar.getInstance();
		String dayEvents[][] = new String[24][2];
		for(int i = 0; i < 24; i++) {
			dayEvents[i][0] = i + "";
		}
		// string for table
		String dayHeader[] = {"Time", new SimpleDateFormat("dd.MMM.yyyy").format(date.getTime())};
		
		//create day table
		daySchedule = new JTable(dayEvents, dayHeader);
		daySchedule.setDefaultEditor(Object.class, null);
		daySchedule.getColumnModel().getColumn(0).setMaxWidth(40);
		JScrollPane sp=new JScrollPane(daySchedule);
		
		// add to current panels
		this.add("Day", sp);

		// create week tab
		weekTab = new JPanel();
		String weekEvents[][] = new String[24][8];
		for(int i = 0; i < 24; i++) {
			weekEvents[i][0] = i + "";
		}
		// for weekSchedule table
		String weekHeader[] = {"Time", "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
		
		//create table for week tab
		weekSchedule = new JTable(weekEvents, weekHeader);
		weekSchedule.setDefaultEditor(Object.class, null);
		weekSchedule.getColumnModel().getColumn(0).setMaxWidth(40);
		JScrollPane sp1 = new JScrollPane(weekSchedule);
		
		// add it to current panel
		this.add("Week", sp1);

		// create table for mothTab
		monthTab = new JPanel();
		monthLabel = new JLabel( new SimpleDateFormat("MMM.yyyy").format(date.getTime()));
		monthTab.add(monthLabel, BorderLayout.NORTH);
		monthSchedule = new JPanel();
		monthSchedule.setLayout(new GridLayout(0, 7, 10, 1));

		// populate table for month tab
		initializeContainer(date, null);

		// add month tab to current schedule
		monthTab.add(monthSchedule);
		this.add("Month", monthTab);


		//create agenda tab
		JPanel agendaTab = new JPanel();
		agendaTab.setLayout(new BorderLayout());
		JPanel north = new JPanel();
		north.setLayout(new BoxLayout(north, BoxLayout.X_AXIS));
		
		// text field for agenda tab
		JLabel promptTextLabel = new JLabel("Input start date");
		startDateTextField = new JTextField();
		startDateTextField.setText("dd/mm/yyyy");
		startDateTextField.setPreferredSize(new Dimension(150, 50));
		
		// add listener to delete prompted date format
		startDateTextField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				startDateTextField.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {

			}

		});
		north.add(promptTextLabel);
		north.add(startDateTextField);

		// other text field in agenda tab
		JLabel promptEndTextLabel = new JLabel("Input start date");
		endDateTextField = new JTextField();
		endDateTextField.setText("dd/mm/yyyy");
		endDateTextField.setPreferredSize(new Dimension(150, 50));
		endDateTextField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				endDateTextField.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
			}

		});
		north.add(promptEndTextLabel);
		north.add(endDateTextField);

		// confirm button in agenda tab
		confirmButton = new JButton("Confirm");
		north.add(confirmButton);

		// result text filed in agenda tab
		// all results go here
		resultTextField = new JTextArea();
		resultTextField.setEditable(false);
		JScrollPane jpr = new JScrollPane(resultTextField);
		agendaTab.add(jpr);

		agendaTab.add(north, BorderLayout.NORTH);

		this.add("Agenda", agendaTab);
	}

	/**
	 * Gets table that will represent day schedule
	 * @return day schedule table
	 */
	public JTable getDaySchedule() {
		return daySchedule;
	}

	/**
	 * Sets day schedule table with given parameter
	 * @param daySchedule new table that will be day schedule
	 */
	public void setDaySchedule(JTable daySchedule) {
		this.daySchedule = daySchedule;
	}

	/**
	 * Gets table that will represent week schedule
	 * @return week schedule table
	 */
	public JTable getWeekSchedule() {
		return weekSchedule;
	}

	/**
	 * Sets week schedule table with given parameter
	 * @param weekSchedule new week schedule table
	 */
	public void setWeekSchedule(JTable weekSchedule) {
		this.weekSchedule = weekSchedule;
	}

	/**
	 * Gets the label that holds months
	 * @return month label
	 */
	public JLabel getMonthLabel() {
		return monthLabel;
	}

	/**
	 * Sets month label with given parameter
	 * @param monthLabel new month label
	 */
	public void setMonthLabel(JLabel monthLabel) {
		this.monthLabel = monthLabel;
	}

	/**
	 * Gets JPanel that will be table of elements for month schedule
	 * @return month schedule JPanel
	 */
	public JPanel getMonthSchedule() {
		return monthSchedule;
	}

	
	/**
	 * Sets month schedule with given parameter
	 * @param monthSchedule new month schedules
	 */
	public void setMonthSchedule(JPanel monthSchedule) {
		this.monthSchedule = monthSchedule;
	}

	/**
	 * Gets JTextField that will hold start date in agenda tab
	 * @return start date text filed
	 */
	public JTextField getStartDateTextField() {
		return startDateTextField;
	}

	/**
	 * Sets start date text filed in agenda tab with given parameter
	 * @param startDateTextField new start date text field
	 */
	public void setStartDateTextField(JTextField startDateTextField) {
		this.startDateTextField = startDateTextField;
	}

	/**
	 * Gets JTextField that will hold end date in agenda tab
	 * @return text field with end date
	 */
	public JTextField getEndDateTextField() {
		return endDateTextField;
	}

	/**
	 * Sets end date text field with given parameter
	 * @param endDateTextField new end date text field
	 */
	public void setEndDateTextField(JTextField endDateTextField) {
		this.endDateTextField = endDateTextField;
	}

	/**
	 * Gets confirm button from agenda tab
	 * @return requested button
	 */
	public JButton getConfirmButton() {
		return confirmButton;
	}

	/**
	 * sets confirm button in agenda tab with given parameter
	 * @param confirmButton new confirm button
	 */
	public void setConfirmButton(JButton confirmButton) {
		this.confirmButton = confirmButton;
	}

	/**
	 * Gets text area that will store results in agenda tab
	 * @return requested text area
	 */
	public JTextArea getResultTextField() {
		return resultTextField;
	}

	/**
	 * Sets text area in agenda tab with given parameter
	 * @param resultTextField new text area for agenda tab
	 */
	public void setResultTextField(JTextArea resultTextField) {
		this.resultTextField = resultTextField;
	}

	/**
	 * This method will initialize one calendar for month tab
	 * @param month month that will be in table
	 * @param year year that will be in table
	 * @return constructed table with given month in given year
	 */
	JTable initializeCalendar(int month, int year) {
		Calendar date = Calendar.getInstance();

		date.set(Calendar.MONTH, month);
		date.set(Calendar.YEAR, year);

		String dates[][] = new String[6][7];
		String days[] = {"S","M","T","W","T","F","S"};
		JTable calendarTable = new JTable(dates, days);

		int firstDayOfWeek = date.getFirstDayOfWeek();
		date.set(Calendar.DATE, date.getMinimum(Calendar.DATE));

		// Now display the dates, one week per line

		StringBuilder week = new StringBuilder();			
		int i = 0;

		while (month==date.get(Calendar.MONTH)) {
			int j = 0;

			// Display date

			week.append(String.format("%d ", date.get(Calendar.DATE)));

			// Increment date

			date.add(Calendar.DATE, 1);

			// Check if week needs to be printed

			if (date.get(Calendar.MONTH)!=month) {

				// end of month
				// just need to output the month

				String weekDays[] = week.toString().split(" ");
				for(j = 0;j < weekDays.length; j++) {
					dates[i][j] = weekDays[j];
				}

				i++;

				week.setLength(0);

			} else if (date.get(Calendar.DAY_OF_WEEK)==firstDayOfWeek) {

				// new week so print out the current week
				// first check if any padding needed

				int padding = 14 - week.length();

				String weekDays[] = new String[7];

				while(padding > 0) {
					weekDays[j++] = " ";
					padding -= 2;
				}
				String temp[] = week.toString().split(" ");
				int k = 0;
				while(k < temp.length) {
					weekDays[j++] = temp[k++];
				}

				for(j = 0;j < weekDays.length; j++) {
					dates[i][j] = weekDays[j];
				}

				i++;

				week.setLength(0);
			}
		}


		for(i = 0; i < dates.length; i++) {
			for(int j = 0; j < dates[i].length; j++) {
				if(dates[i][j] != null) {
					calendarTable.getModel().setValueAt(dates[i][j], i, j);
				}else {
					calendarTable.getModel().setValueAt(" ", i, j);
				}
			}
		}

		return calendarTable;
	}

	/**
	 * This method will initialize month tab with given parameters
	 * @param date date to be shown in month tab
	 * @param events array list of events that will be shown in this tab
	 */
	public void initializeContainer(Calendar date, ArrayList<Event> events) {
		monthSchedule.removeAll();
		int firstDayOfWeek = date.getFirstDayOfWeek();
		int month = date.get(Calendar.MONTH);
		date.set(Calendar.DATE, date.getMinimum(Calendar.DATE));
		monthLabel.setText(new SimpleDateFormat("MMM.yyyy").format(date.getTime()));

		ArrayList<Event> reduced = new ArrayList<>();
		if(events != null) {
			for(Event e : events) {
				if(e.getYear() == date.get(Calendar.YEAR) && (e.getMonth() - 1) == date.get(Calendar.MONTH)) {
					reduced.add(e);
				}
			}
		}

		StringBuilder week = new StringBuilder();

		ArrayList<JTextArea> textAreas = new ArrayList<>();

		while (month==date.get(Calendar.MONTH)) {

			int j = 0;
			week.append(String.format("%d ", date.get(Calendar.DATE)));  
			JTextArea textArea = new JTextArea(4, 9);
			textArea.setEditable(false);
			textArea.setText("");
			for(Event e : reduced) {
				if(e.getDay() == date.get(Calendar.DAY_OF_MONTH)) {
					textArea.append(e.getTime() + "h - " + e.getName() + "\n");
				}
			}
			date.add(Calendar.DATE, 1);


			if (date.get(Calendar.MONTH)!=month) {

				textAreas.add(textArea);
				String weekDays[] = week.toString().split(" ");
				for(j = 0;j < weekDays.length; j++) {
					JPanel monthDay = new JPanel();
					monthDay.setLayout(new BorderLayout());
					JLabel day = new JLabel(weekDays[j]);
					monthDay.add(day, BorderLayout.NORTH);
					monthDay.add(textAreas.get(j));

					monthSchedule.add(monthDay);
				}
				textAreas = new ArrayList<JTextArea>();
				continue;
			} else if(date.get(Calendar.DAY_OF_WEEK) == firstDayOfWeek){
				textAreas.add(textArea);
				int padding = 14 - week.length();

				String weekDays[] = new String[7];

				while(padding > 0) {
					JTextArea ta = new JTextArea(4, 9);
					ta.setEditable(false);
					ta.setText("");
					textAreas.add(0, ta);
					weekDays[j++] = " ";
					padding -= 2;
				}
				String temp[] = week.toString().split(" ");
				int k = 0;
				while(k < temp.length) {
					weekDays[j++] = temp[k++];
				}

				for(j = 0;j < weekDays.length; j++) {
					JPanel monthDay = new JPanel();
					monthDay.setLayout(new BorderLayout());
					JLabel day = new JLabel(weekDays[j]);
					monthDay.add(day, BorderLayout.NORTH);
					monthDay.add(textAreas.get(j));

					monthSchedule.add(monthDay);
				}

				textAreas = new ArrayList<JTextArea>();
				week.setLength(0);
				continue;
			}
			textAreas.add(textArea);
		}
		this.date.add(Calendar.MONTH, -1);
		monthSchedule.updateUI();

	}


	/**
	 * Gets date that is in planner
	 * @return requested date
	 */
	public Calendar getDate() {
		return date;
	}

	/**
	 * Sets date with given parameter
	 * @param date new date that will be in planner
	 */
	public void setDate(Calendar date) {
		this.date = date;
	}

}
