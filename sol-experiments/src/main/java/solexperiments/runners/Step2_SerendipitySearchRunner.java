package solexperiments.runners;

import java.util.List;

import solengine.configuration.Config;
import solengine.configuration.QESystemConfiguration;
import solengine.model.QueryResponse;
import solengine.utils.StringFormatter;
import solexperiments.storage.ResultStorage;

public class Step2_SerendipitySearchRunner extends AbstractRunner {

	public static void main(String[] args) {
		performSerendipitySearch();
	}


	public static void performSerendipitySearch() {
		
		Config.qeConfiguration = new QESystemConfiguration(configNumber);
		if(modeOn.equals("limited")) {
			Config.setLimit(1);
		}
		else {
			Config.setLimit(2);
			Config.qeLimited = false;
		}
		
		System.out.println("Here goes " + baseFolder + " under " + configNumber + "configuration. ");
		
		long start = System.currentTimeMillis();
		retrieveSerendipResponse();
		
		long elapsedTime = System.currentTimeMillis() - start;
		
		System.out.println("### Finished at "+elapsedTime/1000F+" seconds");
		System.out.println("Here lies " + baseFolder);
	}
	
	private static void retrieveSerendipResponse() {
		List<List<String>> baseResults = ResultStorage.readListOfLists(baseFolder, baseListFile);
		System.out.println("starting with "+baseResults.size());
		
		List<QueryResponse> responses = system.findResponses(baseResults, datasetAddresses);
		
		for(QueryResponse response : responses) {
			persistResponse(response);
		}
	}
	
	private static void persistResponse(QueryResponse response) {
		
		String title = StringFormatter.clean(response.getResult());
		if(response.isValid()) {
			ResultStorage.saveSingleResult(baseFolder, title, response);
			ResultStorage.updateList(baseFolder, "__successX", title);
			ResultStorage.updateList(analysisBaseFolder, "__base", title);
			ResultStorage.reduceListOfLists(baseFolder, baseListFile, response.getResult());

			// pro form
			printProgress();
		}
		else {
			System.out.println("danger");
			ResultStorage.updateList(baseFolder, "__errorX", title);
		}
	}
	
	public static void debug() {
		
		System.out.println(configNumber);
		Config.qeConfiguration = new QESystemConfiguration(configNumber);
		System.out.println(Config.qeConfiguration.HierarchieAnalogy);
		System.out.println("-------------------------------------------");
	}

}
