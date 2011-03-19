/**
 * Copyright (c) 2009-2010 by droMDAry.org (see CONTRIBUTORS)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.dromdary.jpa.generator.helper;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Class;

public class EntityHelper {
	static String relationTypeComposite = "composite";
	static String relationTypeAssociation = "association";
	static String relationTypeAggregation = "aggregation";
	static String oneToOne = "OneToOne";
	static String oneToMany = "OneToMany";
	static String manyToOne = "ManyToOne";
	static String manyToMany = "ManyToMany";

	/**
	 * Pr�fen, ob Attributnamen gesetzt sind und ggf. setzen. Falls Attribut zu
	 * einer Beziehung geh�rt, werden diese nicht implizit gesetzt. Bsp:
	 * <ownedAttribute xmi:id="70d3f655-57d7-48ac-bb0c-47afc4c03206" name=""
	 * visibility="public" type="{7C26B580-E30D-42e6-8854-15CF672CEEA7}"
	 * aggregation="shared"
	 * association="{2FB9BEEE-A6A4-4490-B5BB-F9B02B0C3AFC}"> <upperValue
	 * xmi:type="uml:LiteralUnlimitedNatural" xmi:id="__ZuR4T-dEd6wd9pmP8Bzng"
	 * value="*"/> <lowerValue xmi:type="uml:LiteralInteger"
	 * xmi:id="__ZuR4D-dEd6wd9pmP8Bzng"/></ownedAttribute>
	 * 
	 * @param Class
	 *            umlClass - Aktuelle UML-Klasse.
	 * @return String ""
	 */
	public static String checkAttributeName(Class umlClass) {
		EList<Property> attributes = umlClass.getAllAttributes();
		for (int i = 0; i < attributes.size(); i++) {
			// Pr�fen ob es sich um eine Klasse oder einen anderen Typ wie
			// PrimitiveType (String...) handelt
			if (attributes.get(i).getType().getClass().toString().contains(
					"ClassImpl")) {
				if (checkAssociationRelatedElements(umlClass, attributes.get(i))) {
					addAttributeName(attributes.get(i));
				}

			}
		}
		return "";
	}

	/**
	 * Pr�fen, um was f�r eine Beziehung es sich handelt.
	 * 
	 * @param Property
	 *            umlProperty - Die aktuelle UML-Property.
	 * @return String multiplicity - Entweder null, OneToOne, OneToMany,
	 *         ManyToMany oder ManyToOne.
	 */
	public static String getPropertyMultiplicity(Property umlProperty) {
		String multiplicity = null;
		EList<Association> association = umlProperty.getClass_()
				.getAssociations();
		if (umlProperty.getAssociation() != null) {
			for (int i = 0; i < association.size(); i++) {
				if (umlProperty.getUpper() == 1) {
					if (umlProperty.getOtherEnd().getUpper() == -1) {
						multiplicity = manyToOne;
					}
					if (umlProperty.getOtherEnd().getUpper() == 1) {
						multiplicity = oneToOne;
					}
				} else if (umlProperty.getUpper() == -1) {
					if (umlProperty.getOtherEnd().getUpper() == 1) {
						multiplicity = oneToMany;
					} else if (umlProperty.getOtherEnd().getUpper() == -1) {
						multiplicity = manyToMany;
					}
				}
			}
		}
		return multiplicity;
	}

	/**
	 * Pr�fen, um was f�r eine Beziehung es sich handelt.
	 * 
	 * @param Property
	 *            umlProperty - Die aktuelle UML-Property.
	 * @return String multiplicity - Entweder null, OneToOne, OneToMany,
	 *         ManyToMany oder ManyToOne.
	 */
	public static String getOtherEndPropertyMultiplicity(Property umlProperty) {
		String multiplicity = null;
		EList<Association> association = umlProperty.getClass_()
				.getAssociations();
		if (umlProperty.getAssociation() != null) {
			for (int i = 0; i < association.size(); i++) {
				if (umlProperty.getOtherEnd().getUpper() == 1) {
					if (umlProperty.getUpper() == -1) {
						multiplicity = manyToOne;
					}
					if (umlProperty.getUpper() == 1) {
						multiplicity = oneToOne;
					}
				} else if (umlProperty.getOtherEnd().getUpper() == -1) {
					if (umlProperty.getUpper() == 1) {
						multiplicity = oneToMany;
					} else if (umlProperty.getUpper() == -1) {
						multiplicity = manyToMany;
					}
				}
			}
		}
		return multiplicity;
	}

	
	/**
	 * Name des Subpackages auslesen.
	 * 
	 * @param Class umlClass - Aktuelle UML-Klasse
	 * @return String temp - Name des Subpackages
	 */
	public static String getSubpackage(Class umlClass) {
		String temp = umlClass.getName();
		return temp.substring(7, temp.length());
	}


	/**
	 * Wert des im UML-Modell ausgew�hlten ENUM Werts auslesen.
	 * 
	 * @param String enumString - Kompletter Inhalt des Enum als String
	 * @return String tmp - Tats�chlicher Wert des ENUMS
	 */
	public static String getEnumValue(String enumString) {
		String tmp = null;
		int index1 = enumString.indexOf("name:");
		int index2 = enumString.indexOf(", visibility");
		if (index1 > 0 && index2 > 0)
			tmp = enumString.substring(index1 + 6, index2);
		return tmp;
	}


	/**
	 * Pr�fen, ob es sich um eine abgeleitete Klasse handelt.
	 * 
	 * @param Class umlClass - Aktuelle UML-Klasse
	 * @return boolean flag - <code>true</code> falls es sich um eine abgeleitete Klasse handelt, <code>false</code> falls nicht
	 */
	public static boolean isExtendedClass(Class umlClass) {
		boolean flag = false;
		EList<Class> extendedClasses = umlClass.getSuperClasses();
		for (Class extendedClass : extendedClasses) {
			if (extendedClass.getName() != null)
				flag = true;
			else
				flag = false;
		}
		return flag;
	}


	/**
	 * Pr�fen, ob Klassenname <code>Abstract</code> enth�lt.
	 * 
	 * @param Class umlClass - Aktuelle UML-Klasse
	 * @return boolean flag - <code>true</code> falls es sich um eine abstrakte Klasse handelt, <code>false</code> falls nicht
	 */
	public static boolean isAbstractClass(Class umlClass) {
		boolean flag = false;
		if (umlClass.getName().contains("Abstract"))
			flag = true;
		return flag;
	}


	/**
	 * Pr�fen, ob in der Klassen des Attributs an irgend einem Attribut die
	 * Annotation @Column angewendet wurde.
	 * 
	 * @param Property umlProperty - Aktuelle UML-Property
	 * @return boolean flag - <code>true</code> falls @Column Annotation angewendet wurde, <code>false</code> falls nicht
	 */
	public static boolean checkClassForColumnAnnotation(Property umlProperty) {
		boolean flag = false;
		EList<Property> properties = umlProperty.getClass_().getAllAttributes();
		for (Property property : properties) {
			EList<Stereotype> stereotypes = property.getAppliedStereotypes();
			for (Stereotype stereotype : stereotypes) {
				if (stereotype.getName().equals("JPA_ColumnJPA"))
					flag = true;
			}
		}
		return flag;
	}
	

	/**
	 * Stereotypnamen formatieren. Aus JPA_NameJPA wird JPA_Name.
	 * 
	 * @param String name - Der Stereotypname
	 * @return String - Formatierter Stereotypname
	 */
	public static String formatJpaStereotypeName(String name) {
		return name.substring(4, name.length() - 3);

	}

	/**
	 * Alle Klassen aus dem UML-Modell holen.
	 * 
	 * @param Model umlModel - Komplettes UML-Modell
	 * @return List<Class> classes - Typisierte Liste mit allen UML-Klassen
	 */
	public static List<Class> getAllClasses(Model umlModel) {
		Model dm = umlModel.getModel();
		List<Class> classes = new ArrayList<Class>();
		EList<NamedElement> elms = dm.getMembers();
		for (NamedElement namedElement : elms) {
			EList<Element> elements = namedElement.allOwnedElements();
			for (Element element : elements) {
				if (element instanceof Class) {
					Class impl = (Class) element;
					// System.out.println(impl.getName());
					classes.add(impl);
				}
			}
		}
		return classes;
	}

	/**
	 * Pr�ft, ob Attribut mit dem Namen id bereits existiert.
	 * 
	 * @param Class umlClass - Aktuelle UML-Klasse
	 * @return boolean flag - <code>true</code> falls Attribut existiert, <code>false</code> falls nicht
	 */
	public static boolean searchIdAttributeName(Class umlClass) {
		boolean flag = false;
		EList<Property> attributes = umlClass.getAllAttributes();
		for (Property property : attributes) {
			if (property.getName().equals("id"))
				flag = true;
			else
				flag = false;
		}
		return flag;
	}

	/**
	 * Attributname des mit @Id annotierten Attributs holen.
	 * 
	 * @param Class umlClass - Aktuelle UML-Klasse
	 * @return String name - Name des Attributs
	 */
	public static String getIdAttributeName(Class umlClass) {
		String name = null;
		EList<Property> attributes = umlClass.getAllAttributes();
		for (Property property : attributes) {
			EList<Stereotype> stereotypes = property.getAppliedStereotypes();
			for (Stereotype stereotype : stereotypes) {
				if (stereotype.getName().equals("JPA_IdJPA"))
					name = property.getName();
			}
		}
		return name;
	}

	/**
	 * Attributname des mit @Id annotierten Attributs holen.
	 * 
	 * @param Property umlProperty - Aktuelle UML-Klasse
	 * @return String name - Name des Attributs
	 */
	public static String getIdAttributeName(Property umlProperty) {
		String name = null;
		EList<Property> attributes = umlProperty.getClass_().getAllAttributes();
		for (Property property : attributes) {
			EList<Stereotype> stereotypes = property.getAppliedStereotypes();
			for (Stereotype stereotype : stereotypes) {
				if (stereotype.getName().equals("JPA_IdJPA"))
					name = property.getName();
			}
		}
		return name;
	}

	/**
	 * Attributtyp des mit @Id annotierten Attributs holen.
	 * 
	 * @param Class umlClass - Aktuelle UML-Klasse
	 * @return String name - Name des Attributs
	 */
	public static String getIdAttributeType(Class umlClass) {
		String name = null;
		EList<Property> attributes = umlClass.getAllAttributes();
		for (Property property : attributes) {
			EList<Stereotype> stereotypes = property.getAppliedStereotypes();
			for (Stereotype stereotype : stereotypes) {
				if (stereotype.getName().equals("JPA_IdJPA"))
					name = property.getType().getName().toUpperCase();
			}
		}
		return name;
	}

	/**
	 * Pr�fen, ob in dieser Klasse f�r ein Attribut bereits die Annotation @Id
	 * angewendet wurde.
	 * 
	 * @param Property umlProperty - Aktuelle UML-Property
	 * @return boolean flag - <code>true</code> falls @Id Annotation angewendet wurde, <code>false</code> falls nicht
	 */
	public static boolean isId(Property umlProperty) {
		boolean flag = false;
		EList<Property> attributes = umlProperty.getClass_().getAllAttributes();
		for (Property property : attributes) {
			EList<Stereotype> stereotypes = property.getAppliedStereotypes();
			for (Stereotype stereotype : stereotypes) {
				if (stereotype.getName().equals("JPA_IdJPA"))
					flag = true;
			}
		}
		
		return flag;
	}

	/**
	 * Pr�fen, ob in dieser Klasse f�r ein Attribut bereits die Annotation @Id
	 * angewendet wurde.
	 * 
	 * @param Class umlClass - Aktuelle UML-Property
	 * @return boolean flag - <code>true</code> falls @Id Annotation angewendet wurde, <code>false</code> falls nicht
	 */
	public static boolean isId(Class umlClass) {
		boolean flag = false;
		EList<Property> attributes = umlClass.getAllAttributes();
		for (Property property : attributes) {
			EList<Stereotype> stereotypes = property.getAppliedStereotypes();
			for (Stereotype stereotype : stereotypes) {
				if (stereotype.getName().equals("JPA_IdJPA"))
					flag = true;
			}
		}

		return flag;
	}
	

	/**
	 * Alle Attribute, welche mit @Id annotiert sind, als ArrayList zur�ckgeben.
	 * 
	 * @param Class umlClass - Aktuelle UML-Klasse
	 * @return List<Property> list - Alle mit @Id annotierten Attribute
	 */
	public static List<Property> getIdAttributes(Class umlClass) {
		List<Property> list = new ArrayList<Property>();
		EList<Property> attributes = umlClass.getAllAttributes();
		for (Property property : attributes) {
			EList<Stereotype> stereotypes = property.getAppliedStereotypes();
			for (Stereotype stereotype : stereotypes) {
				if (stereotype.getName().equals("JPA_IdJPA"))
					list.add(property);
			}
		}
		return list;
	}
	
	/**
	 * Alle Attribute, welche mit @Id annotiert sind, als ArrayList zur�ckgeben.
	 * 
	 * @param Property umlProperty - Aktuelle UML-Property
	 * @return List<Property> list - Alle mit @Id annotierten Attribute
	 */
	public static List<Property> getIdAttributes(Property umlProperty) {
		List<Property> list = new ArrayList<Property>();
		EList<Property> attributes = umlProperty.getClass_().getAllAttributes();
		for (Property property : attributes) {
			EList<Stereotype> stereotypes = property.getAppliedStereotypes();
			for (Stereotype stereotype : stereotypes) {
				if (stereotype.getName().equals("JPA_IdJPA"))
					list.add(property);
			}
		}
		return list;
	}
	
	/**
	 * Pr�fen, ob eine Prim�rschl�sselklasse ben�tigt wird.
	 * 
	 * @param Class umlClass - Aktuelle UML-Klasse
	 * @return boolean flag - <code>true</code> falls Prim�rschl�sselklasse ben�tigt wird, <code>false</code> falls nicht
	 */
	public static boolean isPrimaryKeyClassReq(Class umlClass) {
		boolean flag = false;
		int counter = 0;
		EList<Property> attributes = umlClass.getAllAttributes();
		for (Property property : attributes) {
			EList<Stereotype> stereotypes = property.getAppliedStereotypes();
			for (Stereotype stereotype : stereotypes) {
				if (stereotype.getName().equals("JPA_IdJPA"))
					counter++;
			}
		}
		
		if (counter > 1)
			flag = true;
		
		return flag;
	}
	
	/**
	 * Pr�fen, ob eine Prim�rschl�sselklasse ben�tigt wird.
	 * 
	 * @param Class umlClass - Aktuelle UML-Klasse
	 * @return boolean flag - <code>true</code> falls Prim�rschl�sselklasse ben�tigt wird, <code>false</code> falls nicht
	 */
	public static boolean isPrimaryKeyClassReq(Property umlProperty) {
		boolean flag = false;
		int counter = 0;
		EList<Property> attributes = umlProperty.getClass_().getAllAttributes();
		for (Property property : attributes) {
			EList<Stereotype> stereotypes = property.getAppliedStereotypes();
			for (Stereotype stereotype : stereotypes) {
				if (stereotype.getName().equals("JPA_IdJPA"))
					counter++;
			}
		}
		
		if (counter > 1)
			flag = true;
		
		return flag;
	}

	/**
	 * Namen eines Attributs, welcher nicht gesetzt ist, erg�nzen.
	 * 
	 * @param Property
	 *            p - Das aktuelles Attribut.
	 */
	private static void addAttributeName(Property p) {
		p.setName(p.getType().getName().toLowerCase());
	}

	/**
	 * Pr�ft, ob das Attribut zu einer Beziehung geh�rt.
	 * 
	 * @param Class
	 *            umlClass - Die aktuelle UML-Klasse.
	 * @param Property
	 *            attribute - Das zu untersuchende Attribut.
	 * @return boolean classFoundFlag - Falls aktuelle Klasse in
	 *         <code>getRelatedElements()</code> enthalten ist, wird true
	 *         zur�ckgegeben.
	 */
	private static boolean checkAssociationRelatedElements(Class umlClass,
			Property attribute) {
		boolean classFoundFlag = false;
		if (attribute.getAssociation() != null) {
			EList<Association> association = umlClass.getAssociations();
			for (Association associations : association) {
				EList<Property> aoe = associations.getOwnedEnds();
				int i = 0;
				for (Property properties : aoe) {
					if (properties.getAssociation().getRelatedElements().get(i)
							.equals(umlClass)) {
						classFoundFlag = true;
					}
					i++;
				}
			}
			return classFoundFlag;
		} else
			return classFoundFlag;

	}
}