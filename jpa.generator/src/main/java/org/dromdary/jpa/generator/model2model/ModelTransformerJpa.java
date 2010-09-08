/**
 * Copyright (c) 2009-2010 by droMDAry.org (see CONTRIBUTORS)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.dromdary.jpa.generator.model2model;

import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.monitor.ProgressMonitor;
import org.eclipse.uml2.uml.internal.impl.ModelImpl;

/**
 * Die Modell-zu-Modell Transformation ist nötig, damit die Generatoren (auch
 * Javabasic) mit der Multiinheritance der Stereotypen klar kommt und auch
 * andere Stereotypen ausser den JPA-Stereotypen verwendet werden können. ->
 * evtl. ein Bug des EMF???
 */
public class ModelTransformerJpa extends SimpleJavaModificationComponent {

	protected void doModification(WorkflowContext ctx, ProgressMonitor monitor,
			Issues issues, Object model) {

		ModelImpl dm = (ModelImpl) model;
		
		// AnyTypeImpl any = (AnyTypeImpl) model;
		//
		// EClass eclass = any.eClass();
		// EList<EAttribute> attributes = eclass.getEAllAttributes();
		//
		// for (EAttribute eAttribute : attributes) {
		//
		// System.out.println("jpa " + eAttribute);
		// }

		// EList<EObject> contents = any.eContents();
		//		
		// for (EObject eObject : contents) {
		//			
		// EList<EObject> modelContents = eObject.eContents();
		// for(EObject eModelContents: modelContents){
		//				
		// eModelContents.toString();
		// }
		// eObject.toString();
		// }

		// ModelImpl dm = (ModelImpl) model;
		//	
		// EList<NamedElement> elms = dm.getMembers();
		// for (NamedElement namedElement : elms) {
		// EList<Element> elements = namedElement.allOwnedElements();
		// for (Element element : elements) {
		// if (element instanceof ClassImpl) {
		// ClassImpl impl = (ClassImpl) element;
		//
		// EList<Stereotype> stetyp = impl.getApplicableStereotypes();
		// for (Stereotype stereotype : stetyp) {
		// String nameOld = stereotype.getName();
		//
		// // Prüfen ob der Stereotyp den Namen Entity enhält
		// // -> dieser darf nicht verändert werden
		// if ((!nameOld.contains("Entity"))
		//
		// // Prüfen ob der Postfix JPA bereits am
		// // Namen hängt -> dann muss nach nicht
		// // mehr angepasst werden
		// && (!nameOld.substring(nameOld.length() - 3)
		// .equals("JPA"))
		//
		// // Prüfen ob der Name des Stereotyp mit
		// // dem Präfix JPA_ beginnt
		// // -> dann handelt es sich um einen
		// // JPA-Stereotyp
		// && (nameOld.startsWith("JPA_"))) {
		// stereotype.setName(nameOld + "JPA");
		// }
		// }
		//
		// EList<Operation> operations = impl.getAllOperations();
		// for (Operation operation : operations) {
		// EList<Stereotype> stetp = operation
		// .getApplicableStereotypes();
		// for (Stereotype stereotpy : stetp) {
		// String nameTemp = stereotpy.getName();
		// if ((!nameTemp.substring(nameTemp.length() - 3)
		// .equals("JPA"))
		// && (nameTemp.startsWith("JPA_"))) {
		// stereotpy.setName(nameTemp + "JPA");
		// }
		// }
		// }
		//
		// EList<Property> attr = impl.getAllAttributes();
		// for (Property property : attr) {
		// EList<Stereotype> st = property
		// .getApplicableStereotypes();
		// for (Stereotype stereotpy : st) {
		// String nameTemp = stereotpy.getName();
		// if ((!nameTemp.substring(nameTemp.length() - 3)
		// .equals("JPA"))
		// && (nameTemp.startsWith("JPA_"))) {
		// stereotpy.setName(nameTemp + "JPA");
		// }
		// }
		// }
		// }
		// }
		// }
	}
}
