package com.log.service;

import com.log.domain.Alert;
import com.log.domain.LogFile;
import com.log.exception.LogException;
import com.log.repository.LogFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

@Service
public class LogService
{

    private Logger logger = LoggerFactory.getLogger(LogService.class);

    @Inject
    private LogFileRepository logFileRepository ;

    @Inject
    private AlertService alertService ;


    public void readLogFile(MultipartFile file) throws  LogException{

        logger.info("read and parse the log file : {}",file.getName());
        if (!file.isEmpty()) {
            try {
                LogFile logFile = createLogFile(file.getOriginalFilename());
                Set<Alert> alerts = new HashSet<>() ;
                InputStream inputStream = file.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                   Alert alert = alertService.createAlert(line) ;
                    alerts.add(alert);
                }
                logFile.setAlerts(alerts);
                logFileRepository.save(logFile);
            } catch (IOException |RuntimeException e) {
                logger.error("error while treating the file : {}",file.getName(),e);
            }
        }

    }


    private LogFile createLogFile(String name) {
        LogFile logFile = new LogFile() ;
        logFile.setName(name);
        return  logFile ;

    }
}
