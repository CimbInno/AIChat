package com.cimb.chatbot.text.nlp;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.util.Sequence;

public class AnalyParsing extends AnalyAbstract {

	private InputStream inputStream = null;

	
	
	
	
	
	/* ################## implement ############################ */

	@Override
	protected void start ( ) throws Exception {
		inputStream = new FileInputStream( "/home/cimb/AIChat/AIChat_Source/javaSource/resource/en-parser-chunking.bin" );
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

			
			ParserModel model = new ParserModel( inputStream );
			Parser parser = ParserFactory.create( model );
			
			Parse[] topParses = ParserTool.parseLine( content, parser, 10 );
			
			System.out.println( topParses );
			
			for ( Parse p : topParses ) {
				
				System.out.println( p.getCoveredText( ) );
				System.out.println( p.getHeadIndex( ) );
				System.out.println( p.getLabel( ) );
				System.out.println( p.getProb( ) );
				System.out.println( p.getText( ) );
				System.out.println( p.getType( ) );
				System.out.println( p.getChildren( ).length );
				
				for ( Parse pc : p.getChildren( ) ) {
					System.out.println( " ---- " + pc.getLabel( ) );
				}
				
				System.out.println( " ######################### " );
				
			}

			return null;

		} finally {
			stop( );

		}

	}

	
	
	
	
	
	public static void main ( String[ ] args ) {

		try {

			String text = "The quick brown fox jumps over the lazy dog .";

			System.out.println( ( new AnalyParsing( ) ).convert( text ) );

		} catch ( Exception e ) {
			e.printStackTrace( );
		}

	}

}
