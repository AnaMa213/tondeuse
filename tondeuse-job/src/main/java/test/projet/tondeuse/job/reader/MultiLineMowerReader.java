package test.projet.tondeuse.job.reader;

import jakarta.annotation.Nullable;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.transform.FieldSet;
import test.projet.tondeuse.job.model.Mower;
import test.projet.tondeuse.job.model.Order;
import test.projet.tondeuse.job.model.Orientation;

import java.util.Arrays;
import java.util.List;

/**
 * Reader that use multi-line reading to construct the all the mower and pass it to processor .
 * <p>
 * Please see {@link org.springframework.batch.item.ItemReader} and {@link org.springframework.batch.item.ItemStream} to see the implemented class.
 *
 * @author Kenan TERRISSE
 * @version 1.0
 */
@Setter
public class MultiLineMowerReader implements ItemReader<Mower>, ItemStream {

    /**
     * delegated reader.
     */
    private FlatFileItemReader<FieldSet> delegate;

    /**
     * override reader method to read two line in a row and initialize an all mower. <p>
     *
     * @return mower ({@link Mower}) created by the reader.
     * @see org.springframework.batch.item.ItemReader#read()
     */
    @Nullable
    @Override
    public Mower read() throws Exception {
        Mower t = null;
        // init numberLine already read.
        int numberLine = 0;

        for (FieldSet line; (line = this.delegate.read()) != null; ) {

            switch (numberLine) {
                case 0 -> {
                    t = new Mower(); // Record must start 1st line.
                    t.setPosX(line.readInt(0)); // get length on first char.
                    t.setPosY(line.readInt(1)); // get length on second char.
                    t.setOrientation(Orientation.valueOf(line.readString(2))); // get orientation on third char.
                    numberLine++;
                }
                case 1 -> {
                    List<Order> orders = Arrays.stream(line.readString(0).split("")).map(Order::valueOf).toList(); // get orders and process it to list on the second line read.
                    t.setOrders(orders);
                    return t;
                }
                default -> {
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * close method delegated to {@link FlatFileItemReader<FieldSet>}. <p>
     *
     * @see FlatFileItemReader#close()
     */
    @Override
    public void close() throws ItemStreamException {
        this.delegate.close();
    }

    /**
     * open method delegated to {@link FlatFileItemReader<FieldSet>}. <p>
     *
     * @param executionContext execution context of the reader.
     * @see FlatFileItemReader#open(ExecutionContext)
     */
    @Override
    public void open(@NonNull ExecutionContext executionContext) throws ItemStreamException {
        this.delegate.open(executionContext);
    }

    /**
     * update method delegated to {@link FlatFileItemReader<FieldSet>}. <p>
     *
     * @param executionContext execution context of the reader.
     * @see FlatFileItemReader#update(ExecutionContext)
     */
    @Override
    public void update(@NonNull ExecutionContext executionContext) throws ItemStreamException {
        this.delegate.update(executionContext);
    }
}
