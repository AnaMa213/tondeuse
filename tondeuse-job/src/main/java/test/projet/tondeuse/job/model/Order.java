package test.projet.tondeuse.job.model;

/**
 * Order that can be used for the mower : <p>
 * {@link #A} (go forward) <p>
 * {@link #D} (go right) <p>
 * {@link #G} (go left) <p>
 * <p>
 *
 * @author Kenan TERRISSE
 * @version 1.0
 */
public enum Order {
    /**
     * go forward.
     */
    A("A"),
    /**
     * go right.
     */
    D("D"),
    /**
     * go left.
     */
    G("G");

    /**
     * initial of order enum.
     */
    public final String initial;

    /**
     * constructor of Order Enum with initial of order.
     *
     * @param initial initial of order.
     */
    Order(String initial) {
        this.initial = initial;
    }
}
