package test.projet.tondeuse.job.reader;

import jakarta.annotation.Nullable;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.transform.FieldSet;
import test.projet.tondeuse.job.model.Tondeuse;

import java.util.Arrays;
import java.util.List;

public class MultiLineTondeuseReader implements ItemReader<Tondeuse>, ItemStream {
    private FlatFileItemReader<FieldSet> delegate;

    /**
     * @see org.springframework.batch.item.ItemReader#read()
     */
    @Nullable
    @Override
    public Tondeuse read() throws Exception {
        Tondeuse t = null;
        int numberLine = 1;

        for (FieldSet line; (line = this.delegate.read()) != null; ) {

            switch (numberLine) {
                case 1 -> {
                    t = new Tondeuse(); // Record must start 1st line
                    t.setPosX(line.readInt(0));
                    t.setPosY(line.readInt(1));
                    t.setOrientation(line.readString(2));
                    numberLine++;
                }
                case 2 -> {
                    List<String> ordres = Arrays.stream(line.readString(0).split("")).toList();
                    t.setOrders(ordres);
                    return t;
                }
            }
        }
        return null;
    }

    public void setDelegate(FlatFileItemReader<FieldSet> delegate) {
        this.delegate = delegate;
    }

    @Override
    public void close() throws ItemStreamException {
        this.delegate.close();
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        this.delegate.open(executionContext);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        this.delegate.update(executionContext);
    }
}
