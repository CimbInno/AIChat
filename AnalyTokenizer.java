package com.cimb.chatbot.text.nlp;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class AnalyTokenizer extends AnalyAbstract {
	

	private InputStream inputStream = null;

	/* ################## implement ############################ */

	@Override
	protected void start ( ) throws Exception {
		inputStream = new FileInputStream( "/home/cimb/AIChat/AIChat_Source/javaSource/resource/en-token.bin" );
	}

	@Override
	protected void stop ( ) {

		try {
			if ( inputStream != null ) inputStream.close( );
		} catch ( Exception ex ) { }

	}
	
	
	/* ################## implement - api ############################ */

	
	public List<String> convert( String content ) throws Exception {
		
		try {
			start ( );
			
			TokenizerModel model = new TokenizerModel( inputStream );
			Tokenizer tokenizer = new TokenizerME( model );

			List<String> returnList = new ArrayList<String>();
			
			String[ ] tokens = tokenizer.tokenize( content );
			for ( String s : tokens ) {
				returnList.add( s );
			}
			
			return returnList;
			
		} finally { 
			stop( );
			
		}
		
	}	
	

	
	
	
	
	

	public static void main ( String[ ] args ) {

		try {
			
			String text = " asdfasdf .asdf asdf d.f as.df.asd.f.asdf.dsa .sdf as...";
			
			System.out.println( ( new AnalyTokenizer( ) ).convert( text ) );

		} catch ( Exception e ) {
			e.printStackTrace( );
		}

	}
	
}
