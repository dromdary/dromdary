/**
 * Copyright (c) 2009-2012 by droMDAry.org (see CONTRIBUTORS)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.dromdary.jpa.generator.helper;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Relationship;
import org.eclipse.uml2.uml.Stereotype;
import org.hibernate.type.PrimitiveType;

public class EntityHelper {
	static String RELATION_TYPE_COMPOSITE = "composite";
	static String RELATION_TYPE_ASSOCIATION = "association";
	static String RELATION_TYPE_AGGREGATION = "aggregation";
	static String RELATION_ONE_TO_ONE = "OneToOne";
	static String RELATION_ONE_TO_MANY = "OneToMany";
	static String RELATION_MANY_TO_ONE = "ManyToOne";
	static String RELATION_MANY_TO_MANY = "ManyToMany";
	static String XMI_ATTR_CLASS_IMPL = "ClassImpl";
	static String XMI_ATTR_ENUM_NAME = "name:";
	static String XMI_ATTR_ENUM_VISIBILITY = ", visibility";
	static String XMI_ATTR_ABSTRACT_CLASS = "Abstract";
	static String XMI_ATTR_STEREOTYPE_JPA_COLUMN = "JPA_ColumnJPA";
	static String XMI_ATTR_STEREOTYPE_JPA_ID = "JPA_IdJPA";
	static String XMI_ATTR_ID = "Id";
	
	private static final int DEFAULT_STRING_DB_SIZE = 20;
	private static final int MAX_CHAR_SIZE = 50;
	
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
					XMI_ATTR_CLASS_IMPL)) {
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
						multiplicity = RELATION_MANY_TO_ONE;
					}
					if (umlProperty.getOtherEnd().getUpper() == 1) {
						multiplicity = RELATION_ONE_TO_ONE;
					}
				} else if (umlProperty.getUpper() == -1) {
					if (umlProperty.getOtherEnd().getUpper() == 1) {
						multiplicity = RELATION_ONE_TO_MANY;
					} else if (umlProperty.getOtherEnd().getUpper() == -1) {
						multiplicity = RELATION_MANY_TO_MANY;
					}
				}
			}
		}
		return multiplicity;
	}
	
	public static String getPropertyTypeName(Property umlProperty) {
		String multiplicity = getPropertyMultiplicity(umlProperty);
		String name = umlProperty.getType().getName();
		return getPropertyTypeNameByMultiplicity(name, multiplicity);
	}
	
	public static String getPropertyTypeInterfaceName(Property umlProperty) {
		String multiplicity = getPropertyMultiplicity(umlProperty);
		String name = NameHelper.interfaceName(umlProperty.getType().getName());
		return getPropertyTypeNameByMultiplicity(name, multiplicity);
	}
	
	private static String getPropertyTypeNameByMultiplicity(String name, String multiplicity) {
		if (multiplicity == null || !multiplicity.contains("ToMany"))
		{
			return name;
		}
		else 
		{
			return "Set<" + name + ">";
		}
	}
	
	public static boolean existsAttributeNameInClass(Property umlProperty) {
		boolean existsAttributeNameInClass = false;
		int count = 0;
		EList<Property> attributes = umlProperty.getClass_().getAllAttributes();
		for (Property property : attributes) {
			if (property.getName().equals(umlProperty.getName())) {
				count++;
			}
		}
		if (count > 1) {
			existsAttributeNameInClass = true;
		}
		return existsAttributeNameInClass;
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
						multiplicity = RELATION_MANY_TO_ONE;
					}
					if (umlProperty.getUpper() == 1) {
						multiplicity = RELATION_ONE_TO_ONE;
					}
				} else if (umlProperty.getOtherEnd().getUpper() == -1) {
					if (umlProperty.getUpper() == 1) {
						multiplicity = RELATION_ONE_TO_MANY;
					} else if (umlProperty.getUpper() == -1) {
						multiplicity = RELATION_MANY_TO_MANY;
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
		int index1 = enumString.indexOf(XMI_ATTR_ENUM_NAME);
		int index2 = enumString.indexOf(XMI_ATTR_ENUM_VISIBILITY);
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
		if (umlClass.getName().contains(XMI_ATTR_ABSTRACT_CLASS))
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
				if (stereotype.getName().equals(XMI_ATTR_STEREOTYPE_JPA_COLUMN))
					flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * Checks, if the given property is an entity, i.e. if the JPA_Entity stereotype is applied.
	 * 
	 * @param umlProperty The property to check.
	 * @return boolean flag - <code>true</code>, if the given property is an entity, <code>false</code> otherwise.
	 */
	public static boolean isEntity(Property umlProperty) {
		return umlProperty.getType().getAppliedStereotype("SimpleJSR220::JPA_Entity") != null;
	}
	
	/**
	 * Checks, if the given property is an enumeration.
	 * 
	 * @param umlProperty The property to check.
	 * @return boolean flag - <code>true</code>, if the given property is an enumeration, <code>false</code> otherwise.
	 */
	public static boolean isEnum(Property umlProperty) {
		return umlProperty.getType() instanceof Enumeration;
	}

	/**
	 * Stereotypnamen formatieren. Aus JPA_NameJPA wird JPA_Name.
	 * 
	 * @param String name - Der Stereotypname
	 * @return String - Formatierter Stereotypname
	 */
	public static String formatJpaStereotypeName(String name) {
		String stereotypeName = null;
		if(name.indexOf("JPA", name.length() - 3) > 0)
		{
			stereotypeName = removeJpaStereotypePrefix(name.substring(4, name.length() - 3));
		}
		else
		{
			System.out.println(name);
			stereotypeName = removeJpaStereotypePrefix(name);
		}
		
		return stereotypeName;
	}
	
	private static String removeJpaStereotypePrefix(String name) {
		if(name.contains("JPA_"))
		{
			return name.substring(4);
		}
		else
		{
			return name;
		}
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
			if (property.getName().equals(XMI_ATTR_ID))
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
				if (stereotype.getName().equals(XMI_ATTR_STEREOTYPE_JPA_ID))
					name = property.getName();
			}
		}
		return name;
	}

	/**
	 * Get the name of the @Id annotated attribute.
	 * 
	 * @param Property umlProperty - Current UML class
	 * @return String name - name of the Id-attribute
	 */
	public static String getIdAttributeName(Property umlProperty) {
		String name = "";
		for (Element e : umlProperty.getType().getOwnedElements()) {
			for (Stereotype stereotype : e.getAppliedStereotypes()) {
				if (stereotype.getName().equals(XMI_ATTR_STEREOTYPE_JPA_ID)) {
					if (e instanceof Property) {
						name = ((Property)e).getName(); 
					}
				}
			}
		}
		for (Relationship rel : umlProperty.getType().getRelationships()) {
			if (rel instanceof Generalization) {
				for (Element e : ((Generalization) rel).getGeneral().getOwnedElements()) {
					for (Stereotype stereotype : e.getAppliedStereotypes()) {
						if (stereotype.getName().equals(XMI_ATTR_STEREOTYPE_JPA_ID)) {
							if (e instanceof Property) {
								name = ((Property)e).getName(); 
							}
						}
					}
				}
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
				if (stereotype.getName().equals(XMI_ATTR_STEREOTYPE_JPA_ID))
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
				if (stereotype.getName().equals(XMI_ATTR_STEREOTYPE_JPA_ID))
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
				if (stereotype.getName().equals(XMI_ATTR_STEREOTYPE_JPA_ID))
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
				if (stereotype.getName().equals(XMI_ATTR_STEREOTYPE_JPA_ID))
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
				if (stereotype.getName().equals(XMI_ATTR_STEREOTYPE_JPA_ID))
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
				if (stereotype.getName().equals(XMI_ATTR_STEREOTYPE_JPA_ID))
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
				if (stereotype.getName().equals(XMI_ATTR_STEREOTYPE_JPA_ID))
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
	 * Checks, if the attribute is part of a association.
	 * 
	 * @param Class
	 *            umlClass - The current UML class.
	 * @param Property
	 *            attribute - The attribute to check.
	 * @return boolean classFoundFlag - Returns true, if the current class 
	 *         is included in <code>getRelatedElements()</code>.
	 */
	public static boolean checkAssociationRelatedElements(Class umlClass,
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
	
	/**
	 * Get a complete list of the packages of the current UML diagram.
	 * 
	 * @param pkg
	 * 			Any package of the current UML diagram.
	 * @return List<Package> packageList - A complete list of the packages of the current UML diagram.
	 */
	public static List<Package> getAllPackages(Package pkg) {
		Package rootPkg = findRootPackage(pkg);
		List<Package> pkgList = new ArrayList<Package>();
		pkgList.add(rootPkg);
		pkgList.addAll(getAllSubPackages(rootPkg));
		return pkgList;
	}
	
	private static List<Package> getAllSubPackages(Package pkg) {
		List<Package> subPkgs = new ArrayList<Package>();
		for (Package subPkg : pkg.getNestedPackages()) {
			subPkgs.add(subPkg);
			subPkgs.addAll(getAllSubPackages(subPkg));
		}
		return subPkgs;
	}
	
	private static Package findRootPackage(Package pkg) {
		Package rootPkg = pkg;
		while (rootPkg.allOwningPackages().size() > 1) {
			rootPkg = rootPkg.getNestingPackage();
		}
		return rootPkg;
	}
	
	public static boolean isGetterOrSetterForExistingProperty(Operation op, List<Property> properties) {
		if (!op.getName().startsWith("get") && !op.getName().startsWith("set")) {
			return false;
		}
		
		String attributeName = op.getName().substring(3, 4).toLowerCase() + op.getName().substring(4);
		
		boolean attributeFound = false;
		for (Property p : properties) {
			if (p.getName().equals(attributeName)) {
				attributeFound = true;
				break;
			}
		}
		
		return attributeFound;
	}
	
	public static String getCustomColumnDefinitionString(Property property) {
		String columnDefinitionString = "";
		if (property.getType().getName().equals("String")) {
			int minSize = 0;
			int maxSize = DEFAULT_STRING_DB_SIZE;
			if (property.getAppliedStereotype("SimpleJSR303::Size") != null) {
				if (property.getValue(property.getApplicableStereotype("SimpleJSR303::Size"), "min") != null) {
					minSize = Integer.parseInt(property.getValue(property.getApplicableStereotype("SimpleJSR303::Size"), "min").toString());
				}
				if (property.getValue(property.getApplicableStereotype("SimpleJSR303::Size"), "max") != null) {
					maxSize = Integer.parseInt(property.getValue(property.getApplicableStereotype("SimpleJSR303::Size"), "max").toString());
				}
			}
			String typeString = "CHAR";
			if (minSize != maxSize && maxSize > MAX_CHAR_SIZE) {
				typeString = "VARCHAR";
			}
			columnDefinitionString = ", columnDefinition=\"" + typeString + "("+ maxSize + ")\"";
		}
		return columnDefinitionString;
	}
}