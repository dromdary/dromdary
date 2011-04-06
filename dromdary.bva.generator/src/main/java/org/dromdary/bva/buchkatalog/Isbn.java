/**
 * Copyright (c) 2009-2010 by droMDAry.org (see CONTRIBUTORS)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.dromdary.bva.buchkatalog;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;

import org.dromdary.bva.buchkatalog.IsbnValidator;

@Target( {METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = IsbnValidator.class)
@Documented
public @interface Isbn {

    String message() default "{validator.isbn}";

    Class<?>[] groups() default {};

}