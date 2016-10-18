package com.cimb.chatbot.text.nlp;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.util.Sequence;

public class AnalyChunker extends AnalyAbstract {

	private InputStream inputStream = null;

	
	
	
	
	
	/* ################## implement ############################ */

	@Override
	protected void start ( ) throws Exception {
		inputStream = new FileInputStream( "/home/cimb/AIChat/AIChat_Source/javaSource/resource/en-chunker.bin" );
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

			ChunkerModel model = new ChunkerModel( inputStream );
			ChunkerME chunker = new ChunkerME( model );

			String sent[] = new String[ ] { "Rockwell" , "International" , "Corp." , "'s" , "Tulsa" , "unit" , "said" , "it" , "signed" , "a" , "tentative" , "agreement" , "extending" , "its" , "contract" , "with" , "Boeing" , "Co." , "to" , "provide" , "structural" , "parts" , "for" , "Boeing" , "'s" , "747" , "jetliners" , "." };
			String pos[] = new String[ ] { "NNP" , "NNP" , "NNP" , "POS" , "NNP" , "NN" , "VBD" , "PRP" , "VBD" , "DT" , "JJ" , "NN" , "VBG" , "PRP$" , "NN" , "IN" , "NNP" , "NNP" , "TO" , "VB" , "JJ" , "NNS" , "IN" , "NNP" , "POS" , "CD" , "NNS" , "." };
			
			Sequence[] topSequences = chunker.topKSequences( sent, pos );
			for ( Sequence s : topSequences ) {
				for ( String ss : s.getOutcomes( ) ) {
					System.out.println( ss );
				}
			}
			
			return null;

		} finally {
			stop( );

		}

	}

	
	
	
	
	
	public static void main ( String[ ] args ) {

		try {

			String text = " asdfasdf .asdf asdf d.f as.df.asd.f.asdf.dsa .sdf as...";

			System.out.println( ( new AnalyChunker( ) ).convert( text ) );

		} catch ( Exception e ) {
			e.printStackTrace( );
		}

	}

}
