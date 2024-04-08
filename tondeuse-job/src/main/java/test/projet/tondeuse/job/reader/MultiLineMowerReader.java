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

@Setter
public class MultiLineMowerReader implements ItemReader<Mower>, ItemStream {
    private FlatFileItemReader<FieldSet> delegate;

    /**
     * @see org.springframework.batch.item.ItemReader#read()
     */
    @Nullable
    @Override
    public Mower read() throws Exception {
        Mower t = null;
        int numberLine = 1;

        for (FieldSet line; (line = this.delegate.read()) != null; ) {

            switch (numberLine) {
                case 1 -> {
                    t = new Mower(); // Record must start 1st line
                    t.setPosX(line.readInt(0));
                    t.setPosY(line.readInt(1));
                    t.setOrientation(Orientation.valueOf(line.readString(2)));
                    numberLine++;
                }
                case 2 -> {
                    List<Order> orders = Arrays.stream(line.readString(0).split("")).map(Order::valueOf).toList();
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

    @Override
    public void close() throws ItemStreamException {
        this.delegate.close();
    }

    @Override
    public void open(@NonNull ExecutionContext executionContext) throws ItemStreamException {
        this.delegate.open(executionContext);
    }

    @Override
    public void update(@NonNull ExecutionContext executionContext) throws ItemStreamException {
        this.delegate.update(executionContext);
    }
}
