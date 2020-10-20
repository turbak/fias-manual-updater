package ru.digital.league.manualfias;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.digital.league.manualfias.launcher.JobLauncherService;

import java.io.File;
import java.util.Arrays;

@Slf4j
@EnableScheduling
@EnableAsync
@EnableRetry
@EnableBatchProcessing
@Configuration
@SpringBootApplication
public class ServiceApplication implements CommandLineRunner {

    @Autowired
    JobLauncherService jobLauncherService;

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Arrays.stream(args).sequential().forEach(x -> log.info("arg {}", x));
        jobLauncherService.launchDelta(args[0]);
    }
}
