/**
 * Copyright (c) 2009-2010 by droMDAry.org (see CONTRIBUTORS)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.dromdary.bva.bookcatalogue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import junit.framework.TestCase;

public class IsbnValidatorTest extends TestCase{
	private static Validator validator;
	
	public IsbnValidatorTest(String name) {
		super(name);
	}
	/**
	 * @throws java.lang.Exception
	 */
	public void setUp() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
	}

	/**
	 * @throws java.lang.Exception
	 */
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link org.dromdary.bva.bookcatalogue.IsbnValidator#isValid(java.lang.String, javax.validation.ConstraintValidatorContext)}.
	 */
	public void testIsValid() {
		// test with the oreilly GWT isbn
		MyIsbn myIsbn = new MyIsbn("9783897217942");
		Set<ConstraintViolation<MyIsbn>> constraintViolations =
           validator.validate(myIsbn);
		assertTrue(constraintViolations.isEmpty());
	}

}
