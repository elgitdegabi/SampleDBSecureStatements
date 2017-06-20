package com.example.sample.db.securestmt.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.sample.db.securestmt.dao.ExampleDAO;
import com.example.sample.db.securestmt.dto.ExampleDTO;
import com.example.sample.db.securestmt.service.ExampleService;

import static org.hamcrest.MatcherAssert.assertThat;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRunner.class) // since 1.4.X version we can use this annotation to run the test using SB features
public class ExampleServiceTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@SpyBean
	private ExampleService exampleService;

	@MockBean
	ExampleDAO exampleDAOMock;
	
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
	
	@Test
	public void getUserInfoWithValidArgumentsShouldReturnAListWithUserInfo() throws Exception {
		Mockito.doReturn(expectedUsers).when(exampleDAOMock).findUserInfoByUser(Mockito.anyString());
		
		List<ExampleDTO> result = exampleService.getUserInfo("User1");

		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(expectedUsers.size()));
		assertThat(result.get(0), equalTo(expectedUsers.get(0)));
		assertThat(result.get(0).getId(), equalTo(expectedUsers.get(0).getId()));
		assertThat(result.get(0).getName(), equalTo(expectedUsers.get(0).getName()));
		assertThat(result.get(0).getUser(), equalTo(expectedUsers.get(0).getUser()));
		assertThat(result.get(0).getPassword(), equalTo(expectedUsers.get(0).getPassword()));

		Mockito.verify(exampleDAOMock).findUserInfoByUser(Mockito.anyString());
	}
	
	@Test
	public void getUserInfoWithValidArgumentsShouldThrowASQLExceptionWhenDAOThrowsAnSQLException() throws Exception {
		SQLException expectedException = new SQLException("Some Expected DAO Exception");

		thrown.expect(SQLException.class);
		thrown.expectMessage(expectedException.getMessage());

		Mockito.doThrow(expectedException).when(exampleDAOMock).findUserInfoByUser(Mockito.anyString());

		exampleService.getUserInfo("User1");
				
		Mockito.verify(exampleDAOMock).findUserInfoByUser(Mockito.anyString());		
	}
}
