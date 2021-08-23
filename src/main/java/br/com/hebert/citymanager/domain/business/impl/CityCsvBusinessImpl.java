package br.com.hebert.citymanager.domain.business.impl;

import br.com.hebert.citymanager.domain.business.CityCsvBusiness;
import br.com.hebert.citymanager.infrastructure.exceptions.BadRequestException;
import br.com.hebert.citymanager.infrastructure.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CityCsvBusinessImpl implements CityCsvBusiness {
    private static final Logger log = LoggerFactory.getLogger(CityCsvBusinessImpl.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    public String importCityCsv() throws BadRequestException {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException
                | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            log.error(e.getMessage());
            throw new BadRequestException(e.getMessage());

        }
        log.info("Batch Process started!!");
        return "City CSV file imported successfully!!";
    }
}
