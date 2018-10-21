package solexperiments.runners;

import java.util.ArrayList;
import java.util.List;

import solengine.datasetorchestration.ResultAnalysisOrchestrator;
import solengine.model.AnalyzedQueryResponse;
import solengine.model.QueryResponse;
import solengine.model.Vocabulary;
import solengine.utils.StringFormatter;
import solexperiments.storage.ResultStorage;

public class AnalysisRunner_ThirdStep extends AbstractRunner {

public static void main(String[] args) {
		
		System.out.println("Here goes " + baseProject);
		
		long start = System.currentTimeMillis();
		
//		stepZero();
//		stepOne();
//		stepOne_B();	
//		realOneStep();
		realFinalStep();
//		finalStep();

		long elapsedTime = System.currentTimeMillis() - start;
		
		System.out.println("### Finished at "+elapsedTime/1000F+" seconds");
		
		System.out.println("Here goes " + baseFolder);

	}
	
	public static void stepZero() {
		List<String> list = ResultStorage.readList(baseFolder, "__successX");
		List<QueryResponse> responses = new ArrayList<QueryResponse>();
		
		for(String title : list) {
			QueryResponse qr = ResultStorage.readQResponse(baseFolder, title);
			if(qr.getObjects().size() > 0) {
				responses.add(qr);
			}
		}
		System.out.println(responses.size() + "!");
	}
	
	public static void stepOne() {		
		ResultAnalysisOrchestrator analyser = new ResultAnalysisOrchestrator(Vocabulary.DBpediaEndpoint);
		
		List<String> list = ResultStorage.readList(analysisBaseFolder, "__base");
		List<QueryResponse> responses = new ArrayList<QueryResponse>();
		
		for(String title : list) {
			QueryResponse qr = ResultStorage.readQResponse(baseFolder, title);
//			System.out.println(qr.getObjects());
			responses.add(qr);
		}
		List<AnalyzedQueryResponse> analysis = analyser.analyzeList(responses);
		
		for(AnalyzedQueryResponse item : analysis) {
//			System.out.println(title.unexpectednessScore);
			printProgress();
			persistAnalysis(item);	
		}
	}
	
	public static void stepOne_B() {		
		ResultAnalysisOrchestrator analyser = new ResultAnalysisOrchestrator(Vocabulary.DBpediaEndpoint);
		
		List<String> list = ResultStorage.readList(analysisBaseFolder, "__base");
		
		for(String title : list) {
			System.out.println(title);
			QueryResponse qr = ResultStorage.readQResponse(baseFolder, title);
			AnalyzedQueryResponse analyzed = analyser.analyzeSingle(qr);
			persistAnalysis(analyzed);				
		}
	}
	
	private static void persistAnalysis(AnalyzedQueryResponse analyzed) {
		String title = StringFormatter.clean(analyzed.queryResponse.getResult());
		if(analyzed.valid) {
			ResultStorage.saveSingleAnalysis(analysisBaseFolder, analyzed);
			ResultStorage.updateList(analysisBaseFolder, "__successX", title);
			ResultStorage.reduceList(analysisBaseFolder, "__base", title);
		}
		else {
			ResultStorage.updateList(analysisBaseFolder, "__errorX", title);
			System.out.println("danger, danger.");
		}	
	}

	public static void finalStep() {
		double sum = 0, counter = 0;
		List<String> list = ResultStorage.readList(analysisBaseFolder, "__successX");
		
		for(String title : list) {
			AnalyzedQueryResponse aqr = ResultStorage.readAnalysis(analysisBaseFolder, title);
			if(!aqr.emptyResponse) {
				sum = sum + aqr.unexpectednessScore;
				counter++;
			}
		}
		
		double average = 0;
		if(counter != 0) {
			average = sum / counter;
		}

		System.out.println("unexpectedness equal to : " + average);
		System.out.println("in a total of : " + counter);

	}
	

	public static void realFinalStep() {

		for(int i=0; i<7; i++) {
			System.out.println("Here goes " + names[i]);
			baseFolder = "analysis//" + queryOn + "//" + modeOn +"//" +  names[i];
			finalStep();
			System.out.println("-------------------------------------");
		}

	}


	public static void realOneStep() {

		for(int i=1; i<6; i++) {
			System.out.println("Here goes " + names[i]);
			baseFolder = "analysis//full//" + queryOn + "//" + modeOn +"//" +  names[i];
			stepOne();
			System.out.println("-------------------------------------");
		}

	}

}
