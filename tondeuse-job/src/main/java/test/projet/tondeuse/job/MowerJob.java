package test.projet.tondeuse.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
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
import test.projet.tondeuse.job.listener.MowerListener;
import test.projet.tondeuse.job.model.Mower;
import test.projet.tondeuse.job.processor.ActivateMowerProcessor;
import test.projet.tondeuse.job.reader.MultiLineMowerReader;

/**
 * Mower job configuration. </br>
 * <p>
 * This job is used to initiate different mowers in a lawn, so they can go there final position. </br>
 * <p>
 * A file referring to different mower will be read and, sequentially, a list of orders will be given to the mower to move in the lawn.
 * Then a writer will create an output file to specify the final position and orientation of each mower in the lawn.
 *
 * @author Kenan TERRISSE
 * @version 1.0
 */
@Configuration
@EnableRetry
public class MowerJob {

    /**
     * localisation of the output file.
     */
    @Value("${file.output}")
    private Resource outputTxt;

    /**
     * global job to read the input file and move all different mower.
     *
     * @param jobRepository         job repository of the job
     * @param readAndOrderMowerStep step to read input file, process records and write the final position of all mower.
     * @return the {@link Job} created to get launched at application run.
     */
    @Bean(name = "activateJob")
    public Job activateJob(JobRepository jobRepository, @Qualifier("readAndOrderMowerStep") Step readAndOrderMowerStep, MowerListener listener) {
        return new JobBuilder("startMower", jobRepository)
                .listener(listener)
                .preventRestart()
                .start(readAndOrderMowerStep).build();
    }

    /**
     * read, process and write step to get the mower information and make them move in the lawn.
     *
     * @param jobRepository      job repository of the job.
     * @param transactionManager transaction manager of the job.
     * @param processor          processor ({@link ItemProcessor}) used to process record {@link Mower}.
     * @param writer             writer ({@link FlatFileItemWriter<Mower>}) used to write the record on output file.
     * @param lawnSizeHandler    handler ({@link LawnSizeHandler}) to get the header line (lawn size) of the input file skipped by the reader.
     * @return the {@link Step} created to get used by the Job.
     */
    @Bean
    protected Step readAndOrderMowerStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                         ItemProcessor<Mower, Mower> processor, FlatFileItemWriter<Mower> writer, LawnSizeHandler lawnSizeHandler) {
        return new StepBuilder("readAndOrderMowerStep", jobRepository).<Mower, Mower>chunk(2, transactionManager)
                .reader(itemReader(null, lawnSizeHandler)).processor(processor).writer(writer).listener(lawnSizeHandler).build();
    }

    /**
     * reader initialization.
     *
     * @param lawnSizeHandler handler ({@link LawnSizeHandler}) to get the header line (lawn size) of the input file skipped by the reader.
     * @return {@link MultiLineMowerReader} created to get used by the step.
     */
    @Bean
    @StepScope
    public MultiLineMowerReader itemReader(@Value("#{jobParameters['inputFile']}") final Resource inputFile, LawnSizeHandler lawnSizeHandler) {
        DelimitedLineTokenizer delimiter = new DelimitedLineTokenizer();
        delimiter.setDelimiter(" ");
        FlatFileItemReader<FieldSet> delegate = new FlatFileItemReaderBuilder<FieldSet>().name("delegateItemReader")
                .resource(inputFile)
                .linesToSkip(1)
                .skippedLinesCallback(lawnSizeHandler)
                .lineTokenizer(delimiter)
                .fieldSetMapper(new PassThroughFieldSetMapper())
                .build();
        MultiLineMowerReader reader = new MultiLineMowerReader();
        reader.setDelegate(delegate);
        return reader;
    }


    /**
     * writer initialization.
     *
     * @return {@link FlatFileItemWriter<Mower>} created to get used by the step.
     */
    @Bean
    @StepScope
    public FlatFileItemWriter<Mower> itemWriter(@Value("#{jobParameters['outputfile']}") final Resource outputTxt) {
        FlatFileItemWriter<Mower> writer = new FlatFileItemWriter<>();
        writer.setResource((WritableResource) outputTxt);
        //All job repetitions should recreate a new outputFile
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

    /**
     * handler initialization.
     *
     * @return {@link LawnSizeHandler} created to get used by the step.
     */
    @Bean
    @StepScope
    public LawnSizeHandler lawnSizeHandler() {
        return new LawnSizeHandler();
    }

    /**
     * processor initialization.
     *
     * @return {@link ItemProcessor} created to get used by the step.
     */
    @Bean
    public ItemProcessor<Mower, Mower> itemProcessor() {
        return new ActivateMowerProcessor();
    }

}


