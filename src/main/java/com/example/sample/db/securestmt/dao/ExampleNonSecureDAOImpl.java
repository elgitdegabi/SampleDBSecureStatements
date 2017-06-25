/** 
 * SampleDBSecureStatements.
 * Copyright (C) 2017 Gabelopment (gabelopment@gmail.com)
 * 
 * This file is part of SampleDBSecureStatements.
 * 
 * SampleDBSecureStatements is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * SampleDBSecureStatements is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with SampleDBSecureStatements.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.example.sample.db.securestmt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.sample.db.securestmt.dto.ExampleDTO;

@Repository("NonSecureDAO")
public class ExampleNonSecureDAOImpl implements ExampleDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExampleNonSecureDAOImpl.class);
	
	final static String QUERY_FIND_ALL_UNSECURE = " SELECT * FROM USER_TEST WHERE USER = ";
	
	@Autowired
	DataSource dataSource;
	
	public List<ExampleDTO> findUserInfoByUser(final String user) throws SQLException {
		List<ExampleDTO> records = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(QUERY_FIND_ALL_UNSECURE + "'"+ user +"'");
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				ExampleDTO dto = new ExampleDTO();
				dto.setId(resultSet.getString(1));
				dto.setName(resultSet.getString(2));
				dto.setUser(resultSet.getString(3));
				dto.setPassword(resultSet.getString(4));
				
				records.add(dto);
			}
			
			return records;
		} catch (SQLException sqle) {
			LOGGER.error("Error performing query: " + QUERY_FIND_ALL_UNSECURE, sqle);
			throw sqle;
		} finally {
			closeSQLObject(resultSet, ResultSet.class.getName());
			closeSQLObject(preparedStatement, PreparedStatement.class.getName());
			closeSQLObject(connection, Connection.class.getName());
		}
	}
	
	/**
	 * Closes the given SQL AutoCloseable object.
	 * @param sqlObject
	 * @param sqlObjectType
	 */
	private void closeSQLObject(final AutoCloseable sqlObject, final String sqlObjectType) {
		try {
			sqlObject.close();
		} catch (Exception e) {
			LOGGER.warn("Exception closing : {}", sqlObjectType);
		}
	}
}
