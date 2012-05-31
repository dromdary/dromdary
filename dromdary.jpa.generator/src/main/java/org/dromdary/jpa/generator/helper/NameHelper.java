package org.dromdary.jpa.generator.helper;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.uml2.uml.Property;

public class NameHelper {
	private static final String ABSTRACT_CLASS_NAME_PREFIX = "Abstract";
	private static final String INTERFACE_NAME_PREFIX = "I";
	private static final int TABLE_NAME_MAX_LENGTH = 30;
	
	public static String abstractClassName(String name) {
		name = convertToJavaPackageName(name);
		if (!name.contains(ABSTRACT_CLASS_NAME_PREFIX))
		{
			return addPrefixToName(name, ABSTRACT_CLASS_NAME_PREFIX);
		}
		else
		{
			return name;
		}
	}
	
	private static String addPrefixToName(String name, String prefix) {
		String className = name;
		String packageName = "";
		int classNamePosition = name.lastIndexOf(".");
		if (classNamePosition != -1) {
			classNamePosition++;
			className = name.substring(classNamePosition);
			packageName = name.substring(0, classNamePosition);
		}
		return packageName + prefix + className;
	}
	
	public static String interfaceName(String name) {
		return addPrefixToName(name, INTERFACE_NAME_PREFIX);
	}
	
	public static String convertToJavaPackageName(String name) {
		if (name != null && name.contains("::")) {
			if (name.startsWith("Model::")) {
				name = name.replaceFirst("Model::", "");
			}
			name = name.replaceAll("::", ".");
		}
		return name;
	}
	
	public static String generateTableName(String tableName) {
		StringBuffer bf = new StringBuffer(tableName.length());
		for (int i = 0; i < tableName.length(); i++)
		{
			String nextChar = String.valueOf(tableName.charAt(i));
			if (nextChar.equals(nextChar.toUpperCase()) && i > 0)
			{
				bf.append("_");
			}
			bf.append(nextChar);
		}
		
		String name = bf.toString().toUpperCase();

		try {
			String prefix = PropertiesHelper.getTableNamePrefix();
			if (prefix != null) {
				if (!prefix.endsWith("_")) {
					prefix += "_";
				}
				name = prefix.toUpperCase() + name;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return shortenNameIfNecessary(name, TABLE_NAME_MAX_LENGTH);
	}
	
	private static String shortenNameIfNecessary(String name, int maxLength) {
		if (name.length() > maxLength) {
			name = name.substring(0, maxLength);
			if (name.endsWith("_")) {
				name = name.substring(0, maxLength - 1);
			}
		}
		return name;
	}
	
	public static String generateColumnName(Property property) {
		String tableName = generateTableName(property.getClass_().getName());
		String propertyName = property.getName();
		return generateColumnName(propertyName, tableName);
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
