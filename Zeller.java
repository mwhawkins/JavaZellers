/**
 * Zeller's Congruence and a printable calendar
 * File: Zeller.java
 * @author Michael Hawkins
 **/
 
import java.util.Scanner;

public class Zeller{
    //These are the immutable (unchangeable) arrays to hold days of the week and months of the year for use in our calendar
    private static final String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    private static final String[] monthsOfYear =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    /**
     * Prints the calendar header showing days of the week
     * @return String
     */
    public String getCalendarHeader(){
        //Initialize a string object to hold our result
        String result = new String();
        //Loop over the days of the week
        for (int i=0;i<=6;i++){
            //Print the days of week at top
            if(i == 6){
                //if we are at the last day (saturday) we stop - no need to add a tab space
                result += daysOfWeek[i];
            }else{
                //loop over the days and add the correct tab spacing
                result += daysOfWeek[i] + "\t\t";
            }
        }
        //return the formatted string result
        return result;
    }
    /**
     *Calculates if the current year is a leap year
     * We make this static so it does not have to be initialized in a class object before being called to calculate
     * @param year int
     *@return boolean
     */
    public static boolean isLeapYear(int year){
        //If year is divisible by 4 and not divisible by 100, or if year is divisible by 400 then it is a leap year
        if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
            //This is a leap year, return true
            return true;
        } else {
            //This is NOT a leap year, return false
            return false;
        }
    }

    /**
     *Calculates the days in each month of the given year
     *Taking into account leap years
     * We make this static so it does not have to be initialized in a class object before being called to calculate
     * @param year int
     * @return int[]
     */
    public static int[] getDaysInMonths(int year){
        //Initialize the basic array for days in each month
        int[] days = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        //If this is a leap year, change the number of days in February
        if (isLeapYear(year)){
            //February is in array index position 1, so we change the days for that value
            days[1] = 29;
        }
        //Return the result to the caller
        return days;
    }

    /**
     * Prints the days and weeks of the month
     * @param totalDaysInMonth int
     * @param year int
     * @param month int
     * @return void
     */
    public void printCalendar(int totalDaysInMonth, int year, int month){
        //print the month and year input by the user
        System.out.println(monthsOfYear[month-1] + " " + Integer.toString(year));
        //print the calendar header calling our method above
        System.out.println(getCalendarHeader());
        //initialize our day of week and day of month variables
        int currentDayOfWeek = 0;
        int day = 1;
        //We loop over the weeks here
        while(currentDayOfWeek<=6){
            //if this is the first day, we calculate the offset
            if (day==1){
                //This is day 1, so we get the first day of month
                currentDayOfWeek = getFirstDayOfMonth(year, month);
                //Loop to generate our offset in the calendar
                for(int j=1;j<=currentDayOfWeek;j++){
                    System.out.print("\t\t");
                }
            }
            //if this is Saturday, we add a newline instead of tab space to the end
            if(currentDayOfWeek==6){
                System.out.print(Integer.toString(day) + "\n");
                currentDayOfWeek = 0;
            }else{
                //print the day number and spacing
                System.out.print(Integer.toString(day) + "\t\t");
                currentDayOfWeek++;
            }
            //check to make sure we didn't exceed our days for the month
            if(day<totalDaysInMonth){
                day++;
            }else{
                //if we are at the last day, we exit the loop - our calendar is done!
                break;
            }
        }
    }

    /**
     * Calculates the first day of the input month using Zeller's Congruence
     * @param year
     * @param month
     * @return int
     */
    public int getFirstDayOfMonth(int year, int month){
        //Convert Jan to 13 and Feb to 14 (of the previous year)
        if (month < 3) {
            month += 12;
            year -= 1;
        }
        //calculate the century and year of century
        int century = year/100;
        int yearOfCentury = year % 100;

        //Formula for Zeller's Congruence in Java code
        int result = (((26*(month+1)/10)+yearOfCentury+(yearOfCentury/4)+(century/4)+(5*century)) % 7);

        //Return our result to caller
        return result;
    }

    /**
     * The main entry method of the program
     * @param args (command line args - not used here)
     */
    public static void main(String[] args) {
        //Initialize a Scanner to collect input
        Scanner input = new Scanner(System.in);

        /*-----BEGIN USER INPUT-----*/
        System.out.print("Enter the year: ");
        int year = input.nextInt();

        System.out.print("Enter Month (1=Jan, 2=Feb, etc.): ");
        int month = input.nextInt();

        System.out.print("Enter Number of Days in Month or Enter 0 to Automatically Calculate: ");
        int days = input.nextInt();
        /*-----END USER INPUT-----*/

        //If days input was 0, we automatically calculate for the user
        if (days == 0) days = getDaysInMonths(year)[month-1]; //change to month-1 to get the array index
        //Initialize the class object
        Zeller z = new Zeller();
        //Get to work printing our calendar
        z.printCalendar(days, year, month);
    }
}
