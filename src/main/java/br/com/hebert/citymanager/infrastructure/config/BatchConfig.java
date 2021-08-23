package br.com.hebert.citymanager.infrastructure.config;


import br.com.hebert.citymanager.domain.model.City;
import br.com.hebert.citymanager.domain.repository.CityRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;


@Configuration
@EnableBatchProcessing
public class BatchConfig {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Lazy
    public CityRepository cityRepository;

    @Value("${csv.filename}")
    private String csvFileName;

    @Bean
    public LineMapper<City> lineMapper() {
        DefaultLineMapper<City> lineMapper = new DefaultLineMapper<City>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[]{"ibge_id","uf","name","capital","lon","lat","no_accents","alternative_names","microregion","mesoregion"});
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(new br.com.hebert.citymanager.infrastructure.batch.CityFieldSetMapper());
        lineMapper.afterPropertiesSet();

        return lineMapper;
    }

    @Bean
    public FlatFileItemReader<City> cityReader() {
        FlatFileItemReader<City> itemReader = new FlatFileItemReader<City>();
        itemReader.setLineMapper(lineMapper());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(new ClassPathResource(csvFileName));
        return itemReader;
    }

    @Bean
    public RepositoryItemWriter<City> cityWriter() {
        RepositoryItemWriter<City> writer = new RepositoryItemWriter<>();
        writer.setRepository(cityRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public br.com.hebert.citymanager.infrastructure.batch.processor.CityProcessor processor() {
        return new br.com.hebert.citymanager.infrastructure.batch.processor.CityProcessor();
    }


    /**
     * JobBuilderFactory(JobRepository jobRepository)  Convenient factory for a JobBuilder which sets the JobRepository automatically
     */
    @Bean
    public Job readCSVFileJob() {
        return jobBuilderFactory
                .get("readCSVFileJob")
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    /**
     * StepBuilder which sets the JobRepository and PlatformTransactionManager automatically
     */
    @Bean
    public Step step() {
        return stepBuilderFactory
                .get("step")
                .<City, City>chunk(5)
                .reader(cityReader())
                .processor(processor())
                .writer(cityWriter())
                .build();
    }
}
