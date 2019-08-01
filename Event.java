
/**
 * This class will represent one event.
 * It will store all that is requred for event like name, date and time
 *
 */
public class Event {
	private String name;
	private int year;
	private int month;
	private int day;
	private int time;
	
	/**
	 * Default constructor that will initialize all fields
	 * @param name name for event
	 * @param year year of event
	 * @param month month of event
	 * @param day day in month of event
	 * @param time time of event in hours
	 */
	public Event(String name, int year, int month, int day, int time) {
		super();
		this.name = name;
		this.year = year;
		this.month = month;
		this.day = day;
		this.time = time;
	}

	/**
	 * Getter for name
	 * @return returns name of event
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for name
	 * @param name new name for event
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for year
	 * @return year of event
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Setter for year
	 * @param year new year for event
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * Getter for month
	 * @return month of event
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * Setter for month
	 * @param month new month for event
	 */
	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * Getter for day
	 * @return day in month for event
	 */
	public int getDay() {
		return day;
	}

	/**
	 * Setter for day
	 * @param day new day for event
	 */
	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * Getter for time
	 * @return time of event
	 */
	public int getTime() {
		return time;
	}

	/**
	 * Setter for time
	 * @param time new time for event
	 */
	public void setTime(int time) {
		this.time = time;
	}
	
	/**
	 * Override to string method so we can print event
	 */
	public String toString() {
		return name + " - " + day + ". " + month + ". " + year + ". " + time + "h";
	}
	
	
}
