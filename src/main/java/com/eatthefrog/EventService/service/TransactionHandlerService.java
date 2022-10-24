package com.eatthefrog.EventService.service;

import lombok.extern.java.Log;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Log
@Service
public class TransactionHandlerService {

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void runInTransaction(Runnable runnable) throws Exception {
        try {
            runnable.run();
        } catch(Exception e) {
            log.severe(ExceptionUtils.getStackTrace(e));
            throw e;
        }
    }
}
