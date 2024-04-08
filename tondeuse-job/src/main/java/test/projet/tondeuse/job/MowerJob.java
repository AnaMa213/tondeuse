package test.projet.tondeuse.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.PassThroughFieldSetMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.WritableResource;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.PlatformTransactionManager;
import test.projet.tondeuse.job.handler.LawnSizeHandler;
import test.projet.tondeuse.job.model.Mower;
import test.projet.tondeuse.job.processor.ActivateMowerProcessor;
import test.projet.tondeuse.job.reader.MultiLineMowerReader;

@Configuration
@EnableRetry
public class MowerJob {

    @Value("${file.input}")
    private Resource inputTxt;

    @Value("${file.output}")
    private Resource outputTxt;

    @Bean(name = "activateJob")
    public Job activateJob(JobRepository jobRepository, @Qualifier("readAndOrderMowerStep") Step readAndOrderMowerStep) {
        return new JobBuilder("startMower", jobRepository).incrementer(new RunIdIncrementer())
                //.listener(listener)
                //
                .preventRestart()
                .start(readAndOrderMowerStep).build();
    }

    @Bean
    protected Step readAndOrderMowerStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                         ItemProcessor<Mower, Mower> processor, FlatFileItemWriter<Mower> writer, LawnSizeHandler lawnSizeHandler) {
        return new StepBuilder("readAndOrderMowerStep", jobRepository).<Mower, Mower>chunk(2, transactionManager)
                .reader(itemReader(lawnSizeHandler)).processor(processor).writer(writer).listener(lawnSizeHandler).build();
    }

    @Bean
    @StepScope
    public MultiLineMowerReader itemReader(LawnSizeHandler lawnSizeHandler) {
        DelimitedLineTokenizer delimiter = new DelimitedLineTokenizer();
        delimiter.setDelimiter(" ");
        FlatFileItemReader<FieldSet> delegate = new FlatFileItemReaderBuilder<FieldSet>().name("delegateItemReader")
                .resource(this.inputTxt)
                .linesToSkip(1)
                .skippedLinesCallback(lawnSizeHandler)
                .lineTokenizer(delimiter)
                .fieldSetMapper(new PassThroughFieldSetMapper())
                .build();
        MultiLineMowerReader reader = new MultiLineMowerReader();
        reader.setDelegate(delegate);
        return reader;
    }


    @Bean
    @StepScope
    public FlatFileItemWriter<Mower> itemWriter() {
        FlatFileItemWriter<Mower> writer = new FlatFileItemWriter<>();
        writer.setResource((WritableResource) this.outputTxt);
        //All job repetitions should recreate a nex outputFile
        writer.setAppendAllowed(false);
        DelimitedLineAggregator<Mower> aggregator = new DelimitedLineAggregator<>();
        aggregator.setDelimiter(" ");
        BeanWrapperFieldExtractor<Mower> beanWrapper = new BeanWrapperFieldExtractor<>();

        //Name field values sequence based on object properties
        beanWrapper.setNames(new String[]{"id", "posX", "posY", "orientation"});
        aggregator.setFieldExtractor(beanWrapper);
        writer.setLineAggregator(aggregator);
        return writer;
    }


    @Bean
    @StepScope
    public LawnSizeHandler pelouseSizeHandler() {
        return new LawnSizeHandler();
    }

    @Bean
    public ItemProcessor<Mower, Mower> itemProcessor() {
        return new ActivateMowerProcessor();
    }

}


