package com.cimb.chatbot.text.speech;

import simplenlg.features.Feature;
import simplenlg.features.Tense;
import simplenlg.framework.NLGElement;
import simplenlg.framework.NLGFactory;
import simplenlg.lexicon.Lexicon;
import simplenlg.lexicon.XMLLexicon;
import simplenlg.phrasespec.NPPhraseSpec;
import simplenlg.phrasespec.SPhraseSpec;
import simplenlg.phrasespec.VPPhraseSpec;
import simplenlg.realiser.english.Realiser;

public class SpeechCore extends SpeechAbstract {

	
	private Lexicon lexicon;
	
	
	
	
	
	/* ################## implement ############################ */
	
	@Override
	protected void start ( ) throws Exception {
	//	lexicon = new XMLLexicon( "/Users/AceattSdnBhd/_Project/OnView/AIChat/Framework/googlenlp/simplenlg-v442/res/default-lexicon.xml" );
		lexicon = new XMLLexicon( "/home/cimb/workspace/cimbot/src/main/resources/default-lexicon.xml" );

		
	}

	@Override
	protected void stop ( ) {
		lexicon.close( );
	}

	
	
	public static void main ( String[ ] args ) {
		SpeechCore sc = new SpeechCore();
		String output =null;
		try{
			output =sc.normal("Mary","chase","the monkey");
			System.out.println( output );
		
		}catch(Exception ex){
			System.out.println("----Exception occured---"+ex.getMessage());
		}
       
		/*Lexicon lexicon = new XMLLexicon( "/home/cimb/AIChat/AIChat_SourcejavaSource/resource/default-lexicon.xml" );
		NLGFactory nlgFactory = new NLGFactory( lexicon );

		Realiser realiser = new Realiser( lexicon );

		NPPhraseSpec subject = nlgFactory.createNounPhrase( "Mary" );
		
		NPPhraseSpec object = nlgFactory.createNounPhrase( "the monkey" );
		
		VPPhraseSpec verb = nlgFactory.createVerbPhrase( "chase" );

		
		
		SPhraseSpec p = nlgFactory.createClause( );
		p.setFeature( Feature.FORM , Tense.PAST );
		p.setSubject( subject );
		p.setVerb( verb );
		p.setObject( object );

		String output = realiser.realiseSentence( p );
		System.out.println( output );

		
		lexicon.close( );*/
		
	}
	
	
	/* ################## implement - api ############################ */
	
	public String normal( String subject , String verb , String object ) throws Exception {
	
		try {
			
			start();
			
			Realiser realiser = new Realiser( lexicon );
			NLGFactory nlgFactory = new NLGFactory( lexicon );
			
			NPPhraseSpec npSubject = nlgFactory.createNounPhrase( subject );
			NPPhraseSpec npObject = nlgFactory.createNounPhrase( verb );
			VPPhraseSpec npVerb = nlgFactory.createVerbPhrase( object );
			
			SPhraseSpec p = nlgFactory.createClause( );
			p.setFeature( Feature.TENSE , Tense.PRESENT );
			p.setSubject( npSubject );
			p.setVerb( npObject );
			p.setObject( npVerb );
			
			return realiser.realiseSentence( p );
			
		} finally {
			stop();
		}
		
	}
	
	public String question( String subject , String verb , String object ) throws Exception {

		try {
			
			start();
			
			Realiser realiser = new Realiser( lexicon );
			NLGFactory nlgFactory = new NLGFactory( lexicon );
			
			NPPhraseSpec npSubject = nlgFactory.createNounPhrase( subject );
			NPPhraseSpec npObject = nlgFactory.createNounPhrase( verb );
			VPPhraseSpec npVerb = nlgFactory.createVerbPhrase( object );
			
			SPhraseSpec p = nlgFactory.createClause( );
			p.setFeature( Feature.INTERROGATIVE_TYPE , Tense.PRESENT );
			p.setSubject( npSubject );
			p.setVerb( npObject );
			p.setObject( npVerb );
			
			return realiser.realiseSentence( p );
			
		} finally {
			stop();
		}
		
	}
		
		
		
		
	
	
	
	
	
	
	
}
