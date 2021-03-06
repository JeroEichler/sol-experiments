package solexperiments.sample;

import java.util.ArrayList;
import java.util.List;

import solengine.frontier.EngineInterface;
import solengine.model.Vocabulary;
import solexperiments.configuration.Config;

/************************************************************************************************************
 * Crawler to query a single endpoint, in a breadth first manner
 *
 * now with several types of processors
 * **********************************************************************************************************/
public class RetrievalRunner {
	
	static int limit = Config.searchSize();

	public static void main(String[] args) {

		experiment1();

	}

	private static void experiment1() {
		EngineInterface system = new EngineInterface();
		List<String> datasetAddresses = new ArrayList<String>();
		datasetAddresses.add(Vocabulary.DBpediaEndpoint);
		
		long start = System.currentTimeMillis();
		for(int i =0; i<34594; i=i+1){
			
			long startMicro = System.currentTimeMillis();
			
			system.processOnDatasets(queryWithoutOffset2(), datasetAddresses, limit, i);
			
			long elapsedTimeMicro = System.currentTimeMillis() - startMicro;
			long elapsedTimeTotal = System.currentTimeMillis() - start;
			
			System.out.println("--> Passed Through "+i+" records at "+
			elapsedTimeMicro/1000F+" seconds, "+
			elapsedTimeTotal/(60*1000F)+" total. "
			);
		}
		
		long elapsedTime = System.currentTimeMillis() - start;
		
		System.out.println("### Finished at "+elapsedTime/1000F+" seconds");

		
	}

	public static String queryWithoutOffset2(){
		String userQuery = 
	            "SELECT ?subject where {" + 
	                    "	?subject <"+Vocabulary.Rdf_TypeProperty+"> <http://dbpedia.org/ontology/Band> ." + 
	                    "} "
	                    ;
		
		return userQuery;
	}

}
