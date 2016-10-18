package com.cimb.chatbot.text.nlp;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

public class AnalyPosTagger extends AnalyAbstract {

	private InputStream inputStream = null;
	
	
	
	
	/* ################## implement ############################ */
	
	@Override
	protected void start ( ) throws Exception {
		inputStream = new FileInputStream( "/home/cimb/AIChat/AIChat_Source/javaSource/resource/en-pos-maxent.bin" );
	}

	@Override
	protected void stop ( ) {
		
		try {
			
			if ( inputStream != null ) 
				inputStream.close( );
			
		} catch ( Exception ex ) { }
		
	}
	
	
	
	
	
	/* ################## implement - api ############################ */
	
	public Map< String, String > convert ( String[] value ) throws Exception {

		try {
			
			start ( );
			
			POSModel model = new POSModel( inputStream );
			POSTaggerME tagger = new POSTaggerME( model );
			String[ ] tags = tagger.tag( value );

			Map< String , String > mapReturn = new HashMap< String , String >( );
			for ( int i = 0; i < value.length; i++ ) {
				if ( i < tags.length ) {
					mapReturn.put( value[ i ] , tags[ i ] );
				}
			}

			return mapReturn;
			
		} finally {
			stop ( );
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main( String[] args ) {
		
		try {
			
			System.out.println( ( new AnalyPosTagger() ).convert( new String[] { "," , "john" }  ) );

		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
	}



}
