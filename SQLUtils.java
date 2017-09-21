package com.geaviation.eds.service.common.util;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SQLUtils {


	private static final Log LOGGER = LogFactory.getLog(SQLUtils.class);

	private SQLUtils(){

	}

	public static void closeQuietly(Connection connection)
	{
		try
		{
			if (connection != null)
			{
				connection.close();
			}
		}
		catch (SQLException e)
		{
			LOGGER.error("An error occurred closing connection.", e);
		}
	}

	public static void closeQuietly(Statement statement)
	{
		try
		{
			if (statement!= null)
			{
				statement.close();
			}
		}
		catch (SQLException e)
		{
			LOGGER.error("An error occurred closing statement.", e);
		}
	}

	public static void closeQuietly(ResultSet resultSet)
	{
		try
		{
			if (resultSet!= null)
			{
				resultSet.close();
			}
		}
		catch (SQLException e)
		{
			LOGGER.error("An error occurred closing result set.", e);
		}
	}

	public static void closeQuietly(Connection connection,
			ResultSet resultSet, Statement statement) {
		closeQuietly(connection);
		closeQuietly(resultSet);
		closeQuietly(statement);
	}
}
