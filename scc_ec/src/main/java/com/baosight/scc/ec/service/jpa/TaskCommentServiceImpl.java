package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.service.TaskCommentService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by zodiake on 2014/6/5.
 */
@Service
@Transactional
public class TaskCommentServiceImpl implements TaskCommentService{

    @Override
    @Scheduled(cron = "0 22 17 * * ?")
    public void shopRank() {
        System.out.print("hello");
    }
}
