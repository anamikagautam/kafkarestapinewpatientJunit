package com.restapi.service;

//import java.util.List;
//import java.util.Optional;

import com.restapi.model.Patient;

public interface MyService {
    
   // List<Patient> listAll();
    //List<Patient> getByFnameAndLname(String name);
    Patient saveOrUpdate(Patient patient);
    void delete(String pid);
    Patient save(Patient p);
    Patient findById(String pid);
    Patient getById(String id);
   // Patient saveOrUpdateProductForm(Patient patient);
    //Optional<Patient> getByPid(String pid);

}