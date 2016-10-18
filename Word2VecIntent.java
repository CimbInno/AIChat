package com.cimb.chatbot.controller;


//import static com.spark.mllib.word2vec.Word2Vec.*;
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
import java.io.FileWriter;
import java.io.InputStream;
import java.io.ObjectOutputStream;

public class Word2VecIntent {

	
	public static int getIntent(Map<Double, String> map) {
        for ( Map.Entry<Double, String> entry : map.entrySet() ) {
            System.out.println( "Key : " + entry.getKey() + " Value : " + entry.getValue() );
        }
        
        Map.Entry<Double, String> entry = map.entrySet().iterator().next();
        return Integer.parseInt( entry.getValue() );
        
    }

	public static Map<Double,String> getIntentMap(double[] vectorA, Map<double[],String> vectorBMap) {
		 
		 Map<Double, String> intentMap = new HashMap<Double, String>();
		 
		 for (Map.Entry<double[], String> entry : vectorBMap.entrySet()) {
		 //    System.out.println("Key = " + entry.getKey().toString() + ", Value = " + entry.getValue());
		     double cosine = cosineSimilarity(vectorA,entry.getKey().clone());
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
