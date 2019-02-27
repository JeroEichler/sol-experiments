package solexperiments.sample.areader;

import java.util.List;

import solengine.model.AnalyzedQueryResponse;
import solexperiments.storage.ResultStorage;

public class AnalysisReader {

	private static String title = "The_Rolling_Stones";
	private static String analysisBaseFolder = "2_Analysis//band//limited//genAnalogy";

	public static void main(String[] args) {
		AnalyzedQueryResponse aqr = ResultStorage.readAnalysis(analysisBaseFolder, title );
		
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

	}

}
