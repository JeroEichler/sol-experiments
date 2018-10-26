package solexperiments.runners;

import java.util.Arrays;
import java.util.List;
import solengine.frontier.EngineInterface;
import solengine.model.Vocabulary;

public class AbstractRunner {
	
	static int configNumber = 0;
	
	static String[] names = {
			"musicInflAnalogy",	// 0
			"genAnalogy",		// 1
			"genSeeAlsoSO",		// 2
			"musicAssocSO",		// 3
			"genSameAsSO",		// 4
			"genDiffInversion",	// 5
			"all"				// 6
		};
		
	static String[] query = {
			"band",
			"m-artist",
			"movie"
		};
	
	static String[] mode = {
			"limited",
			"full"
		};
	
	static EngineInterface system = new EngineInterface();
	static List<String> datasetAddresses =  Arrays.asList(Vocabulary.DBpediaEndpoint);

	public static String queryOn = query[2];
	public static String modeOn = mode[0];
	public static String baseProject = names[configNumber];
	
	public static String baseFolder = queryOn + "//" /*+ modeOn +"//" + baseProject*/;	
	
	public static String analysisBaseFolder = "analysis//" + baseFolder;
	
	static String baseListFile = "__userResults"; 
	
	static int counter = 0;
	

	protected static void printProgress() {
		if(counter % 2500 == 0) {
			System.out.println("passed by "+counter);
		}
		counter++;
	}

}
