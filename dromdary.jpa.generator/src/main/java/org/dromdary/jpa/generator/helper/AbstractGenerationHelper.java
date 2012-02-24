package org.dromdary.jpa.generator.helper;

import org.eclipse.uml2.uml.Classifier;

public class AbstractGenerationHelper {

	public static boolean isMappedSuperclass(Classifier umlClassifier){
		if(umlClassifier.getAppliedStereotype("SimpleJSR220::JPA_MappedSupperclass") != null){
			return true;
		}
		System.err.println("bin heir");
		for (Classifier parent : umlClassifier.allParents()){
			if(parent.getAppliedStereotype("SimpleJSR220::JPA_MappedSupperclass") != null){
				return true;
			}
		}
		return false;
	}
}
