package com.ytem.repository.task;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class CleanerTask {
	private final Logger logger = Logger.getLogger(CleanerTask.class);

	/*@Scheduled(cron="0/10 * *  * * ? ")*/
	public void clear() {
		
	}
}
