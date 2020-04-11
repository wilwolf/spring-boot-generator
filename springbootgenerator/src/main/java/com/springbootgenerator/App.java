package com.springbootgenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;

public class App {
	//Source model {model.java} folder of proyect
	private final static String sourceFolder = "C:/generadosspring/";
	//Directory Structure of proyect
	private final static String mainPackage = "com/nsp20be/spring";
	//
	private static List<String> entityNames = new ArrayList<String>();
	private static List<String> entityTypeId = new ArrayList<String>();
	private static List<String> entityNameId = new ArrayList<String>();
	
	public static void main(String[] args) {
		System.out.println("::Iniciando el proceso ::");
		// Uncomment this line for adding manuel model name
		//entityNames.add("Departamentos");		
		
		// Use this method for auto parsing model classes
		parseEntityNamesFromFolder("model");
		parseEntities();
	}

	private static void parseEntityNamesFromFolder(String modelFolder) {
		try {
			File dir = new File(sourceFolder.concat(mainPackage).concat("/" + modelFolder));
			File[] directoryListing = dir.listFiles();
			System.out.println(dir+" Direccion::::");
			
			if (directoryListing != null) {
				for (int i = 0; i < directoryListing.length; i++) {
					File child = directoryListing[i];
					String temp = child.getName().replace(".java", "");
					FileReader f = new FileReader(sourceFolder.concat(mainPackage).concat("/model/")+child.getName());
					BufferedReader b = new BufferedReader(f);
					String cadena;
					System.out.println("********************************");
					System.out.println("--->initial for entity:::"+temp);
					int ctr = 0; 
					while((cadena = b.readLine())!=null) {
			            if(cadena.contains("private")){ctr++;
			            	if(ctr==2) {
			            		
			            		String parsecad = cadena.trim();
			            		String [] cadena2 = parsecad.split(" ");	
			            		//String NameId = cadena2[2].substring(0, 1).toUpperCase() + cadena2[7].substring(1) ;
			            		String NameId = cadena2[2].toUpperCase().charAt(0) + cadena2[2].substring(1, cadena2[2].length());
			            		entityTypeId.add(cadena2[1]);
			            		entityNameId.add(NameId);
			            		System.out.println(NameId+"-->Name");
			            		System.out.println(cadena2[1]+"-->1");
			            		System.out.println(cadena2[2]+"-->2");			            		
			            	}
			            }
						//System.out.println(cadena);
			        }
			        b.close();
					entityNames.add(temp);
				}

			} else {
				System.out.println("Error directorio vacio!!!");
				// Handle the case where dir is not really a directory.
				// Checking dir.isDirectory() above would not be sufficient
				// to avoid race conditions with another process that deletes
				// directories.
			}
		} catch (Exception e) {
			System.out.println("Error-->"+e);
			e.printStackTrace();
		}
	}

	private static void parseEntities() {
		int cant=0;
		int dato=0;
		for (String entityName : entityNames) {
			String entityIdType =  entityTypeId.get(dato);
			String entityIdName = entityNameId.get(dato);
			System.out.println("********************************");
			System.out.println("Creando files for entity:::"+entityName);
			// Rest Service
			String serviceTemplate = Templates.getServiceTemplate(mainPackage, entityName, entityIdType, entityIdName);
			createFile("rest", entityName + "Service.java", serviceTemplate);
			cant++;
			System.out.println("***************Create services");
			// Dao Object
			String daoTemplate = Templates.getDaoTemplate(mainPackage, entityName, entityIdType);
			createFile("dao", entityName + "Dao.java", daoTemplate);
			cant++;
			System.out.println("***************Create daos");
			// Repository
			String repositoryTemplate = Templates.getRepositoryTemplate(mainPackage, entityName, entityIdType);
			createFile("repositories", entityName + "Repository.java", repositoryTemplate);
			cant++;
			System.out.println("***************Create repository");
			// ListItem
			String listTemplate = Templates.getListTemplate(mainPackage, entityName, entityIdName);
			//System.out.print(listTemplate);
			createFile("listitem", entityName + "ListItem.java", listTemplate);
			cant++;
			System.out.println("***************Create listItems");
			dato++;
		}
		System.out.println("Thank you for use A&A Rest Generated Code V 1.0");
		System.out.println("Generated {"+cant+"} files");
		System.out.println("Dev. Grover Wilson Quisbert Ibañez");
	}	

	private static void createFile(String folderName, String fileName, String fileTemplate) {
		PrintWriter writer;

		try {
			writer = new PrintWriter(sourceFolder + mainPackage + "/" + folderName + "/" + fileName, "UTF-8");
			writer.print(fileTemplate);
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
