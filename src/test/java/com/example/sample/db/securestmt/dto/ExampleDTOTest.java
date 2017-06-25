package com.example.sample.db.securestmt.dto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.sample.db.securestmt.dto.ExampleDTO;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SpringRunner.class) // since 1.4.X version we can use this annotation to run the test using SB features
public class ExampleDTOTest {

	private ExampleDTO dto;
	
	@Before
	public void setUp() {
		dto = new ExampleDTO();
	}

	@Test
	public void getSetIdTest() {
		assertThat(dto.getId(), nullValue());
		dto.setId("theId");
		assertThat(dto.getId(), notNullValue());
		assertThat(dto.getId(), equalTo("theId"));
	}
	
	@Test
	public void getSetNameTest() {
		assertThat(dto.getName(), nullValue());
		dto.setName("theName");
		assertThat(dto.getName(), notNullValue());
		assertThat(dto.getName(), equalTo("theName"));
	}
	
	@Test
	public void getSetUserTest() {
		assertThat(dto.getUser(), nullValue());
		dto.setUser("theUser");
		assertThat(dto.getUser(), notNullValue());
		assertThat(dto.getUser(), equalTo("theUser"));
	}

	@Test
	public void getSetPasswordTest() {
		assertThat(dto.getPassword(), nullValue());
		dto.setPassword("thePassword");
		assertThat(dto.getPassword(), notNullValue());
		assertThat(dto.getPassword(), equalTo("thePassword"));
	}	
}
