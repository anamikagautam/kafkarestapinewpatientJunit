package com.restapi.service;

import com.restapi.model.AppConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate; 
import org.springframework.stereotype.Service;

 
@Service
public class KafKaProducerService 
{
    private static final Logger logger = 
            LoggerFactory.getLogger(KafKaProducerService.class);
     
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
 
    public void sendMessage(String message) 
    {
        logger.info(String.format("Patient id is -> %s", message));
        this.kafkaTemplate.send(AppConstants.TOPIC_NAME, message);
    }

     public void deleteMessage(String message) 
    {
        logger.info(String.format("Deleted Patient id is -> %s", message));
        this.kafkaTemplate.send(AppConstants.TOPIC_NAME1, message);
    }

    public void editMessage(String message) 
    {
        logger.info(String.format("Patient id is edited -> %s", message));
        this.kafkaTemplate.send(AppConstants.TOPIC_NAME2, message);
    }
}