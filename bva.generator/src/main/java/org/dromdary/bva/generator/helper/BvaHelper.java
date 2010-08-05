/**
 * Copyright (c) 2009-2010 by droMDAry.org (see CONTRIBUTORS)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.dromdary.bva.generator.helper;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Stereotype;

public class BvaHelper {
	
	
	/**
	 * checks if myObj is instance of myType
	 * @param myObj
	 * @param myType
	 * @return
	 */
	public static boolean implTypeOf(java.lang.Object myObj, String myType) {
		try {
			if (java.lang.Class.forName(myType).isInstance(myObj)) {
				return true;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("Your type " + myType + " is not a valid uml::Type.");
			e.printStackTrace();
			return false;
		}

		return false;
	}
	
	public static EList<EObject> getTags(Stereotype stereotype) {
		
		
		return stereotype.getStereotypeApplications(); 
	}
	
	public static boolean hasTaggedValues(Stereotype st, Element el) {
		EObject test = el.getStereotypeApplication(st);
		TreeIterator<EObject> dafdfa = test.eAllContents();
		
		
		//Element adf = new Element();
		//adf.getValue(stereotype, propertyName)
		
		return true;
	}
	
	/**
	 * 
	 * @param element The element typed by the Stereotype
	 * @param stereoTypeName The name of the stereotype to get the
	 * TaggedValue from
	 * @param name The name of the TaggedValue
	 * @param type The datatype of the TaggedValue
	 * @return The value (already casted to his type) of a TaggedValue
	 */
//	public static Object getTaggedValue(Element element,
//			String stereoTypeName, String name, String type){
//		Object tv = null;
//		EList stereotypes = null;
//
//		stereotypes = element.getAppliedStereotypes();
//		for (Iterator iter = stereotypes.iterator(); iter.hasNext();) {
//			Stereotype stereotype = (Stereotype) iter.next();
//			if (stereotype.getName().equalsIgnoreCase(stereoTypeName)){
//				try{
//					tv = element.getValue(stereotype, name);
//				}catch (Exception ex){
//					tv = getTypeDefaults().get(type);
//				}
//				break;
//			}
//		}
//		return tv;
//	}
	
}
