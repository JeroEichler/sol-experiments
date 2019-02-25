package solexperiments.runners;

import java.util.ArrayList;
import java.util.List;

import solengine.model.Analysis;
import solexperiments.storage.BaseStorage;

public class Batch4_AnalysisReport extends AbstractRunner {

	static int queryId = 0, modeId = 0, configNumber = 0;

	public static void main(String[] args) {
		
		List<Analysis> output = new ArrayList<Analysis>();
		
		System.out.println("Are you ready?");
		
		//query = [	band(0), m-artist(1), movie(2)	]
		for(queryId=0; queryId<3; queryId++) {
			//mode = [ limited(0), full(1)	]
			for(modeId=0; modeId<2; modeId++) {
				//config = [	musicInflAnalogy(0), genAnalogy(1), 
				//				genSeeAlsoSO(2), musicAssocSO(3), genSameAsSO(4),
				//				genDiffInversion(5),
				//				all(6)	]
				for(configNumber=0; configNumber<7; configNumber++) {
					String functionalPath = query[queryId] + "//" + mode[modeId] +"//" + names[configNumber];
					baseFolder = "data//" + functionalPath;
					analysisBaseFolder = "analysis//" + functionalPath;
					baseProject = names[configNumber];
					
					modeOn = mode[modeId];
					counter = 0;
					
					Analysis analysis = Step3_AnalysisRunner.readLocalAnalysis();
					output.add(analysis);
				}
			}
			System.out.println("Finished with " + query[queryId]);
		}
		BaseStorage.saveEntity("output", "analysisList", output);
	}

}
