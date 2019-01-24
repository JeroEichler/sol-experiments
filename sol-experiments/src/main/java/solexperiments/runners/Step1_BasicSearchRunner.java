package solexperiments.runners;

import java.util.ArrayList;
import java.util.List;

import solengine.model.Vocabulary;
import solexperiments.storage.ResultStorage;

public class Step1_BasicSearchRunner extends AbstractRunner {

	public static void main(String[] args) {
		performBasicSearch();
	}
	
	public static void performBasicSearch() {
		theOneThatStoresBasicResults();
		theOneThatReadsBasicResults();

	}

	private static void theOneThatStoresBasicResults() {
		
		List<List<String>> finalResult = new ArrayList<List<String>>();
		
		long start = System.currentTimeMillis();
		
		for(int i=0; i<10000; i=i+10000) {
			List<List<String>> result = system.ordinaryProcess(makeQuery(i), datasetAddresses);
			finalResult.addAll(result);
			System.out.println(finalResult.size()+"=====================================>");
		}
			
		System.out.println("\n\n"+finalResult.size()+"--------------------------------->");
		ResultStorage.saveEntity(baseFolder, baseListFile, finalResult);
			
		long elapsedTime = System.currentTimeMillis() - start;
		
		System.out.println("### Finished at "+elapsedTime/1000F+" seconds");
		
	}
	
	protected static String makeQuery(int offset) {
		String type = "http://dbpedia.org/ontology/Band";
		if(queryOn.equals("m-artist")) {
			type = "http://dbpedia.org/ontology/MusicalArtist";
		}
		else if(queryOn.equals("movie")) {
			type = "http://dbpedia.org/ontology/Film";
		}
		String userQuery = 
	            "SELECT ?subject where {" + 
	                    "	?subject <"+Vocabulary.Rdf_TypeProperty+"> <" + type + "> ." + 
	                    "} " + 
	                    "LIMIT 10000 " + 
	                    "OFFSET " + offset + " "	                    
	                    ;
		
		return userQuery;
	}

	
	private static void theOneThatReadsBasicResults() {
		List<List<String>> listR = ResultStorage.readListOfLists(baseFolder, baseListFile);
		for(List<String> r: listR) {
			if(r.contains("http://dbpedia.org/resource/The_Beatles"))
				System.out.println(r +"  "+ r.size());
		}
		System.out.println(listR.size());
		
	}
}
