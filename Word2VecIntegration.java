package com.cimb.chatbot.controller;
//package com.spark.mllib.word2vec;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Word2VecIntegration {

	
	public static void main(String[] args) throws Exception {
		setVectorBMap();
	//	Map<Double,String>  unsortedIntentMap =  getVectorMap();
	}
	
	public static int getIntent(Map<Double, String> map) {
        for ( Map.Entry<Double, String> entry : map.entrySet() ) {
          //  System.out.println( "Key : " + entry.getKey() + " Value : " + entry.getValue() );
        }
        
        Map.Entry<Double, String> entry = map.entrySet().iterator().next();
       
        return Integer.parseInt( entry.getValue() );
        
    }



//	public static Map<double[],String> getVectorMap() throws Exception {
		public static void setVectorBMap() throws Exception {
		
		//File outFile = new File("/home/cimb/vectorB"); 
		//FileWriter fw = new FileWriter(outFile);
		
		FileOutputStream fos=new FileOutputStream("/home/cimb/vectorB");
        ObjectOutputStream oos=new ObjectOutputStream(fos);

        
		
		BufferedReader br = null;
//		BufferedWriter bw = new BufferedWriter (fw);
		String line = "";
		StringTokenizer st = null;
		Map<double[], String> vectortMap = new HashMap<double[], String>();
		List<String> lineArray = new ArrayList<String>();
		try{
			br = new BufferedReader(new FileReader("/home/cimb/Phrases_1.csv"));
					
			while ((line = br.readLine()) != null) {
				lineArray.add(line);
			}

			for (int i = 1; i < lineArray.size(); i++) {
				st = new StringTokenizer(lineArray.get(i), ",");
				String[] tokenArray = new String[st.countTokens()];
				int tokens=0;
				
				while(st.hasMoreTokens())
	           		 {
					 String token  = st.nextToken();
					// System.out.println("--token---"+token);
				     	tokenArray[tokens]=token;
	                    		tokens++;
	            		}
				
				String str = tokenArray[1];
				String words[] = str.split(" ");
				/* for(int k=0; k <words.length; k++){
				  System.out.println("--word---"+words[k]);
				 }*/
				
				 double[] VectorB = CIMBWord2Vec.getVector(words).toArray();
				 vectortMap.put(VectorB, tokenArray[2]);
			//     fw.write(VectorB+","+tokenArray[2]+System.getProperty("line.separator"));

			}
			oos.writeObject(vectortMap);
	        oos.close();
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	//	return  vectortMap;
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
