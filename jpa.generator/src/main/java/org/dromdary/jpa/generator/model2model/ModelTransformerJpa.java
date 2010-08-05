/**
 * Copyright (c) 2009-2010 by droMDAry.org (see CONTRIBUTORS)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.dromdary.jpa.generator.model2model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.internal.impl.ClassImpl;
import org.eclipse.uml2.uml.internal.impl.ModelImpl;
import org.openarchitectureware.workflow.WorkflowContext;
import org.openarchitectureware.workflow.issues.Issues;
import org.openarchitectureware.workflow.lib.SimpleJavaModificationComponent;
import org.openarchitectureware.workflow.monitor.ProgressMonitor;

/**
 * Die Modell-zu-Modell Transformation ist nötig, damit die
 * Generatoren (auch Javabasic) mit der Multiinheritance der Stereotypen klar
 * kommt und auch andere Stereotypen ausser den JPA-Stereotypen
 * verwendet werden können. -> evtl. ein Bug des EMF???
 */
public class ModelTransformerJpa extends SimpleJavaModificationComponent {

	@Override
	protected void doModification(WorkflowContext ctx, ProgressMonitor monitor,
			Issues issues, Object model) {
		ModelImpl dm = (ModelImpl) model;
		EList<NamedElement> elms = dm.getMembers();
		for (NamedElement namedElement : elms) {
			EList<Element> elements = namedElement.allOwnedElements();
			for (Element element : elements) {
				if (element instanceof ClassImpl) {
					ClassImpl impl = (ClassImpl) element;

					EList<Stereotype> stetyp = impl.getApplicableStereotypes();
					for (Stereotype stereotype : stetyp) {
						String nameOld = stereotype.getName();
						
						// Prüfen ob der Stereotyp den Namen Entity enhält 
						// -> dieser darf nicht verändert werden
						if ((!nameOld.contains("Entity"))
								
								// Prüfen ob der Postfix JPA bereits am 
								// Namen hängt -> dann muss nach nicht 
								// mehr angepasst werden
								&& (!nameOld.substring(nameOld.length() - 3)
										.equals("JPA"))
										
								// Prüfen ob der Name des Stereotyp mit
								// dem Präfix JPA_ beginnt
								// -> dann handelt es sich um einen
								// JPA-Stereotyp
								&& (nameOld.startsWith("JPA_"))) {
							stereotype.setName(nameOld + "JPA");
						}
					}

					EList<Operation> operations = impl.getAllOperations();
					for (Operation operation : operations) {
						EList<Stereotype> stetp = operation
								.getApplicableStereotypes();
						for (Stereotype stereotpy : stetp) {
							String nameTemp = stereotpy.getName();
							if ((!nameTemp.substring(nameTemp.length() - 3)
									.equals("JPA"))
									&& (nameTemp.startsWith("JPA_"))) {
								stereotpy.setName(nameTemp + "JPA");
							}
						}
					}

					EList<Property> attr = impl.getAllAttributes();
					for (Property property : attr) {
						EList<Stereotype> st = property
								.getApplicableStereotypes();
						for (Stereotype stereotpy : st) {
							String nameTemp = stereotpy.getName();
							if ((!nameTemp.substring(nameTemp.length() - 3)
									.equals("JPA"))
									&& (nameTemp.startsWith("JPA_"))) {
								stereotpy.setName(nameTemp + "JPA");
							}
						}
					}
				}
			}
		}
	}
}
