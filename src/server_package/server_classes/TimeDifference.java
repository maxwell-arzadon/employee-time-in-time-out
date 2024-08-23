package server_package.server_classes;

public class TimeDifference {
    public int hours;
    public int minutes;

    /**
     * Constructor for TimeDifference class.
     * @param hours is the number of hours in a certain time.
     * @param minutes is the number of minutes in a certain time.
     */
    public TimeDifference(int hours, int minutes){
        this.hours = hours;
        this.minutes = minutes;
    }

    /**
     * Computes for the difference of two instances in time.
     * @param start is the beginning time.
     * @param end is the ending time.
     * @return the TimeDifference object.
     */
    public static TimeDifference difference(TimeDifference start, TimeDifference end){

        TimeDifference diff = new TimeDifference(0, 0);

        if (start.minutes > end.minutes){
            --end.hours;
            end.minutes += 60;
        }

        diff.minutes = end.minutes - start.minutes;
        diff.hours = end.hours - start.hours;
        return (diff);
    }
}
