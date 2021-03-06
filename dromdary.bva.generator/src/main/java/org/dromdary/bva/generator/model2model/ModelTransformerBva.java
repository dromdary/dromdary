/**
 * Copyright (c) 2009-2010 by droMDAry.org (see CONTRIBUTORS)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.dromdary.bva.generator.model2model;

import org.dromdary.jpa.generator.model2model.SimpleJavaModificationComponent;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.monitor.ProgressMonitor;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;


public class ModelTransformerBva extends SimpleJavaModificationComponent{ 

	protected void doModification(WorkflowContext ctx, ProgressMonitor monitor,
			Issues issues, Object model) {
		
		try {
			Model dm = (Model) model;
			EList<NamedElement> elms = dm.getMembers();

			for (NamedElement namedElement : elms) {
				// System.out.println(namedElement);
				EList<Element> elements = namedElement.allOwnedElements();
				for (Element element : elements) {
					if (element instanceof Class) {
						Class impl = (Class) element;

						removePrefix(impl.getAppliedStereotypes());

						EList<Operation> operations = impl.getAllOperations();
						for (Operation operation : operations) {
							removePrefix(operation.getAppliedStereotypes());
						}

						EList<Property> attr = impl.getAllAttributes();
						for (Property property : attr) {
							removePrefix(property.getAppliedStereotypes());
						}
					}
				}
			}
		} catch (java.lang.ClassCastException exception) {
			System.out.println(exception.toString()
					+ "\nPlease control the format of Inputmodel!\n");
		}
	}

	/*
	 * Removes the prefix "BVA_" from stereotypes
	 */
	private void removePrefix(EList<Stereotype> stereotypes) {
		for (Stereotype stereotype : stereotypes) {
			String nameTemp = stereotype.getName();

			if (nameTemp.contains("BVA_")) {
				stereotype.setName(nameTemp.substring(4, nameTemp.length()));
			}
		}
	}
}
