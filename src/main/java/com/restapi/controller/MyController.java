package com.restapi.controller;

import java.time.LocalDate;
import java.util.Optional;

//import java.util.List;
//import java.util.Optional;
import javax.validation.Valid;
import com.restapi.model.Patient;
import com.restapi.repository.MyRepository;
import com.restapi.service.KafKaProducerService;
import com.restapi.service.MyService;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    private final KafKaProducerService producerService;
 
    @Autowired
    public MyController(KafKaProducerService producerService) 
    {
        this.producerService = producerService;
    }

    @Autowired
    private MyService myService;

   /* @GetMapping("/patient/list")
    public List<Patient> listAll() {
        return myService.listAll();
    }


    @GetMapping("/patient")
    public List<Patient> searchPatient(@RequestParam(value = "firstname") String name) {
        return myService.getByFnameAndLname(name);
    }
    */
@GetMapping("/patient/{pid}")
    public Patient searchPatient(@PathVariable String pid) {
        return myService.findById(pid);
    }
    

/*
 @PostMapping("/patient")
    public Patient newPatient(@RequestBody Patient patient){
        String pattern = "^([A-Za-z0-9])*$";
       
        if (patient.getFirstname().matches(pattern) && patient.getLastname().matches(pattern) && patient.getDob().isBefore(LocalDate.now()) ) {
           System.out.println("valid");
            return myService.saveOrUpdate(patient);
        } else {
                System.out.println("Input format should be [A-Za-z0-9]");
                return null;
        }
    }*/
    @PostMapping("/patient")
    public ResponseEntity<Object> newPatient(@RequestBody Patient patient){


        String pattern = "^([A-Za-z0-9])*$";
                /*String msg = "Input format should be [A-Za-z0-9]";
                HttpHeaders header = new HttpHeaders();
                header.add("desc", "New Patient Addded");*/
       
                if (patient.getFirstname().matches(pattern) && patient.getLastname().matches(pattern) && patient.getDob().isBefore(LocalDate.now()) ) {
                    //System.out.println("valid");
                     Patient p = myService.saveOrUpdate(patient);
                     this.producerService.sendMessage(p.getPid());
                     ////return new ResponseEntity<>(p, HttpStatus.CREATED);...........(working)
                     //return new ResponseEntity<>(msg, HttpStatus.CREATED);
                    //return ResponseEntity<patient>(msg, header, Status, HttpStatus.CREATED);
                    //return ResponseEntity.status(HttpStatus.OK).headers(header).build();.........(working)
                    String msg = "New Patient is added";
		            return new ResponseEntity<Object>(msg, HttpStatus.CREATED.valueOf(200));
                } else {
                    
                  HttpStatus.valueOf(400);
				// return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);.......(working)
                   //return ResponseEntity<patient>("invalid", header,Status, HttpStatus.INTERNAL_SERVER_ERROR);
                   return new ResponseEntity<Object>("Input format should be [A-Za-z0-9]", HttpStatus.INTERNAL_SERVER_ERROR.valueOf(400));
                }
    }
/*
     @GetMapping("/patient/delete/{pid}")
    public String delete(@PathVariable String pid){
        System.out.println("in new patient api");
        myService.delete(pid);
        return "Patient id is Deleted"+pid;
    }
    */
    @RequestMapping("/patient/delete/{id}")
    public String delete(@PathVariable String id){
        myService.delete(id);
        return "redirect:/patient/list/0";
    }
/*
    @PutMapping("product/edit/{id}")
    public ResponseEntity<Object> edit(@PathVariable String id, Object status){
        Patient patient = myService.getById(id);
        System.out.println(patient);
        Patient p = myService.saveOrUpdate(patient);
        System.out.println(p);
        String msg = "New Patient is edited";
		return new ResponseEntity<Object>(msg, HttpStatus.CREATED.valueOf(200));
    }
*//*
    @PutMapping("/patient/edit/{id}")
    public ResponseEntity < Object > updateEmployee(@PathVariable(value = "id") String pid,
        @Valid @RequestBody Patient patientDetails) {
        Patient p = myService.findById(pid);

        p.setFirstname(patientDetails.getFirstname());
        p.setLastname(patientDetails.getLastname());
        p.setGender(patientDetails.getGender());
        p.setDob(patientDetails.getDob());
        final Patient updatedpatient = myService.save(p);
        this.producerService.editMessage(p.getPid());
        String msg = "Patient id " + pid + " is edited";
		return new ResponseEntity<Object>(msg, HttpStatus.CREATED.valueOf(200));
        
    }*/

    @PostMapping("/patient/editing/{id}")
    public ResponseEntity<Object> editPatient(@PathVariable(value = "id") String pid ,@RequestBody Patient patient, Object Status){


        String pattern = "^([A-Za-z0-9])*$";
                /*String msg = "Input format should be [A-Za-z0-9]";
                HttpHeaders header = new HttpHeaders();
                header.add("desc", "New Patient Addded");*/
       
                if (patient.getFirstname().matches(pattern) && patient.getLastname().matches(pattern) && patient.getDob().isBefore(LocalDate.now()) ) {
                    //System.out.println("valid");
                    // Patient p = myService.saveOrUpdate(patient);
                    // this.producerService.sendMessage(p.getPid());
                     ////return new ResponseEntity<>(p, HttpStatus.CREATED);...........(working)
                     //return new ResponseEntity<>(msg, HttpStatus.CREATED);
                    //return ResponseEntity<patient>(msg, header, Status, HttpStatus.CREATED);
                    //return ResponseEntity.status(HttpStatus.OK).headers(header).build();.........(working)
                   // String msg = "New Patient is added";
                   Patient p = myService.findById(pid);

        p.setFirstname(patient.getFirstname());
        p.setLastname(patient.getLastname());
        p.setGender(patient.getGender());
        p.setDob(patient.getDob());
        final Patient updatedpatient = myService.save(p);
        this.producerService.editMessage(p.getPid());
        String msg = "Patient id " + pid + " is edited";
		            return new ResponseEntity<Object>(msg, HttpStatus.CREATED.valueOf(200));
                } else {
                    
                  HttpStatus.valueOf(400);
				// return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);.......(working)
                   //return ResponseEntity<patient>("invalid", header,Status, HttpStatus.INTERNAL_SERVER_ERROR);
                   return new ResponseEntity<Object>("Input format should be [A-Za-z0-9]", HttpStatus.INTERNAL_SERVER_ERROR.valueOf(400));
                }
    }


/*
   @GetMapping(value = "/publish")
    public void getMessageToKafkaTopic(String message) 
    {
    	System.out.println(message+" GET METHOD");
        this.producerService.sendMessage(message);
    }
   */
 /*
    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) 
    {
        this.producerService.sendMessage(message);
    }*/



    /* @PostMapping("/collections")
    public ResponseEntity<Person> createPerson(@RequestBody Person per) {
        try {
            Person _pp = perRepository.save(new Person(per.getFirstname(), per.getLastname(), per.getGender(), per.getDob()));
            return new ResponseEntity<>(_pp, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
         }
    }
*/


     /*@PostMapping("/patient")
    public Patient newPatient(@RequestBody Patient patient){
           return myService.saveOrUpdate(patient);
    }*/

    
}