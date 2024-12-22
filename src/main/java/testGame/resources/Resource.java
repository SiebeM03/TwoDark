package testGame.resources;


public abstract class Resource {
    private String name;
    private float amount;
    private float amountPerClick = 1;

    protected String texturePath;
    protected float clickDelay;

    public void harvest() {
        this.setAmount(this.amount() + this.amountPerClick());
    }

    public String name() {
        return name;
    }

    public float amount() {
        return amount;
    }

    public float amountPerClick() {
        return amountPerClick;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setAmountPerClick(float amountPerClick) {
        this.amountPerClick = amountPerClick;
    }
}
