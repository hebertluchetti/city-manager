package br.com.hebert.citymanager.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AppConfig {
	@Bean
	public Executor taskExecutor() {
			ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	        executor.setCorePoolSize(2);
	        executor.setMaxPoolSize(5);
	        executor.setQueueCapacity(10);
	        executor.setThreadNamePrefix("CityManager-");
	    executor.setAllowCoreThreadTimeOut(true);
	    executor.setKeepAliveSeconds(120);
	    executor.initialize();
	    return executor;
	}

}
