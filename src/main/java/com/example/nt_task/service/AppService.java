package com.example.nt_task.service;

import com.example.nt_task.dao.LogTimeDao;
import com.example.nt_task.entity.LogTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class AppService {
    private static final Logger log = LoggerFactory.getLogger(AppService.class);

    @Autowired
    LogTimeDao logTimeDao;

    public void logTime(Timestamp timestamp) {
        logTimeDao.create(new LogTime(timestamp));
    }

    public List<LogTime> printAllTimes() {
        return logTimeDao.getAll();
    }
}
