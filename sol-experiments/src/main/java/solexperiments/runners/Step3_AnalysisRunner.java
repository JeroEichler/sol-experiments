package solexperiments.runners;

import java.util.ArrayList;
import java.util.List;

import solengine.configuration.Config;
import solengine.datasetorchestration.QueryResponseAnalysisOrchestrator;
import solengine.model.AnalyzedQueryResponse;
import solengine.model.QueryResponse;
import solengine.model.Vocabulary;
import solengine.utils.StringFormatter;
import solexperiments.storage.ResultStorage;

public class Step3_AnalysisRunner extends AbstractRunner {

	public static void main(String[] args) {
	
		System.out.println(Config.version);
		System.out.println("Here goes " + baseProject);
		
		performAnalysis();
		
		System.out.println("Here goes " + baseFolder);

	}
	
	public static void performAnalysis() {
		
		long start = System.currentTimeMillis();
		
//		stepZero();
		stepOne();
//		stepOne_B();	
//		realOneStep();
//		realFinalStep();
		//readLocalAnalysis();

		long elapsedTime = System.currentTimeMillis() - start;
		
		System.out.println("### Finished at "+elapsedTime/1000F+" seconds");

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
		QueryResponseAnalysisOrchestrator analyser = new QueryResponseAnalysisOrchestrator(Vocabulary.DBpediaEndpoint);
		
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
		QueryResponseAnalysisOrchestrator analyser = new QueryResponseAnalysisOrchestrator(Vocabulary.DBpediaEndpoint);
		
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

	public static void readLocalAnalysisTEMPORARY() {
		double sum = 0, counter = 0;
		List<String> list = ResultStorage.readList(analysisBaseFolder, "__successX");
		
		for(String title : list) {
			AnalyzedQueryResponse aqr = ResultStorage.readAnalysis(analysisBaseFolder, title);
			if(aqr.emptyResponse || !aqr.valid) {
//				System.out.println("error, error");
			}
			else {
				sum = sum + aqr.unexpectednessScore;
				counter++;
			}
		}
		
		double average = 0;
		if(counter != 0) {
			average = sum / counter;
		}

		System.out.println("In path : " + analysisBaseFolder);
		System.out.println("--- unexpectedness equal to : " + average);
		System.out.println("--- in a total of : " + counter);
		System.out.println("-----------------------------------------------");

	}
	
	public static void readLocalAnalysis() {
		double sum = 0, counter = 0;
		List<String> list = ResultStorage.readList(analysisBaseFolder, "__successX");
		
		for(String title : list) {
			AnalyzedQueryResponse aqr = ResultStorage.readAnalysis(analysisBaseFolder, title);
			if(!aqr.valid) {
				System.out.println("error, error");
			}
			else if (!aqr.emptyResponse) {
				sum = sum + aqr.unexpectednessScore;
				counter++;
			}
		}
		
		double average = 0;
		if(counter != 0) {
			average = sum / counter;
		}

		System.out.println("In path : " + analysisBaseFolder);
		System.out.println("--- unexpectedness equal to : " + average);
		System.out.println("--- in a total of : " + counter);
		System.out.println("-----------------------------------------------");

	}
	

	public static void realFinalStep() {

		for(int i=0; i<7; i++) {
			System.out.println("Here goes " + names[i]);
			baseFolder = "analysis//" + queryOn + "//" + modeOn +"//" +  names[i];
			readLocalAnalysis();
			System.out.println("-------------------------------------");
		}

	}


	
	public static void batchStepOne() {	
		
		for(int i=0; i<7; i++) {
			baseFolder = queryOn + "//" + modeOn +"//" +  names[i];
			System.out.println("Here goes " + baseFolder);
			analysisBaseFolder = "analysis//" + baseFolder;
			stepOne();
			System.out.println("-------------------------------------");
		}
		
	}
	

}
