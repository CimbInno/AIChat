package com.cimb.chatbot.controller;

import java.util.Map;
import java.util.TreeMap;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Comparator;
import java.util.HashMap;


public class HelloWorld {
	
//	public static void main(String[] args) throws Exception {
		public static int getIntentId (String[] args) throws Exception {
	        System.out.println("Java and Scala together");
	 		
		   	double[] vectorA = new double[50];
		   	vectorA	= CIMBWord2Vec.getVector( args ).toArray();
		    System.out.println("--------vectorA---------"+vectorA.clone());
		   	Map<double[],String>  vectorBMap = getVectorMap();
		   	Map<Double,String>  unsortedIntentMap = getIntentMap( vectorA.clone() , vectorBMap );
				
			Map<Double, String> treeMap = new TreeMap<Double, String>(
				new Comparator<Double>() {
					public int compare(Double o1, Double o2) {
						return o2.compareTo(o1);
			      	}

			 	}
			 );

			treeMap.putAll( unsortedIntentMap );
			int intentId = Word2VecIntegration.getIntent( treeMap );
		    System.out.println("---intentId----" + intentId);
		    return intentId;
	}
		
		
		public static Map<double[], String> getVectorMap() throws Exception {
			FileInputStream fis = null;
			ObjectInputStream ois = null;
			Map<double[], String> vectorBMap = null;
			try{
				fis = new FileInputStream("/home/cimb/vectorB");
				ois = new ObjectInputStream(fis);
				vectorBMap = (Map<double[], String>)ois.readObject();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				ois.close();
				fis.close();
			}
			return vectorBMap;
		}

		public static Map<Double,String> getIntentMap(double[] vectorA, Map<double[],String> vectorBMap) {
			 
			 Map<Double, String> intentMap = new HashMap<Double, String>();
			 
			 for (Map.Entry<double[], String> entry : vectorBMap.entrySet()) {
			   //  System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			     double cosine = cosineSimilarity(vectorA,entry.getKey());
			     intentMap.put(cosine, entry.getValue());
			 }
			 
			
			 return intentMap;
		}
		
		
		public static double cosineSimilarity(double[] vectorA, double[] vectorB) {
			    double dotProduct = 0.0;
			    double normA = 0.0;
			    double normB = 0.0;
			    for (int i = 0; i < vectorA.length; i++) {
				dotProduct += vectorA[i] * vectorB[i];
				normA += Math.pow(vectorA[i], 2);
				normB += Math.pow(vectorB[i], 2);
			    }   
			    return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
		}
	
}
