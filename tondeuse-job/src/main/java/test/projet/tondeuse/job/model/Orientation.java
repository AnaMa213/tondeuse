package test.projet.tondeuse.job.model;

public enum Orientation {
    N("N"),
    E("E"),
    W("W"),
    S("S");

    public final String initial;

    Orientation(String initial) {
        this.initial = initial;
    }
}
