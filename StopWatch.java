/**
 * Timer.java
 * Authors: Lucas Chavarria, Cole Vikupitz, Ron Guo, James Xu
 * -----------------------------------------------------------------------------
 * FIXME
 */
package sudoku;


/* Imports */
import java.util.Timer;
import java.util.TimerTask;

public class StopWatch {

    private int secondsPassed;
    private Timer timer;
    private TimerTask task;


    public StopWatch() {
        this(0);
    }

    public StopWatch(int x) {
        this.timer = new Timer();
        this.task = new TimerTask() {
            @Override
            public void run() {
                secondsPassed++;
            }
        };
        this.secondsPassed = (0 + x);

    }

    /***/
    public void start() {
        this.timer.scheduleAtFixedRate(this.task, 1000, 1000);
    }

    /***/
    public void stop() {
        this.timer.cancel();
    }

    /***/
    public void reset() {
        this.secondsPassed = 0;
    }

    /***/
    public long getSecondsPassed() {
        return this.secondsPassed;
    }

    /***/
    public String convertToString(int time) {
        int sec = (time % 60);
        int min = (time / 60);
        int hrs = ((time / 60) / 60);

        String s;
        if (hrs > 0)
            s = String.format("%d:%02d:%02d", hrs, (min % 60), sec);
        else
            s = String.format("%02d:%02d", min, sec);
        return s;
    }

    /***/
    public String toString() {
        int sec = (this.secondsPassed % 60);
        int min = (this.secondsPassed / 60);
        int hrs = ((this.secondsPassed / 60) / 60);

        String s;
        if (hrs > 0)
            s = String.format("%d:%02d:%02d", hrs, (min % 60), sec);
        else
            s = String.format("%02d:%02d", min, sec);
        return s;
    }

    /////////////////////////////////////////////
    public static void main(String[] args) {
        StopWatch s = new StopWatch(3598);
        s.start();
    }
    ////////////////////////////////////////////

} // End Timer class