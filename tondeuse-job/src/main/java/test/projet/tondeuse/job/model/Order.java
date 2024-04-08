package test.projet.tondeuse.job.model;

public enum Order {
    A("A"),
    D("D"),
    G("G");

    public final String initial;

    Order(String initial) {
        this.initial = initial;
    }
}
