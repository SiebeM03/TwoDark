package old.engine.util;

public class Debouncer {
    private double interval;      // Time interval for debouncing, in seconds
    private double timer = 0;     // Accumulated time since last triggered action

    public Debouncer(double interval) {
        this.interval = interval;
    }

    /**
     * Update the timer by adding delta time (dt).
     *
     * @param dt - the time increment, typically from an update loop
     * @return true if the debounce interval has been reached, false otherwise
     */
    public boolean shouldTrigger(float dt) {
        timer += dt;
        if (timer >= interval) {
            timer = 0;  // Reset timer after interval is reached
            return true;
        }
        return false;
    }

    /**
     * Update the timer by adding delta time (dt).
     *
     * @param dt the time increment, typically from an update loop
     * @param reset whether to reset the timer after the interval is reached
     * @return true if the debounce interval has been reached, false otherwise
     */
    public boolean shouldTrigger(float dt, boolean reset) {
        timer += dt;
        if (timer >= interval) {
            if (reset) {
                timer = 0;  // Reset timer after interval is reached
            }
            return true;
        }
        return false;
    }

    /**
     * Resets debounce timer to zero, useful for manual resets if needed.
     */
    public void reset() {
        timer = 0;
    }
}