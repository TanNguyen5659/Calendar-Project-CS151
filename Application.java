import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * This class will be main window, it holds CalendarView as well as Planner and one more button
 *
 */
public class Application extends JFrame{

	JButton fromFileButton;

	ArrayList<Event> events;
	private EventEditor eventEditor = new EventEditor();

	/**
	 * Default constructor that will create and initialize all fields
	 */
	public Application() {
		// set name for JFrame
		super("Planner");
		
		// hold events
		events = new ArrayList<Event>();
		
		//left part of JFrame
		CalendarView left = new CalendarView();

		Planner planner = new Planner();

		// calendar on left side
		JTable calendarTable = left.getCalendarTable();
		
		// add listener
		calendarTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

				// take selected date
				int row = calendarTable.getSelectedRow();
				int col = calendarTable.getSelectedColumn();

				try {
					int dayNum = Integer.parseInt((String) calendarTable.getValueAt(row, col));
					Calendar date = left.getDate();

					int oldMonth = planner.getDate().get(Calendar.MONTH);
					int oldYear = planner.getDate().get(Calendar.YEAR);
					int oldWeek = planner.getDate().get(Calendar.WEEK_OF_YEAR);
					date.set(Calendar.DAY_OF_MONTH, dayNum);
					int newMonth = date.get(Calendar.MONTH);
					int newYear = date.get(Calendar.YEAR);
					int newWeek = date.get(Calendar.WEEK_OF_YEAR);

					// update day tab
					JTable daySchedule = planner.getDaySchedule();

					JTableHeader header= daySchedule.getTableHeader();
					TableColumnModel colMod = header.getColumnModel();
					TableColumn tabCol = colMod.getColumn(1);
					tabCol.setHeaderValue(new SimpleDateFormat("dd. MMM yyyy").format(date.getTime()));
					updateDaySchedule(date, daySchedule);
					header.repaint();

					date.set(Calendar.DAY_OF_MONTH, dayNum);

					// update week tab
					if(oldWeek != newWeek) {
						JTable weekSchedule = planner.getWeekSchedule();
						updateWeekSchedule(date, weekSchedule);
					}

					// update month tab
					if(oldMonth != newMonth) {
						JPanel monthSchedule = planner.getMonthSchedule();
						planner.initializeContainer((Calendar)date.clone(), events);
						monthSchedule.repaint();
					}

					planner.getDate().set(newYear, newMonth, date.get(Calendar.DAY_OF_MONTH));
				}catch(NumberFormatException ex) {
					// show error window 
					JOptionPane.showMessageDialog(null, "Selected date is empty. Please select another one.");
				}
			}
		});

		JButton todayButton = left.getTodayButton();
		todayButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// set date for CalendarView
				left.setDate(Calendar.getInstance());
				// update CalendarView label
				left.updateLabel();
				
				// update calendar
				left.updateCalendar();
				
				//update month tab
				planner.initializeContainer(Calendar.getInstance(), events);
				
				//update week tab
				updateWeekSchedule(Calendar.getInstance(), planner.getWeekSchedule());
			}

		});

		// add listener to create button
		JButton createButton = left.getCreateButton();
		createButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// get result from event editor
				int result = JOptionPane.showConfirmDialog(null, eventEditor,
						"Create Event", JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.PLAIN_MESSAGE);


				// if it is confirmed 
				if (result == JOptionPane.OK_OPTION) {
					// create new event
					String name = eventEditor.getFieldText(EventEditor.FieldTitle.NAME);
					int year = 0;
					try {
						year = Integer.parseInt(eventEditor.getFieldText(EventEditor.FieldTitle.YEAR));
						if(year < 1000 || year > 10_000) {
							JOptionPane.showMessageDialog(null, "Invalid year.");
							return;
						}
					}catch(NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Invalid year.");
						return;
					}
					int month = 0;
					try {
						month = Integer.parseInt(eventEditor.getFieldText(EventEditor.FieldTitle.MONTH));
						if(month < 1 || month > 12) {
							JOptionPane.showMessageDialog(null, "Invalid month.");
							return;
						}
					}catch(NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Invalid month.");
						return;
					}

					int day = 0;
					try {
						day = Integer.parseInt(eventEditor.getFieldText(EventEditor.FieldTitle.DAY));
						if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
							if(day < 1 || day > 31) {
								JOptionPane.showMessageDialog(null, "Invalid starting time.");
								return;
							}
						}else if(month == 4 || month == 6 || month == 9 || month == 11) {
							if(day < 1 || day > 30) {
								JOptionPane.showMessageDialog(null, "Invalid starting time.");
								return;
							}
						}else {
							if(day < 1 || day > 29) {
								JOptionPane.showMessageDialog(null, "Invalid starting time.");
								return;
							}
						}
					}catch(NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Invalid date.");
						return;
					}

					int time = 0;
					try {
						time = Integer.parseInt(eventEditor.getFieldText(EventEditor.FieldTitle.TIME));
						if(time < 0 || time > 23) {
							JOptionPane.showMessageDialog(null, "Invalid time.");
							return;
						}
					}catch(NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "Invalid time.");
						return;
					}

					Event event = new Event(name, year, month, day, time);
					events.add(event);
				}
			}

		});

		this.add(left, BorderLayout.WEST);
		this.add(planner);

		//setSize(1000, 600);
		

		JPanel west = new JPanel();
		// create and add listener for from file button
		fromFileButton = new JButton("From file");
		fromFileButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// display file chooser to choose file
				JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

				int returnValue = jfc.showOpenDialog(null);

				// if file is selected
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = jfc.getSelectedFile();
					// open selected file
					try(BufferedReader reader = new BufferedReader(new FileReader(selectedFile))){
						String line;
						// parse file line by line
						while((line = reader.readLine()) != null) {
							String data[] = line.split(";");
							if(data.length < 7) {
								JOptionPane.showMessageDialog(null, "Data in file is not in correct format.");
								return;
							}
							try {
								String eventName = data[0];
								int year = Integer.parseInt(data[1]);
								if(year < 1900 || year > 10000) {
									JOptionPane.showMessageDialog(null, "Data in file is not in correct format.");
									return;
								}
								int startingMonth = Integer.parseInt(data[2]);
								if(startingMonth < 1 || startingMonth > 12) {
									JOptionPane.showMessageDialog(null, "Data in file is not in correct format.");
									return;
								}
								int endingMonth = Integer.parseInt(data[3]);
								if(endingMonth < 1 || endingMonth > 12) {
									JOptionPane.showMessageDialog(null, "Data in file is not in correct format.");
									return;
								}
								String days = data[4];
								if(!days.matches("[SMTWHFA]+")) {
									JOptionPane.showMessageDialog(null, "Data in file is not in correct format.");
									return;
								}

								int startingTime = Integer.parseInt(data[5]);
								if(startingTime < 0 || startingTime > 23) {
									JOptionPane.showMessageDialog(null, "Data in file is not in correct format.");
									return;
								}

								int endingTime = Integer.parseInt(data[6]);
								if(endingTime < 0 || endingTime > 23) {
									JOptionPane.showMessageDialog(null, "Data in file is not in correct format.");
									return;
								}


								LocalDate startDate = LocalDate.of(year, startingMonth, 1);

								int endDay = 0;
								if(endingMonth == 1 || endingMonth == 3 || endingMonth == 5 || endingMonth == 7 || endingMonth == 8 || endingMonth == 10 || endingMonth == 12) {
									endDay = 31;
								}else if(endingMonth == 4 || endingMonth == 6 || endingMonth == 9 || endingMonth == 11){
									endDay = 30;
								}else {
									if(isLeap(year)) {
										endDay = 29;
									}else {
										endDay = 28;
									}
								}

								LocalDate endDate = LocalDate.of(year, endingMonth, endDay);
								List<LocalDate> list = getDatesBetween(startDate, endDate);
								for(LocalDate d : list) {
									if(days.contains("S") && d.getDayOfWeek() == DayOfWeek.SUNDAY) {
										for(int i = startingTime; i < endingTime; i++) {
											events.add(new Event(eventName, d.getYear(), d.getMonthValue(), d.getDayOfMonth(),i));
										}
									}else if(days.contains("M") && d.getDayOfWeek() == DayOfWeek.MONDAY) {
										for(int i = startingTime; i < endingTime; i++) {
											events.add(new Event(eventName, d.getYear(), d.getMonthValue(), d.getDayOfMonth(),i));
										}
									}else if(days.contains("T") && d.getDayOfWeek() == DayOfWeek.TUESDAY) {
										for(int i = startingTime; i < endingTime; i++) {
											events.add(new Event(eventName, d.getYear(), d.getMonthValue(), d.getDayOfMonth(),i));
										}
									}else if(days.contains("W") && d.getDayOfWeek() == DayOfWeek.WEDNESDAY) {
										for(int i = startingTime; i < endingTime; i++) {
											events.add(new Event(eventName, d.getYear(), d.getMonthValue(), d.getDayOfMonth(),i));
										}
									}else if(days.contains("H") && d.getDayOfWeek() == DayOfWeek.THURSDAY) {
										for(int i = startingTime; i < endingTime; i++) {
											events.add(new Event(eventName, d.getYear(), d.getMonthValue(), d.getDayOfMonth(),i));
										}
									}else if(days.contains("F") && d.getDayOfWeek() == DayOfWeek.FRIDAY) {
										for(int i = startingTime; i < endingTime; i++) {
											events.add(new Event(eventName, d.getYear(), d.getMonthValue(), d.getDayOfMonth(),i));
										}
									}else if(days.contains("A") && d.getDayOfWeek() == DayOfWeek.SATURDAY) {
										for(int i = startingTime; i < endingTime; i++) {
											events.add(new Event(eventName, d.getYear(), d.getMonthValue(), d.getDayOfMonth(),i));
										}
									}
								}
							}catch(NumberFormatException ex) {
								JOptionPane.showMessageDialog(null, "Data in file is not in correct format.");
								return;
							}
						}
					} catch (FileNotFoundException ex) {
						ex.printStackTrace();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}

		});

		// add listener for confirm button in agenda tab
		JButton confirmButton = planner.getConfirmButton();
		confirmButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				planner.getResultTextField().setText("");
				// take results from text fields 
				String startDateAsString = planner.getStartDateTextField().getText();
				String endDateAsString = planner.getEndDateTextField().getText();

				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
				try {
					// parse text into date
					Date startDate = formatter.parse(startDateAsString);
					Date endDate = formatter.parse(endDateAsString);

					String s = "";

					
					//print all events in given period
					for(Event ev : events) {
						String eventDateAsString = ev.getDay() + "/" + ev.getMonth() + "/" + ev.getYear();
						Date eventDate = formatter.parse(eventDateAsString);
						if(eventDate.after(startDate) && eventDate.before(endDate)) {
							s += planner.getResultTextField().getText();
							s += "\n" + ev;
						}
					}
					planner.getResultTextField().setText(s);
				} catch (ParseException e1) {
					JOptionPane.showMessageDialog(null, "Invalid date format.\nDate should be like 01/01/2020");
					return;
				}

			}

		});

		west.add(fromFileButton, BorderLayout.NORTH);
		this.add(west, BorderLayout.EAST);
	}

	/**
	 * This method will create and return all dates in given period between startDate and endDate
	 * @param startDate date when we start to putting elements in a list
	 * @param endDate date when we end with putting elements in a list
	 * @return created List with dates betwwen startDate and endDate
	 */
	public static List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {	  
		return startDate.datesUntil(endDate.plusDays(1)).collect(Collectors.toList());
	}

	/**
	 * This method will check if given year as parameter is leap year or not
	 * @param year year to be checked is leap or not
	 * @return true if year is leap or false otherwise
	 */
	private boolean isLeap(int year) {
		if ((year % 4 == 0) && year % 100 != 0)
		{
			return true;
		}
		else if ((year % 4 == 0) && (year % 100 == 0) && (year % 400 == 0))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * This method will update day tab
	 * @param date new date that will be shown in day tab
	 * @param daySchedule table that holds elements for day tab
	 */
	private void updateDaySchedule(Calendar date, JTable daySchedule) {
		var model = daySchedule.getModel();

		for(int i = 0; i < 24; i++) {
			model.setValueAt("", i, 1);
		}

		// find event with given day and update table
		for(Event e : events) {
			if(e.getYear() == date.get(Calendar.YEAR) && (e.getMonth() - 1) == date.get(Calendar.MONTH) && e.getDay() == date.get(Calendar.DAY_OF_MONTH)) {
				int time = e.getTime();
				model.setValueAt(e.getName(), time, 1);
			}
		}

	}

	/**
	 * This method will update week tab
	 * @param date new date to be shown in week tab
	 * @param weekSchedule table that holds elements for week tab
	 */
	private void updateWeekSchedule(Calendar date, JTable weekSchedule) {
		var model = weekSchedule.getModel();
		// delete all previos elements
		for(int i = 0; i < 24; i++) {
			for(int j = 1; j < 8; j++) {
				model.setValueAt("", i, j);
			}
		}
		// find all events in given week and update table
		for(Event e : events) {
			Calendar eCal = Calendar.getInstance();
			eCal.set(e.getYear(), e.getMonth()-1, e.getDay());
			int eWeek = eCal.get(Calendar.WEEK_OF_YEAR);
			int dWeek = date.get(Calendar.WEEK_OF_YEAR);
			if(eWeek == dWeek) {
				int day = (eCal.get(Calendar.DAY_OF_WEEK) - 1) == 0 ? 7 : (eCal.get(Calendar.DAY_OF_WEEK) - 1);
				model.setValueAt(e.getName(), e.getTime(), day);
			}
		}
	}
}

