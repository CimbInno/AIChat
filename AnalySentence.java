package com.cimb.chatbot.text.nlp;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

public class AnalySentence extends AnalyAbstract {

	private InputStream inputStream = null;

	/* ################## implement ############################ */

	@Override
	protected void start ( ) throws Exception {
		inputStream = new FileInputStream( "/home/cimb/AIChat/AIChat_Source/javaSource/resource/en-sent.bin" );
	}

	@Override
	protected void stop ( ) {

		try {

			if ( inputStream != null ) inputStream.close( );

		} catch ( Exception ex ) {
		}

	}
	
	
	/* ################## implement - api ############################ */

	public List< String > convert ( String content ) throws Exception {

		try {

			start( );

			SentenceModel model = new SentenceModel( inputStream );
			SentenceDetectorME sentenceDetector = new SentenceDetectorME( model );

			List< String > returnList = new ArrayList< String >( );

			String[ ] sentencesString = sentenceDetector.sentDetect( content );
			for ( String s : sentencesString ) {
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
			
			System.out.println( ( new AnalySentence( ) ).convert( text ) );

		} catch ( Exception e ) {
			e.printStackTrace( );
		}

	}

}
