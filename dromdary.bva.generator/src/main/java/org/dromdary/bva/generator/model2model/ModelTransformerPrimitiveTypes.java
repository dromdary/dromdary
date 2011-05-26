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
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.internal.impl.ClassImpl;
import org.eclipse.uml2.uml.internal.impl.ModelImpl;
import org.eclipse.uml2.uml.internal.impl.PackageImpl;

public class ModelTransformerPrimitiveTypes extends SimpleJavaModificationComponent {

	protected void doModification(WorkflowContext ctx, ProgressMonitor monitor,
			Issues issues, Object model) {
		
		try {
			ModelImpl dm = (ModelImpl) model;
			EList<NamedElement> elms = dm.getMembers();

			for (NamedElement namedElement : elms) {
				// System.out.println(namedElement);
				EList<Element> elements = namedElement.allOwnedElements();
				for (Element element : elements) {
					if (element instanceof ClassImpl) {
						ClassImpl impl = (ClassImpl) element;
						System.out.println(impl.getName());

						removePrefix(impl.getAppliedStereotypes());

						EList<Operation> operations = impl.getAllOperations();
						for (Operation operation : operations) {
							removePrimitiveTypesPkgName(operation.getOwnedParameters());
							removePrimitiveTypesPkgName(operation.getReturnResult().getType());
							removePrefix(operation.getAppliedStereotypes());
						}

						EList<Property> attr = impl.getAllAttributes();
						for (Property property : attr) {
							removePrimitiveTypesPkgName(property);
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

	/*
	 * Removes the prefix "PrimitiveTypes" from list of Parameter
	 */
	private void removePrimitiveTypesPkgName(EList<Parameter> params) {
		for (Parameter parameter : params) {
			removePrimitiveTypesPkgName(parameter.getType());
		}
		
	}

	/*
	 * Removes the prefix "PrimitiveTypes" from Property
	 */
	private void removePrimitiveTypesPkgName(Property pProperty) {
		removePrimitiveTypesPkgName(pProperty.getType());
	}

	/*
	 * Removes the prefix "PrimitiveTypes" from Type
	 */
	private void removePrimitiveTypesPkgName(Type pType) {
		if (pType != null) {
			Package impl = pType.getPackage();
			if (impl.getName().equals("PrimitiveTypes")) {
				System.out.println(pType.getName() + " - " + impl.getName());
				impl.setName("");
			}
		}
	}
}
