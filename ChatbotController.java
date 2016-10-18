package com.cimb.chatbot.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.chatbot.model.Account;
import com.cimb.chatbot.model.Country;
import com.cimb.chatbot.model.Customer;
import com.cimb.chatbot.model.History;
import com.cimb.chatbot.model.Transaction;
import com.cimb.chatbot.service.CustomerService;
import com.cimb.chatbot.service.HistoryService;

import antlr.StringUtils;
import com.cimb.chatbot.text.nlp.AnalyPosTagger;
import com.cimb.chatbot.text.nlp.AnalyTokenizer;
//import com.spark.mllib.word2vec.Word2Vec;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;


//import static com.spark.mllib.word2vec.Word2Vec.*;
import java.util.Comparator;
import java.util.HashMap;

@RestController
public class ChatbotController {
	
	@Resource(name="customerService")
	CustomerService  customerService;
	
	@Resource(name="historyService")
	HistoryService  historyService;
	
	@Autowired
	HttpServletRequest request;
	
	/*
	@RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = "application/json")
	public Customer getEmployeeInJSON(@PathVariable String name) {
		System.out.println("---Customer name is----"+name);
		Customer cust = new Customer();
		cust.setName(name);
	    return cust;
	}
	
	*/
	@RequestMapping(value = "/accounts", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Account> getAccounts(@RequestBody Customer cust){//,@RequestBody String input ) {
         
		List<Account> accountList = customerService.getAccounts(cust.getId());
		for(int i=0; i< accountList.size();i++){
			System.out.println("------acc---"+ accountList.get(i).getAccountnumber());
		}
	//	 String json = new Gson().toJson(accountList);
		return accountList;
	}
	
	@RequestMapping(value = "/transfer", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public Transaction transferFunds(@RequestBody Transaction transcation){//,@RequestBody String input ) {
        
		System.out.println("---From  acc----"+transcation.getFrom());
		System.out.println("---TO  acc----"+transcation.getTo());
	    boolean status = customerService.transferAmount(transcation);
		Transaction  tx = new Transaction();
		if(status){
			tx.setCode("200");
		}else{
			tx.setCode("400");
		}
		return tx;
	}
	
	@RequestMapping(value = "/balanceInfo", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public Customer getAccountDetails(@RequestBody Customer cust){//,@RequestBody String input ) {
         
		 System.out.println("---customer query  is----"+cust.getQuery());
		 int intentId=0;
		 String response =null;
		 String vector[] =null;
		 String product =null;
		 Customer custResponse = new Customer();
		 HttpSession session =request.getSession();
		 
		 History history = new History();
		 history.setCustid(cust.getId());
		 history.setQuery(cust.getQuery());
		 
		 String previousVector = null;
		 String currentVector = null;
		 
	
		 int prevIntentId = historyService.getPreviousIntentId();
		 System.out.println("----previous Intent---"+prevIntentId);
		 
		 product= historyService.getProductName();
		 System.out.println("----product name---"+product);
		// if(historyService !=null && prevIntentId <28){
		
		 if(cust.getQuery() ==null || cust.getQuery() =="") {
			 response = "Sorry, this Bot deals with CIMB products Only !!! ";
		 }
		 
		 if(cust.getQuery() !=null ) {
			 vector =removeStopWords(cust.getQuery());
			// intentId=getIntentId(vector);
		//	 System.out.println("----current Intent---"+intentId);
		 
	//	 if(historyService !=null && prevIntentId <28 && intentId <28 && intentId >=0) {
			 custResponse.setCode("1");
			 if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("hi")) response = "Hi there, how can I assist you?";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("hihi")) response = "Hi there, how can I assist you?";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("hi hi")) response = "Hi there, how can I assist you?";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("hi there")) response = "Hi there, how can I assist you?";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("hi are you there")) response = "Hi there, how can I assist you?";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("hey")) response = "Hi there, how can I assist you?";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("hello")) response = "Hello, how can I assist you?";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("hello there")) response = "Hello, how can I assist you?";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("hallo")) response = "Hello, how can I assist you?";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("halo")) response = "Hello, how can I assist you?";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("good day")) response = "Good morning, how can I assist you?";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("good morning")) response = "Good morning, how can I assist you?";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("good afternoon")) response = "Good afternoon, how can I assist you?";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("good evening")) response = "Good evening, how can I assist you?";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("good night")) response = "Good night";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("goodbye")) response = "Thank you and goodbye";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("good bye")) response = "Thank you and goodbye";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("ok")) response = "Is there anything I can help you with?";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("okay")) response = "Is there anything I can help you with?";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("okie")) response = "Is there anything I can help you with?";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("okies")) response = "Is there anything I can help you with?";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("ok then")) response = "Is there anything I can help you with?";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("okay then")) response = "Is there anything I can help you with?";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("bye")) response = "Thank you and goodbye";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("bye bye")) response = "Thank you and goodbye";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("bye now")) response = "Thank you and goodbye";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("thanks")) response = "You are welcome";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("thank you")) response = "You are welcome";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("thnks")) response = "You are welcome";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("tks")) response = "You are welcome";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("noted and thank you")) response = "You are welcome";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("noted with thanks")) response = "You are welcome";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("noted")) response = "Great. Is there anything I can help you with?";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("the end")) response = "Thank you and goodbye";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("end")) response = "Thank you and goodbye";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("end now")) response = "Thank you and goodbye";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("no")) response = "Thank you and goodbye";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("no. that's all")) response = "Thank you and goodbye";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("that's all")) response = "Thank you and goodbye";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("no. not now")) response = "Thank you and goodbye";
			 else if(cust.getQuery().trim().replace("!", "").replace("?", "").replace("#", "").equalsIgnoreCase("yes")) response = "How may I assist you?";
			 else {
				String str = null;
				String resp = "";
				String accFromType =null;
				String accToType =null;
				double amount = 0.00;
				intentId = getIntentId(vector);
				System.out.println("----current Intent---" + intentId);
				//String[] words = vector.replace("c(", "").replace(")","").replace("'", "").split(",");
				  for(String word : vector)
				    {
				        if(word !=null && (word.equalsIgnoreCase("transfer") || word.equalsIgnoreCase("pay")))
				        {
				        	intentId = 36;
				        }
				    }
				if (prevIntentId < 28) {
				//	intentId = getIntentId(vector);
				//	System.out.println("----current Intent---" + intentId);
					if ((intentId == 28 || intentId == 29 ||  intentId == 30 || intentId == 31) && product != null) {
						vector = removeStopWords(cust.getQuery() + " " + product);
						intentId = getIntentId(vector);
						System.out.println("---new intent id is" + intentId);
						custResponse.setCode("1");
						history.setProduct(product);
						resp="I think you are referring to "+product+" and ";
					}else if ((intentId == 28 || intentId == 29 ||  intentId == 30 || intentId == 31) && product == null) {
						custResponse.setCode("2");
					}else if(intentId == 32 || intentId == 33 || intentId == 34){
						history.setProduct(cust.getQuery());
						custResponse.setCode("3");
					}else if(intentId == 36){
						custResponse.setCode("4");
					}else{
						if(intentId >=1 && intentId <=6){
							history.setProduct("kwik account");
						}
						if(intentId >=7 && intentId <=12){
							history.setProduct("airasia account");
						}
						if(intentId >=13 && intentId <=19){
							history.setProduct("Cash Rebate Platinum Master Card Account");
						}
						custResponse.setCode("1");
					}
				}
				if (prevIntentId >= 28) {
					vector = removeStopWords(cust.getQuery());
					String prodName =checkProductName(vector);
					if (intentId == 28 || (intentId == 28 && prevIntentId == 28)) {
						custResponse.setCode("2");
					}
					if (prevIntentId == 28) {
						//vector = removeStopWords(cust.getQuery() + " " + "balance");
						//intentId = getIntentId(vector);
						if(prodName.equals("kwik")) intentId =1;
						if(prodName.equals("airasia")) intentId =7;
						if(prodName.equals("master")) intentId =13;
			
						System.out.println("---new intent id is" + intentId);
						custResponse.setCode("1");
						history.setProduct(cust.getQuery());
					}

					if (intentId == 29 || (intentId == 29 && prevIntentId == 29)) {
						custResponse.setCode("2");
					}
					if (prevIntentId == 29) {
						//vector = removeStopWords(cust.getQuery() + " " + "recent transactions");
						//intentId = getIntentId(vector);
						if(prodName.equals("kwik")) intentId =6;
						if(prodName.equals("airasia")) intentId =12;
						if(prodName.equals("master")) intentId =19;
						
						System.out.println("---new intent id is" + intentId);
						custResponse.setCode("1");
						history.setProduct(cust.getQuery());
					}
					
					if (intentId == 30 || (intentId == 30 && prevIntentId == 30)) {
						custResponse.setCode("2");
					}
					if (prevIntentId == 30) {
					//	vector = removeStopWords(cust.getQuery() + " " + "last transaction");
					//	intentId = getIntentId(vector);
						if(prodName.equals("kwik")) intentId =2;
						if(prodName.equals("airasia")) intentId =8;
						if(prodName.equals("master")) intentId =14;
						
						System.out.println("---new intent id is" + intentId);
						custResponse.setCode("1");
						history.setProduct(cust.getQuery());
					}
					if (intentId == 31 || (intentId == 31 && prevIntentId == 31)) {
						custResponse.setCode("2");
					}
					if (prevIntentId == 31) {
						//vector = removeStopWords(cust.getQuery() + " " + " account number");
						//intentId = getIntentId(vector);
						if(prodName.equals("kwik")) intentId =5;
						if(prodName.equals("airasia")) intentId =11;
						if(prodName.equals("master")) intentId =17;
						
						System.out.println("---new intent id is" + intentId);
						custResponse.setCode("1");
						history.setProduct(cust.getQuery());
					}
					if (intentId == 32 || (intentId == 32 && prevIntentId == 32)) {
						custResponse.setCode("3");
					}
					if (prevIntentId == 32) {
						vector = removeStopWords(cust.getQuery() + " " + product);
						intentId = getIntentId(vector);
						System.out.println("---new intent id is" + intentId);
						custResponse.setCode("1");
						history.setProduct(product);
					}
					if (intentId == 33 || (intentId == 33 && prevIntentId == 33)) {
						custResponse.setCode("3");
					}
					if (prevIntentId == 33) {
						vector = removeStopWords(cust.getQuery() + " " + product);
						intentId = getIntentId(vector);
						System.out.println("---new intent id is" + intentId);
						custResponse.setCode("1");
						history.setProduct(product);
					}
					if (intentId == 34 || (intentId == 34 && prevIntentId == 34)) {
						custResponse.setCode("3");
					}
					if (prevIntentId == 34) {
						vector = removeStopWords(cust.getQuery() + " " + product);
						intentId = getIntentId(vector);
						System.out.println("---new intent id is" + intentId);
						custResponse.setCode("1");
						history.setProduct(product);
					}
				}
				response = resp+""+customerService.getResponse(cust, intentId);
			 }
		 }
		
		 
		// vector =removeStopWords(cust.getQuery());
		 history.setVector(vector.toString());
		// session.setAttribute("vector",vector);
		 history.setIntentid(intentId);
		 historyService.save(history);
		 System.out.println("---History id is"+ history.getId());
		 
		 
		 System.out.println("---response at last is"+ response);
		 

		 history.setResponse(response); 
		 historyService.update(history);
	    
	    custResponse.setId(cust.getId());
	    custResponse.setName(cust.getName());
	    custResponse.setResponse(response);
	    
	    return custResponse;
	}
	
	public String checkProductName(String[] vector){
		String productName =null;
		
	//	String[] words = vector.replace("c(", "").replace(")","").replace("'", "").split(",");
		
		  for(String word : vector)
		    {
		        if(word !=null && word.equalsIgnoreCase("kwik"))
		        {
		        	productName ="kwik";
		        }
		        if(word !=null && word.equalsIgnoreCase("airasia"))
		        {
		        	productName ="airasia";
		        }
		        if(word !=null && (word.equalsIgnoreCase("master") || word.equalsIgnoreCase("credit")))
		        {
		        	productName ="master";
		        }
		    }

		
		return productName;
	}
	
	public String[] removeStopWords(String s ){
		
		HttpSession session =request.getSession();
		
		List< String > number = new ArrayList< String >();
		List< String > conjunction = new ArrayList< String >();
		List< String > verbWord = new ArrayList< String > ();
		List< String > question = new ArrayList< String > ();
		
		try {
			
			System.out.println( "removeStopWords :: [string = " + s + "]" );
			
			
			List< String > tokenizer =  ( new AnalyTokenizer( ) ).convert( s.toLowerCase() );
			Map< String , String > maps = ( new AnalyPosTagger( ) ).convert( tokenizer.toArray( new String[ tokenizer.size( ) ] ) );
			
			Object[] keys = maps.keySet( ).toArray( );
			for ( Object key : keys ) {
				
				String keyValue = key.toString();
				String tagValue = maps.get( key );
				
				if ( tagValue.equals( "NN" ) || tagValue.startsWith( "VB" ) || tagValue.equals( "JJ" ) || tagValue.equals( "NNS" ) || tagValue.equals( "." )) { // verb
					verbWord.add( keyValue.toLowerCase( ).replace("!", "").replace("?", "").replace(".", "") );
					
				}
				
				if ( tagValue.substring( 0 , 1 ).equals( "W" ) ) {  // what , why , when ...
					question.add( keyValue );
					
				}
				
				if ( tagValue.equals( "CC" ) ) { // and , or
					conjunction.add( keyValue );
					
				}
				
				if ( tagValue.equals( "CD" ) ) { // number
					number.add( keyValue );
					session.setAttribute("amount", keyValue);
					
				}
				
				System.out.println( "removeStopWords :: [" + tagValue + " == " + keyValue + "]" );
			}
			
			
		} catch ( Exception ex ) {
			ex.printStackTrace();
		}
	
		List< String > newList = new ArrayList< String >( );
		for ( String vw : verbWord ) {
			newList.add( "'" + vw + "'" );
		}
		
	//	String returnString = org.apache.commons.lang3.StringUtils.join( verbWord ,  "," );
	//	System.out.println( "removeStopWords :: [returnString = " + returnString + "]" );
	//	return returnString;
		
		String nlpString = org.apache.commons.lang3.StringUtils.join( verbWord ,  ",");
		System.out.println( "nlpString :: [nlpString = " + nlpString + "]" );
		
		
		
		
		
		
		
	    String afterStopWords =""; 
	    String stopWordsList = "please,a,about,above,across,after,again,against,all,almost,alone,along,already,also,although,always,am,among,an,and,another,any,anybody,anyone,anything,anywhere,are,area,areas,aren't,around,as,ask,asked,"
	       		+ "asking,asks,at,away,b,back,backed,backing,backs,be,became,because,become,becomes,been,before,began,behind,being,beings,below,best,better,between,big,both,but,by,c,came,can,cannot,can't,case,cases,certain,certainly,clear,clearly,come,could,"
	       		+ "couldn't,d,did,didn't,differ,different,differently,do,does,doesn't,doing,done,don't,down,downed,downing,downs,during,e,each,early,either,end,ended,ending,ends,enough,even,evenly,ever,every,everybody,everyone,everything,everywhere,f,face,faces,"
	       		+ "fact,facts,far,felt,few,find,finds,first,for,four,from,full,fully,further,furthered,furthering,furthers,g,gave,general,generally,get,gets,give,given,gives,go,going,good,goods,got,great,greater,greatest,group,grouped,grouping,groups,h,had,hadn't,has,"
	       		+ "hasn't,have,haven't,having,he,he'd,hello,he'll,her,here,here's,hers,herself,he's,hi,high,higher,highest,him,himself,his,how,however,how's,i,i'd,if,i'll,i'm,important,in,interested,interesting,into,is,isn't,it,its,it's,itself,i've,j,just,k,keep,"
	       		+ "keeps,kind,knew,know,known,knows,l,large,largely,later,least,less,let,lets,let's,like,likely,long,longer,longest,m,made,make,making,man,many,may,me,mean,meaning,meant,member,members,men,might,more,most,mostly,mr,mrs,much,must,mustn't,my,myself,n,necessary,need,needed,"
	       		+ "needing,needs,never,new,newer,newest,next,no,nobody,non,noone,nor,not,nothing,now,nowhere,o,ok,of,off,often,old,older,oldest,on,once,one,only,open,opened,opening,opens,or,order,ordered,ordering,orders,other,others,ought,our,ours,ourselves,out,over,own,"
	       		+ "p,part,parted,parting,parts,per,perhaps,place,places,point,pointed,pointing,points,possible,present,presented,presenting,presents,problem,problems,put,puts,q,quite,r,rather,really,right,room,rooms,s,said,same,saw,say,says,second,seconds,see,seem,seemed,seeming,seems,"
	       		+ "sees,several,shall,shan't,she,she'd,she'll,she's,should,shouldn't,show,showed,showing,shows,side,sides,since,small,smaller,smallest,so,some,somebody,someone,something,somewhere,sorry,state,states,still,such,sure,t,take,taken,tell,than,that,that's,the,their,theirs,them,themselves,then,there,"
	       		+ "therefore,there's,these,they,they'd,they'll,they're,they've,thing,things,think,thinks,this,those,though,thought,thoughts,three,through,thus,to,today,together,told,too,took,toward,turn,turned,turning,turns,two,u,under,until,up,upon,us,use,used,uses,v,very,w,want,wanted,wanting,wants,was,"
	       		+ "wasn't,way,ways,we,we'd,well,we'll,wells,went,were,we're,weren't,we've,what,what's,when,when's,where,where's,whether,which,while,who,whole,whom,who's,whose,why,why's,will,with,within,without,won't,work,worked,working,works,would,wouldn't,x,y,year,years,yes,yet,you,you'd,you'll,young,younger,youngest,your,you're,yours,yourself,yourselves,you've,z";
		   
	        String[] words = nlpString.split(",");
		    ArrayList<String> wordsList = new ArrayList<String>();
		    Set<String> stopWordsSet = new HashSet<String>();
		  	
			String []stopWords =stopWordsList.split(",");
			   for(int i=0;i<stopWords.length;i++){
				   stopWordsSet.add(stopWords[i]);
			   }

		    for(String word : words)
		    {
		        String wordCompare = word.toLowerCase();
		        if(!stopWordsSet.contains(wordCompare) && !wordCompare.equals(""))
		        {
		            wordsList.add(word.toLowerCase());
		        }
		    }

		    for (String str : wordsList){
		    	afterStopWords +=str+","; 
		    }
		    System.out.println("--afterStopWords--"+afterStopWords);
	
	//	return afterStopWords;
	
		    String[] inputWords = afterStopWords.split(",");
			
		/*	String vectorWords = "c(";
			for(String word : inputWords){
			//	System.out.println("--word--"+word);
				vectorWords +="'"+word+"'"+",";
			}
			
			 vectorWords =vectorWords.substring(0, vectorWords.length() - 1);
			 vectorWords +=")";
			 System.out.println("vectorWords----"+vectorWords);*/
	
	//	return vectorWords;
		return inputWords;
	
	}
	
	
	public int getIntentId(String[] vectorWords){

		// RConnection connection = null;
		int intentId = 0;
		try {
			// connection = new RConnection();
			// if(connection.isConnected()) {
			// System.out.println("Connected to RServe successfully.");
			// }
			// connection.eval("source('/home/cimb/Desktop/Test.R')");
			// String intentDesc
			// =connection.eval("myAdd2("+vectorWords+")").asString();
			// intentId = Integer.parseInt(intentDesc);
			intentId = HelloWorld.getIntentId(vectorWords);
			System.out.println("The INTENT Id is=" + intentId);

			/*
			 * } catch (RserveException e) { e.printStackTrace(); } catch
			 * (REXPMismatchException e) { e.printStackTrace();
			 */
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return intentId;
	}
	
	@RequestMapping(value = "/countries", method = RequestMethod.GET,headers="Accept=application/json")
	public List<Country> getAccountDetails()
	{
		List<Country> listOfCountries = new ArrayList<Country>();
		listOfCountries=createCountryList();
		return listOfCountries;
	}
	
	@RequestMapping(value = "/country/{id}", method = RequestMethod.GET,headers="Accept=application/json")
	public Country getCountryById(@PathVariable int id)
	{
		List<Country> listOfCountries = new ArrayList<Country>();
		listOfCountries=createCountryList();
	
		for (Country country: listOfCountries) {
			if(country.getId()==id)
				return country;
		}
		
		return null;
	}
	
	//Utiliy method to create country list.
	public List<Country> createCountryList()
	{
		Country indiaCountry=new Country(1, "India");
		Country chinaCountry=new Country(4, "China");
		Country nepalCountry=new Country(3, "Nepal");
		Country bhutanCountry=new Country(2, "Bhutan");
	
		List<Country> listOfCountries = new ArrayList<Country>();
		listOfCountries.add(indiaCountry);
		listOfCountries.add(chinaCountry);
		listOfCountries.add(nepalCountry);
		listOfCountries.add(bhutanCountry);
		return listOfCountries;
	}
	
	/*public RConnection getRConnection(){
		
		RConnection connection = null;
		try {
	            connection = new RConnection();
				if(connection.isConnected()) {
		            System.out.println("Connected to RServe successfully.");
		        }
		 } catch (RserveException e) {
	            e.printStackTrace();
	     }
		return connection;
	}*/


}
