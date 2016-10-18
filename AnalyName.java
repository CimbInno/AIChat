package com.cimb.chatbot.text.nlp;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

public class AnalyName extends AnalyAbstract {

	private InputStream inputStream = null;

	/* ################## implement ############################ */

	@Override
	protected void start ( ) throws Exception {
		inputStream = new FileInputStream( "/home/cimb/AIChat/AIChat_Source/javaSource/resource/en-ner-person.bin" );
	}

	@Override
	protected void stop ( ) {

		try {

			if ( inputStream != null ) inputStream.close( );

		} catch ( Exception ex ) {
		}

	}

	/* ################## implement - api ############################ */

	public List< String > convert ( String[ ] sentence ) throws Exception {

		try {

			start( );

			TokenNameFinderModel model = new TokenNameFinderModel( inputStream );
			NameFinderME nameFinder = new NameFinderME( model );

			List< String > nameReturn = new ArrayList< String >( );

			Span[ ] nameSpans = nameFinder.find( sentence );
			for ( Span s : nameSpans ) {
				String name = "";
				for ( int i = 0; i < sentence.length; i++ ) {
					if ( i >= s.getStart( ) && i < s.getEnd( ) ) {
						name += sentence[ i ] + " ";
					}
				}
				nameReturn.add( name.trim( ) );
			}

			return nameReturn;

		} finally {
			stop( );
		}

	}

	public static void main ( String[ ] args ) {

		try {

			System.out.println( ( new AnalyName( ) ).convert( new String[ ] { "Pierre" , "Vinken" , "is" , "61" , "years" , "old" , "." } ) );

		} catch ( Exception e ) {
			e.printStackTrace( );
		}

	}

}
