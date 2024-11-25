package gameOld.resources;

public abstract class Resource {
    private double quantity;
    private double ratePerSecond = 1;

    public void update(float dt) {
        quantity += ratePerSecond * dt;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + ratePerSecond + "/s): " + quantity;
    }

    public double getRatePerSecond() {
        return ratePerSecond;
    }

    public void setRatePerSecond(double ratePerSecond) {
        this.ratePerSecond = Math.round(ratePerSecond * 1000.0) / 1000.0;
    }
}
