package sample.nodes;

/**
 * Each NodeBox *SHOULD* implements this interface
 * since we tract the method 'execute' to run the
 * algorithm.
 * @author Daniel.
 */
public interface Algorithm {

    /**
     * Execute the algorithm when the lines are connected
     * or the input changes
     */
    void execute();
}
