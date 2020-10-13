package com.restapi.controller;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import com.restapi.model.Patient;
import com.restapi.service.MyService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MyController.class)
@WithMockUser
public class MyControllerTest {

   	@Autowired
	private MockMvc mockMvc;

    @MockBean
    private MyService myService;

    
    String date = "1995-09-15";
    LocalDate localDate = LocalDate.parse(date);

    Patient mockPatient = new Patient("Anamika", "Gautam", "female", localDate);

	String examplePatientJson = "{\"firstname\":\"Anamika\",\"lastname\":\"Gautam\", \"gender\":\"female\", \"dob\":\"1995-09-15\"}";

    @Test
	    public void newPatient() throws Exception {

            Patient patient = new Patient();
            patient.setFirstname("Anamika");
            patient.setLastname("Gautam");
            patient.setGender("female");
            patient.setDob(localDate);

		Mockito.when(
				myService.saveOrUpdate(Mockito.any(Patient.class))).thenReturn(mockPatient);

		        RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/patient")
				.accept(MediaType.APPLICATION_JSON).content(examplePatientJson)
				.contentType(MediaType.APPLICATION_JSON);

		        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		        MockHttpServletResponse response = result.getResponse();

		        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		        assertEquals("https://8081-ec3074d5-c4d4-47ba-a23a-ceb8d4bb0978.ws.kgp20200905.karkinos.in/patient",
                response.getHeader(HttpHeaders.LOCATION));
                
                //return ResponseEntity<Object>("SUCCESS", HttpStatus.OK.valueOf(200)));

	}



}