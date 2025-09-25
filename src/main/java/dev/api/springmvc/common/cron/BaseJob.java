package dev.api.springmvc.common.cron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BaseJob {

	private static final Logger log = LoggerFactory.getLogger(BaseJob.class);

	@Scheduled(cron = "0 * * * * *")
	public void runBaseJob() {
		log.info("Base cron job running at {}", LocalDateTime.now());
	}
}
