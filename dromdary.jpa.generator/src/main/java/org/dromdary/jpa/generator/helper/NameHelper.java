package org.dromdary.jpa.generator.helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.eclipse.uml2.uml.Property;

import javax.swing.text.TabExpander;

public class NameHelper {
	public static String abstractClassName(String name) {
		if (!name.contains("Abstract"))
		{
			return "Abstract"+name;
		}
		else
		{
			return name;
		}
	}
	
	public static String generateTableName(String tableName) {
		StringBuffer bf = new StringBuffer(tableName);
		int underScores = 0;
		for (int i = 1; i < tableName.length(); i++) 
		{
			if (tableName.substring(i, i + 1).equals(tableName.substring(i, i + 1).toUpperCase()) && underScores == 0)
			{
				bf = bf.insert(i, "_");
				underScores++;
			}
			else if (tableName.substring(i, i + 1).equals(tableName.substring(i, i + 1).toUpperCase()) && underScores > 0)
			{
				bf = bf.insert(i + underScores, "_");
			}
		} 
		return "TEST_" + bf.toString().toUpperCase();
	}
	
	public static String generateColumnName(Property property) {
		String tableName = generateTableName(property.getClass_().getName());
		String[] splitString = tableName.split("_");
		
		StringBuffer bf = new StringBuffer();
		if (splitString.length == 2)
		{
			bf.append(splitString[1].substring(0, 3));
		}
		else if (splitString.length == 3)
		{
			bf.append(splitString[1].substring(0, 3));
			bf.append(splitString[2].substring(0, 1));
		}
		else if (splitString.length == 4)
		{
			bf.append(splitString[1].substring(0, 3));
			bf.append(splitString[2].substring(0, 1));
			bf.append(splitString[3].substring(0, 1));
		}
		
		bf.append("_");
		
		String propertyName = property.getName();
		int underScores = 0;
		StringBuffer bf2 = new StringBuffer(propertyName);
		for (int i = 1; i < propertyName.length(); i++) 
		{
			if (propertyName.substring(i, i + 1).equals(propertyName.substring(i, i + 1).toUpperCase()) && underScores == 0)
			{
				bf2 = bf2.insert(i, "_");
				underScores++;
			}
			else if (propertyName.substring(i, i + 1).equals(propertyName.substring(i, i + 1).toUpperCase()) && underScores > 0)
			{
				bf2 = bf2.insert(i + underScores, "_");
			}
		}
		bf.append(bf2);
		
		return bf.toString().toUpperCase();
	}
	
public static String generateColumnName(String property, String tableName) {
		String[] splitString = tableName.split("_");
		
		StringBuffer bf = new StringBuffer();
		if (splitString.length == 2)
		{
			bf.append(splitString[1].substring(0, 3));
		}
		else if (splitString.length == 3)
		{
			bf.append(splitString[1].substring(0, 3));
			bf.append(splitString[2].substring(0, 1));
		}
		else if (splitString.length == 4)
		{
			bf.append(splitString[1].substring(0, 3));
			bf.append(splitString[2].substring(0, 1));
			bf.append(splitString[3].substring(0, 1));
		}
		
		bf.append("_");
		
		String propertyName = property;
		int underScores = 0;
		StringBuffer bf2 = new StringBuffer(propertyName);
		for (int i = 1; i < propertyName.length(); i++) 
		{
			if (propertyName.substring(i, i + 1).equals(propertyName.substring(i, i + 1).toUpperCase()) && underScores == 0)
			{
				bf2 = bf2.insert(i, "_");
				underScores++;
			}
			else if (propertyName.substring(i, i + 1).equals(propertyName.substring(i, i + 1).toUpperCase()) && underScores > 0)
			{
				bf2 = bf2.insert(i + underScores, "_");
			}
		}
		bf.append(bf2);
		
		return bf.toString().toUpperCase();
	}

public static String getColumnNamePrefix(String columnName) {
	String[] splitString = columnName.split("_");
	return splitString[0].toUpperCase();
}
}
