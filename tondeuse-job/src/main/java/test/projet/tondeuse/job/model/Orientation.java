package test.projet.tondeuse.job.model;

/**
 * Orientation that can be taken by the mower : <p>
 * {@link #N} (north) <p>
 * {@link #E} (east) <p>
 * {@link #W} (west) <p>
 * {@link #S} (south)
 * <p>
 *
 * @author Kenan TERRISSE
 * @version 1.0
 */
public enum Orientation {
    /**
     * north.
     */
    N("N"),
    /**
     * east.
     */
    E("E"),
    /**
     * west.
     */
    W("W"),
    /**
     * south.
     */
    S("S");

    public final String initial;

    /**
     * constructor of Orientation Enum with initial of orientation.
     *
     * @param initial initial of orientation.
     */
    Orientation(String initial) {
        this.initial = initial;
    }
}
