import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * A class that produces left side of window. Controls buttons and calendar.
 *
 */
public class CalendarView extends JPanel{
	private Calendar date;

	private JButton todayButton;
	private JButton leftButton;
	private JButton rightButton;

	private JLabel dateAndYearLabel;
	private JButton createButton;

	private JTable calendarTable;

	/**
	 * Constructor of CalendarView that sets all elements that will be on this side of window
	 */
	public CalendarView() {
		date = Calendar.getInstance();

		todayButton = new JButton("Today");


		leftButton = new JButton("<");
		leftButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				decrementMonth();
				updateLabel();
				updateCalendar();
			}

		});

		rightButton = new JButton(">");

		rightButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				incrementMonth();
				updateLabel();
				updateCalendar();
			}

		});

		JPanel todayLeftRight = new JPanel();
		todayLeftRight.add(todayButton);
		todayLeftRight.add(leftButton);
		todayLeftRight.add(rightButton);

		createButton = new JButton("Create");
		createButton.setBackground(Color.RED);

		dateAndYearLabel = new JLabel();
		dateAndYearLabel.setText(new SimpleDateFormat("MMM-YYYY").format(date.getTime()));

		String dates[][] = new String[6][7];
		String days[] = {"S","M","T","W","T","F","S"};
		calendarTable = new JTable(dates, days);
		calendarTable.setDefaultEditor(Object.class, null);
		calendarTable.getTableHeader().setReorderingAllowed(false);

		JScrollPane sp=new JScrollPane(calendarTable);
		sp.setPreferredSize(new Dimension(120,120));

		JPanel createAndTable = new JPanel();
		createAndTable.setLayout(new BoxLayout(createAndTable, BoxLayout.Y_AXIS));

		createAndTable.add(todayLeftRight);
		createAndTable.add(createButton);
		createAndTable.add(dateAndYearLabel);
		createAndTable.add(sp);

		this.add(createAndTable, BorderLayout.SOUTH);
		updateCalendar();
	}

	/**
	 * Getter for TodayButton
	 * @return todayButton button that is todayButton
	 */
	public JButton getTodayButton() {
		return todayButton;
	}

	/**
	 * Sets Today Button with given parameter
	 * @param todayButton Button that will be today button
	 */
	public void setTodayButton(JButton todayButton) {
		this.todayButton = todayButton;
	}

	/**
	 * Getter for leftButton
	 * @return Button that is leftButton
	 */
	public JButton getLeftButton() {
		return leftButton;
	}

	/**
	 * Sets leftButton with given parameter
	 * @param leftButton button that will be leftButton
	 */
	public void setLeftButton(JButton leftButton) {
		this.leftButton = leftButton;
	}

	/**
	 * Getter for rightButton
	 * @return button that is rightButton
	 */
	public JButton getRightButton() {
		return rightButton;
	}

	/**
	 * Sets rightButton with given parameter
	 * @param rightButton button that will be rightButton
	 */
	public void setRightButton(JButton rightButton) {
		this.rightButton = rightButton;
	}

	/**
	 * Getter for label that holds month and year
	 * @return date and year label
	 */
	public JLabel getDateAndYearLabel() {
		return dateAndYearLabel;
	}

	/**
	 * Sets label to the parameter label
	 * @param dateAndYearLabel new label that will be date and year
	 */
	public void setDateAndYearLabel(JLabel dateAndYearLabel) {
		this.dateAndYearLabel = dateAndYearLabel;
	}

	/**
	 * Gets date of calendar
	 * @return instance of Calendar that represents time in calendar
	 */
	public Calendar getDate() {
		return date;
	}

	/**
	 * Sets date with given parameter
	 * @param date new date that will now be on calendar
	 */
	public void setDate(Calendar date) {
		this.date = date;
	}

	/**
	 * Gets table with calendar
	 * @return calendar table
	 */
	public JTable getCalendarTable() {
		return calendarTable;
	}

	/**
	 * Sets calendar with given parameter
	 * @param calendarTable table that will be new calendar
	 */
	public void setCalendarTable(JTable calendarTable) {
		this.calendarTable = calendarTable;
	}

	/**
	 * Gets the create button
	 * @return create button 
	 */
	public JButton getCreateButton() {
		return createButton;
	}

	/**
	 * Sets create button with given parameter
	 * @param createButton new create button
	 */
	public void setCreateButton(JButton createButton) {
		this.createButton = createButton;
	}

	/**
	 * This method will update calendar based on date attribute
	 */
	public void updateCalendar() {
		int month = date.get(Calendar.MONTH);
		int firstDayOfWeek = date.getFirstDayOfWeek();
		date.set(Calendar.DATE, date.getMinimum(Calendar.DATE));

		// Now display the dates, one week per line

		StringBuilder week = new StringBuilder();

		String days[] = {"S","M","T","W","T","F","S"};
		String dates[][] = new String[6][7];

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
		decrementMonth();
	}

	/**
	 * Sets date and year label to the date in the date attribute in format MMM-YYYY
	 */
	public void updateLabel() {
		dateAndYearLabel.setText(new SimpleDateFormat("MMM-YYYY").format(date.getTime()));
	}

	/**
	 * Increase day in calendar by one
	 */
	public void incrementDay() {
		date.add(Calendar.DATE, 1);
	}

	
	/**
	 * Increase month in calendar by one
	 */
	public void incrementMonth() {
		date.add(Calendar.MONTH, 1);
	}

	/**
	 * Decrease month in calendar by one
	 */
	public void decrementMonth() {
		date.add(Calendar.MONTH, -1);
	}

	/**
	 * Increase year in calendar by one
	 */
	public void incrementYear() {
		date.add(Calendar.YEAR, 1);
	}

}
