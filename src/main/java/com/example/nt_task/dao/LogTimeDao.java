package com.example.nt_task.dao;

import com.example.nt_task.entity.LogTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class LogTimeDao {
    private static final Logger log = LoggerFactory.getLogger(LogTimeDao.class);

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Save the log time in the database.
     */
    @Transactional
    public void create(LogTime logTime) {
        log.info("New log time: " + logTime.getTime());
        entityManager.persist(logTime);
    }

    /**
     * Return all the log times stored in the database.
     */
    public List<LogTime> getAll() {
        return entityManager.createQuery("from LogTime").getResultList();
    }

    /**
     * Return the log time having the passed id.
     */
    public LogTime getById(long id) {
        return entityManager.find(LogTime.class, id);
    }

}
