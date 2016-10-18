package com.cimb.chatbot.text.spell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.languagetool.JLanguageTool;
import org.languagetool.language.BritishEnglish;
import org.languagetool.rules.RuleMatch;

public class TxtCheckSpell extends TxtAbstract {
	
	private BritishEnglish britishEnglish;
	
	
	
	/* ################## implement ############################ */
	
	@Override
	protected void start() throws Exception {
		britishEnglish = new BritishEnglish( );
	}
	
	@Override
	protected void stop() {
		
		try {
			britishEnglish.close( );
		} catch ( Exception e ) { }
		
	}
	
	
	
	
	/* ################## implement - api ############################ */
	
	public Map< String , List<String> > check( String content ) throws Exception {
		
		try {
			
			start();
			
			Map< String, List<String> > mapList = new HashMap< String , List<String> >();
			
			JLanguageTool langTool = new JLanguageTool( britishEnglish );
			List< RuleMatch > matches = langTool.check( content );
			for ( RuleMatch match : matches ) {
				
				List< String > suggestion = new ArrayList< String >(); 
				
				for ( String s : match.getSuggestedReplacements( ) ) {
					suggestion.add( s );
				}
				
				mapList.put( content.substring( match.getFromPos( ) , match.getToPos( ) ) , suggestion );
				
			}
			
			return mapList;
			
		} finally {
			stop();
			
		}
		
	}
	
	
	
	

	
	
	
	
	
	public static void main( String[] args ) {
		
		try {
			
			System.out.println( ( new TxtCheckSpell() ).check( "A sentence fuck with a error in the Hitchhiker's Guide tot he Galaxy" ) );

		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
	}




}

