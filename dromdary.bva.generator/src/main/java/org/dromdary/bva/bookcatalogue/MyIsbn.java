/**
 * Copyright (c) 2009-2010 by droMDAry.org (see CONTRIBUTORS)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.dromdary.bva.bookcatalogue;


public class MyIsbn {
	@Isbn
	public String myIsbn;

	public MyIsbn(String myIsbn) {
		super();
		this.myIsbn = myIsbn;
	}
	
}
