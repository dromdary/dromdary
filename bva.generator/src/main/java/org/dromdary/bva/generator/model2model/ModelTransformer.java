/**
 * Copyright (c) 2009-2010 by droMDAry.org (see CONTRIBUTORS)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.dromdary.bva.generator.model2model;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.internal.impl.ModelImpl;
import org.openarchitectureware.workflow.WorkflowContext;
import org.openarchitectureware.workflow.issues.Issues;
import org.openarchitectureware.workflow.lib.SimpleJavaModificationComponent;
import org.openarchitectureware.workflow.monitor.ProgressMonitor;

public class ModelTransformer extends SimpleJavaModificationComponent {

	@Override
	protected void doModification(WorkflowContext ctx, ProgressMonitor monitor,
			Issues issues, Object model) {

		System.out.println("analyzing model");
		
		ModelImpl dm = (ModelImpl) model;
		EList<NamedElement> elms = dm.getMembers();
		for (NamedElement namedElement : elms) {
			System.out.println("\t" + namedElement);

			EList<Element> elements = namedElement.allOwnedElements();
			for (Element element : elements) {
				System.out.println("\t\t" + element);
//				if (element instanceof ClassImpl) {
//					ClassImpl impl = (ClassImpl) element;
////					System.out.println("Class: " + impl.getAppliedStereotypes());
////					EList<Stereotype> stetyp = impl.getAppliedStereotypes();
////					for (Stereotype stereotype : stetyp) {
////						String nameOld = stereotype.getName();
////						if (!nameOld.substring(nameOld.length() - 2).equals("Cl")) {
////							stereotype.setName(nameOld + "Cl");
////						}
////					}				
//					EList<Operation> operations = impl.getAllOperations();
//					for (Operation operation : operations) {
////						System.out.println("Operation: " + operation.getAppliedStereotypes());
//						EList<Stereotype> stetp = operation.getAppliedStereotypes();
//						for (Stereotype stereotype : stetp) {
//							String nameOld = stereotype.getName();
//							if (!nameOld.substring(nameOld.length() - 2).equals("Op")) {
//								stereotype.setName(nameOld + "Op");
//							}
//						}
//					}					
//					EList<Property> attr = impl.getAllAttributes();
//					for (Property property : attr) {
////						System.out.println("Property: " + property.getAppliedStereotypes());
//						EList<Stereotype> stetp = property.getAppliedStereotypes();
//						for (Stereotype stereotype : stetp) {
//							String nameOld = stereotype.getName();
//							if (!nameOld.substring(nameOld.length() - 2).equals("Pr")) {
//								stereotype.setName(nameOld + "Pr");
//							}
//						}
//					}
//				}
			}
		}
	}
}
