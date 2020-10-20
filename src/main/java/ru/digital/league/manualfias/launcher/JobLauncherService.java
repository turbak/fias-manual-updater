package ru.digital.league.manualfias.launcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;
import ru.digital.league.manualfias.dto.delta.FiasDeltaDto;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class JobLauncherService {

	private final JobLauncher jobLauncher;
	private final Job fiasUpdateJob;

	public void launchDelta(String deltaFile) throws IOException {
		File deltas = new File(System.getProperty("user.dir") + File.separator + deltaFile);
		ObjectMapper mapper = new ObjectMapper();
		CollectionType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, FiasDeltaDto.class);
		List<FiasDeltaDto> list = mapper.readValue(deltas, type);
		list.sort(Comparator.comparing(FiasDeltaDto::getVersionId));
		for (FiasDeltaDto dto : list) {
			log.info("Starting job for delta {}", dto.getTextVersion());
			JobParameters jobParameters = new JobParametersBuilder()
					.addLong("time", System.currentTimeMillis())
					.addLong("updateId", dto.getVersionId())
					.toJobParameters();
			try {
				JobExecution execution = jobLauncher.run(fiasUpdateJob, jobParameters);
				List<Throwable> exc = execution.getAllFailureExceptions();
				if (!exc.isEmpty()) {
					System.exit(0);
				}
			} catch (Exception e) {
				System.exit(0);
			}

		}
	}
}
