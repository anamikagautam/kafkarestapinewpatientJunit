package com.restapi.service;

//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;

import com.restapi.model.Patient;
import com.restapi.repository.MyRepository;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class MyServiceImpl implements MyService {
    private MyRepository myRepository;

    private final KafKaProducerService producerService;

    @Autowired
    public MyServiceImpl(MyRepository myRepository,  KafKaProducerService producerService) {
        this.myRepository = myRepository;
        this.producerService = producerService;
    }

    /*
    @Override
    public List<Patient> getByFnameAndLname(String name) {
        List<Patient> patients = new ArrayList<>();
        myRepository.getByFnameAndLname(name).forEach(patients::add);
        return patients;
    }

    @Override
    public List<Patient> listAll() {
        List<Patient> patients = new ArrayList<>();
        myRepository.findAll().forEach(patients::add);
        return patients;
    }
*/

    @Override
    public Patient saveOrUpdate(Patient patient) {
        patient.setPid(RandomStringUtils.randomAlphanumeric(16));
        myRepository.save(patient);
        return patient;
    }

    @Override
    public void delete(String pid) {
        myRepository.deleteById(pid);
         producerService.deleteMessage(pid);
    }
/*
    @Override
    public void delete(String id) {
        myRepository.deleteById(id);
    }
*/
    @Override
    public Patient getById(String id) {
        return myRepository.findById(id).orElse(null);
    }

    @Override
    public Patient findById(String pid) {
        
        return myRepository.findById(pid).orElse(null);
    }

    @Override
    public Patient save(Patient p) {
           return myRepository.save(p);
    }

/*
    @Override
    public Optional<Patient> getByPid(String pid) {
        return myRepository.findById(pid);
    }

  */  
    
}