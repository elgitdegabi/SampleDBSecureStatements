package com.example.sample.db.securestmt.controller;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.sample.db.securestmt.controller.ExampleController;
import com.example.sample.db.securestmt.dto.ExampleDTO;
import com.example.sample.db.securestmt.service.ExampleService;

import static org.hamcrest.MatcherAssert.assertThat;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRunner.class) // since 1.4.X version we can use this annotation to run the test using SB features
public class ExampleControllerTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@SpyBean
	private ExampleController exampleController;
	
	@MockBean
	ExampleService exampleServiceMock;
	
	private List<ExampleDTO> expectedUsers;
	
	@Before
	public void setUp() {
		ExampleDTO dto = new ExampleDTO();
		dto.setId(String.valueOf(1));
		dto.setName("Name1");
		dto.setUser("User1");
		dto.setPassword("Password1");
		
		expectedUsers = new ArrayList<>();
		expectedUsers.add(dto);		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void getUserInfoWithValidArgumentsShouldReturnAResponseEntityWithAListWithUserInfoAndHttpStatusOK() throws Exception {
		Mockito.doReturn(expectedUsers).when(exampleServiceMock).getUserInfo(Mockito.anyString());
		
		ResponseEntity<?> result = exampleController.getUserInfo("User1");
		
		assertThat(result, notNullValue());
		assertThat(result.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(result.getBody(), notNullValue());
		
		List<ExampleDTO> users = (List<ExampleDTO>)result.getBody();
		
		assertThat(users.size(), equalTo(1));
		assertThat(users.get(0).getId(), equalTo("1"));
		assertThat(users.get(0).getName(), equalTo("Name1"));
		assertThat(users.get(0).getUser(), equalTo("User1"));
		assertThat(users.get(0).getPassword(), equalTo("Password1"));

		Mockito.verify(exampleServiceMock).getUserInfo(Mockito.anyString());
	}
	
	@Test
	public void getUserInfoWithValidArgumentsShouldReturnAResponseEntityWithErrorAndHttpStatusBadRequestWhenExampleServiceFails() throws Exception {
		SQLException expectedException = new SQLException("Some Expected Exception");

		Mockito.doThrow(expectedException).when(exampleServiceMock).getUserInfo(Mockito.anyString());

		ResponseEntity<?> result = exampleController.getUserInfo("User1");

		assertThat(result, notNullValue());
		assertThat(result.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
		assertThat(result.getBody(), notNullValue());
		
		String error = (String)result.getBody();
		
		assertThat(error, notNullValue());
		assertThat(error, equalTo("ERROR : " + expectedException.getMessage()));

		Mockito.verify(exampleServiceMock).getUserInfo(Mockito.anyString());;		
	}
}
