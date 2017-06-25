package com.example.sample.db.securestmt.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.sample.db.securestmt.dto.ExampleDTO;

@RunWith(SpringRunner.class)
public class ExampleSecureDAOImplTest {
	
	// include the query because if you change the query accidentally the test case will fail (it's a good checkpoint)
	final static String QUERY_FIND_ALL_SECURE = " SELECT ID, NAME, USER, PASSWORD FROM USER_TEST WHERE USER = ?";
	
	@SpyBean
	ExampleSecureDAOImpl exampleSecureDAOImpl;
	
	@MockBean
	DataSource dataSourceMock;

	@MockBean
	Connection connectionMock;
	@MockBean
	PreparedStatement preparedStatementMock = null;
	@MockBean
	ResultSet resultSetMock = null;

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
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
	public void findUserInfoByUserWithValidArgumentsShouldReturnAListWithUserInfo() throws Exception {
		Mockito.doReturn(connectionMock).when(dataSourceMock).getConnection();
		Mockito.doReturn(preparedStatementMock).when(connectionMock).prepareStatement(QUERY_FIND_ALL_SECURE);
		Mockito.doNothing().when(preparedStatementMock).setString(Mockito.anyInt(), Mockito.anyString());
		Mockito.doReturn(resultSetMock).when(preparedStatementMock).executeQuery();
		
		// mocks the ResultSet iteration and sets the expect results
		Mockito.doReturn(true).doReturn(false).when(resultSetMock).next();
		Mockito.doReturn(expectedUsers.get(0).getId()).when(resultSetMock).getString(1);
		Mockito.doReturn(expectedUsers.get(0).getName()).when(resultSetMock).getString(2);
		Mockito.doReturn(expectedUsers.get(0).getUser()).when(resultSetMock).getString(3);
		Mockito.doReturn(expectedUsers.get(0).getPassword()).when(resultSetMock).getString(4);
		
		// mocks the finally block
		Mockito.doNothing().when(resultSetMock).close();
		Mockito.doNothing().when(preparedStatementMock).close();
		Mockito.doNothing().when(connectionMock).close();
		
		// invokes the test subject that uses the mocked behaviors		
		List<ExampleDTO> result = exampleSecureDAOImpl.findUserInfoByUser("User1");
		
		// assert the expected results of the test case
		assertThat(result, notNullValue());
		assertThat(result.size(), equalTo(expectedUsers.size()));
		assertThat(result.get(0).getId(), equalTo(expectedUsers.get(0).getId()));
		assertThat(result.get(0).getName(), equalTo(expectedUsers.get(0).getName()));
		assertThat(result.get(0).getUser(), equalTo(expectedUsers.get(0).getUser()));
		assertThat(result.get(0).getPassword(), equalTo(expectedUsers.get(0).getPassword()));
	}

	@Test
	public void findUserInfoByUserWithValidArgumentsShouldThrowASQLExceptionWhenGetConnectionThrowsAnException() throws Exception {
		SQLException expectedException = new SQLException("Get Connection Exception");

		thrown.expect(SQLException.class);
		thrown.expectMessage(expectedException.getMessage());

		Mockito.doThrow(expectedException).when(dataSourceMock).getConnection();
		
		// mocks the finally block
		Mockito.doNothing().when(resultSetMock).close();
		Mockito.doNothing().when(preparedStatementMock).close();
		Mockito.doNothing().when(connectionMock).close();

		exampleSecureDAOImpl.findUserInfoByUser("User1");				
	}	
}
