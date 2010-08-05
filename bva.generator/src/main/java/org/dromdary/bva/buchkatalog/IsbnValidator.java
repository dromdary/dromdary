/**
 * Copyright (c) 2009-2010 by droMDAry.org (see CONTRIBUTORS)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.dromdary.bva.buchkatalog;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.dromdary.bva.buchkatalog.Isbn;

public class IsbnValidator implements ConstraintValidator<Isbn, String> {

	public void initialize(Isbn constraintAnnotation) {
		// nothing to do
	}

	public boolean isValid(String isbn,
			ConstraintValidatorContext constraintContext) {
		// Here the ISBN-13 validation rules must be implemented
		// according to Wikipedia http://de.wikipedia.org/wiki/ISBN
		if (isbn == null)
			return false;
		
		if ( isbn.length() != 13)
			return false;
		
		// Calculate checksum
		char charArray[] =isbn.toCharArray();
		int intArray[] = new int[charArray.length];
		// convert string to int array
		int len = charArray.length;
		for(int i=0;i<charArray.length;i++)
			intArray[i]=Integer.parseInt(""+ charArray[i]);
		
		int sumA = 0;
		int sumB = 0;
		int checkSum = 0;
		// The 12 digits calculate the checkSum in position 13 
		for (int i=0;i<(intArray.length-1);i++){
			if ((i+1)%2 == 1) 
				sumA += intArray[i];
			else
				sumB += intArray[i];
		}
		if ((10-(sumA + (sumB*3))%10) == intArray[intArray.length-1])
			return true;
		else
			return false;
	}
}