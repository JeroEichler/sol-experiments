package solexperiments.sample.areader;

import java.util.ArrayList;
import java.util.List;

import solengine.model.AnalyzedQueryResponse;
import solexperiments.storage.ResultStorage;

public class AnalysisReaderLoop {
	
	static String[] query = {
			"band",
			"m-artist",
			"movie"
		};
	
	static String[] names = {
			"musicInflAnalogy",	// 0
			"genAnalogy",		// 1
			"genSeeAlsoSO",		// 2
			"musicAssocSO",		// 3
			"genSameAsSO",		// 4
			"genDiffInversion",	// 5
			"all"				// 6
		};

	private static String title = "The_Rolling_Stones";
	private static String analysisBaseFolder = "2_Analysis//"+query[0] +"//limited//" +names[3];
	private static AnalyzedQueryResponse closest = null;
	private static List<AnalyzedQueryResponse> listA = new ArrayList<AnalyzedQueryResponse>();
	private static double control = 0;

	public static void main(String[] args) {
		loop();
		printWinner(closest);
		for(AnalyzedQueryResponse a: listA) {
			System.out.println("-----------------------------");
			printWinner(a);
		}
	}
	
	public static void loop() {
		List<String> list = ResultStorage.readList(analysisBaseFolder, "__successX");
		
		for(String title : list) {
			AnalyzedQueryResponse aqr = ResultStorage.readAnalysis(analysisBaseFolder, title);
			if(!aqr.valid) {
				System.out.println("error, error");
			}
			else if (!aqr.emptyResponse) {
				//double local = aqr.unexpectednessScore;
				double local = getCoverage(aqr);
				if(local > control) {
					control = local;
					if(closest !=  null) {
						listA.add(closest);
					}
					closest  = aqr;
				}
			}
		}
	}
	
	public static double getCoverage(AnalyzedQueryResponse aqr) {
		List<String> listTwo = aqr.additionalInfoLabels.values().iterator().next();
		
		double intersecctionCounter = 0;
		
		for(String x : aqr.resultLabels) {
			if(listTwo.contains(x)) {
				intersecctionCounter++;
			}
		}
		
		return intersecctionCounter / aqr.resultLabels.size();
	}
	
	public static void printWinner(AnalyzedQueryResponse aqr) {
		List<String> listTwo = aqr.additionalInfoLabels.values().iterator().next();
		
		double intersecctionCounter = 0;
		
		for(String x : aqr.resultLabels) {
			if(listTwo.contains(x)) {
				intersecctionCounter++;
			}
		}
		
		System.out.println(aqr.unexpectednessScore);		

		System.out.println(aqr.resultLabels.size());
		System.out.println(intersecctionCounter);		

		System.out.println(aqr.queryResponse.getResult());

	}

}
