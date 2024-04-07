package test.projet.tondeuse.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import test.projet.tondeuse.job.handler.PelouseSizeHandler;
import test.projet.tondeuse.job.model.Tondeuse;
import test.projet.tondeuse.job.processor.ActivateTondeuseProcessor;
import test.projet.tondeuse.job.reader.MultiLineTondeuseReader;

@Configuration
@EnableRetry
public class TondeuseJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(TondeuseJob.class);

    @Value("input/tondeuse_instruction.txt")
    private Resource inputTxt;

    @Value("file:output/output.txt")
    private Resource outputTxt;

    @Bean(name = "activateJob")
    public Job activateJob(JobRepository jobRepository, @Qualifier("readAndOrderTondeuseStep") Step readAndOrderTondeuseStep) {
        return new JobBuilder("startTondeuse", jobRepository).incrementer(new RunIdIncrementer())
                //.listener(listener)
                //
                .preventRestart()
                .start(readAndOrderTondeuseStep).build();
    }

    @Bean
    protected Step readAndOrderTondeuseStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                            ItemProcessor<Tondeuse, Tondeuse> processor, FlatFileItemWriter<Tondeuse> writer, PelouseSizeHandler pelouseSizeHandler) {
        return new StepBuilder("readAndOrderTondeuseStep", jobRepository).<Tondeuse, Tondeuse>chunk(2, transactionManager)
                .reader(itemReader(pelouseSizeHandler)).processor(processor).writer(writer).listener(pelouseSizeHandler).build();
    }

    @Bean
    @StepScope
    public MultiLineTondeuseReader itemReader(PelouseSizeHandler pelouseSizeHandler) {
        DelimitedLineTokenizer delimiter = new DelimitedLineTokenizer();
        delimiter.setDelimiter(" ");
        FlatFileItemReader<FieldSet> delegate = new FlatFileItemReaderBuilder<FieldSet>().name("delegateItemReader")
                .resource(this.inputTxt)
                .linesToSkip(1)
                .skippedLinesCallback(pelouseSizeHandler)
                .lineTokenizer(delimiter)
                .fieldSetMapper(new PassThroughFieldSetMapper())
                .build();
        MultiLineTondeuseReader reader = new MultiLineTondeuseReader();
        reader.setDelegate(delegate);
        return reader;
    }


    @Bean
    public FlatFileItemWriter<Tondeuse> itemWriter() {
        FlatFileItemWriter<Tondeuse> writer = new FlatFileItemWriter<>();
        writer.setResource((WritableResource) this.outputTxt);
        //All job repetitions should recreate a nex outputFile
        writer.setAppendAllowed(false);

        //Name field values sequence based on object properties
        writer.setLineAggregator(new DelimitedLineAggregator<>() {
            {
                setDelimiter(" ");
                setFieldExtractor(new BeanWrapperFieldExtractor<>() {
                    {
                        setNames(new String[]{"id", "posX", "posY", "orientation"});
                    }
                });
            }
        });
        return writer;
    }

    @Bean
    @StepScope
    public PelouseSizeHandler pelouseSizeHandler() {
        return new PelouseSizeHandler();
    }

    @Bean
    public ItemProcessor<Tondeuse, Tondeuse> itemProcessor() {
        return new ActivateTondeuseProcessor();
    }

}


