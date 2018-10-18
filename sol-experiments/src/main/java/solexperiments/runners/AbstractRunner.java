package solexperiments.runners;

import java.util.Arrays;
import java.util.List;
import solengine.frontier.EngineInterface;
import solengine.model.Vocabulary;

public class AbstractRunner {
	
	static int configNumber = 1;
	
	static String[] names = {
			"all",				// 0
			"genAnalogy",		// 1
			"genDiffInversion",	// 2
			"genSameAsSO",		// 3
			"genSeeAlsoSO",		// 4
			"musicAssocSO",		// 5
			"musicInflAnalogy"	// 6
		};
		
	static String[] query = {
			"band",
			"m-artist"
		};
	
	static String[] mode = {
			"limited",
			"full"
		};
	
	static EngineInterface system = new EngineInterface();
	static List<String> datasetAddresses =  Arrays.asList(Vocabulary.DBpediaEndpoint);

	public static String queryOn = query[0];
	public static String modeOn = mode[1];
	public static String baseProject = names[configNumber];
	
	public static String baseFolder = queryOn + "//" + modeOn +"//" + baseProject;
	
	static String baseListFile = "__userResults"; 
	
	static int counter = 0;
	

	protected static void printProgress() {
		if(counter % 500 == 0) {
			System.out.println("passed by "+counter);
		}
		counter++;
	}
	
	public static String analysisBaseFolder = "analysis//" + baseFolder;

}
