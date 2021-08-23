package br.com.hebert.citymanager.infrastructure.config;

import br.com.hebert.citymanager.domain.repository.CityRepository;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JobCompletionListener extends JobExecutionListenerSupport {
    private static final Logger log = LoggerFactory.getLogger(JobCompletionListener.class);
    private final CityRepository cityRepository;

    @Autowired
    public JobCompletionListener(CityRepository repository) {
        this.cityRepository = repository;
    }

    // The callback method from the Spring Batch JobExecutionListenerSupport class that is executed when the batch process is completed
    @Override
    public void afterJob(JobExecution jobExecution) {
        // When the batch process is completed the the users in the database are retrieved and logged on the application logs
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("JOB COMPLETED! The results are:");
            cityRepository.findAll()
                    .forEach(city -> log.info("Found City:[" + city + "] in the database") );
        }
    }
}
