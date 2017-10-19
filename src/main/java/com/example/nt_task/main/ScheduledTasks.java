package com.example.nt_task.main;

import com.example.nt_task.service.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    AppService appService;

    @Autowired
    DataSource dataSource;

    private static boolean tryConnect = true;
    private static int counter = 0;
    private Queue<Timestamp> times = new LinkedList<>();

    @Scheduled(fixedRate = 1000, initialDelay = 5000)
    public void addCurrentTime() throws InterruptedException, SQLException {
        times.add(new Timestamp(System.currentTimeMillis()));
    }

    @Scheduled(fixedRate = 1000, initialDelay = 5000)
    public void reportCurrentTime() throws InterruptedException, SQLException {
        if (tryConnect) {
            try {
                if (haveFiveSecondsPast()) {
                    counter = 0;
                    dataSource.getConnection();
                }
                if (!times.isEmpty()) {
                    while (times.peek() != null) {
                        long startTime = System.nanoTime();
                        appService.logTime(times.peek());
                        long stopTime = System.nanoTime();
                        times.poll();
                        long resultTimeSec = TimeUnit.SECONDS.convert((stopTime - startTime), TimeUnit.NANOSECONDS);
                        if (resultTimeSec > 3) {
                            log.warn("Slow connection with database. Spent " + TimeUnit.SECONDS.convert((stopTime - startTime), TimeUnit.NANOSECONDS) + " seconds");
                        }
                    }
                }
            } catch (Exception e) {
                tryConnect = false;
                log.warn("No connection with database. Try to connect after 5 seconds ...");
            }
        } else {
            counter++;
            if (haveFiveSecondsPast()) {
                tryConnect = true;
            }
        }
    }

    private boolean haveFiveSecondsPast() {
        return counter == 5;
    }
}