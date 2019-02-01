package solexperiments.runners;

public class Batch2_SerendipitySearch extends AbstractRunner {

	static int queryId = 0, modeId = 0;

	public static void main(String[] args) {
		
		System.out.println("Are you ready?");
		
		//query = [	band(0), m-artist(1), movie(2)	]
		for(queryId=2; queryId<3; queryId++) {
			//mode = [ limited(0), full(1)	]
			for(modeId=1; modeId<2; modeId++) {
				//config = [	musicInflAnalogy(0), genAnalogy(1), 
				//				genSeeAlsoSO(2), musicAssocSO(3), genSameAsSO(4),
				//				genDiffInversion(5),
				//				all(6)	]
				for(configNumber=1; configNumber<3; configNumber++) {
					String functionalPath = query[queryId] + "//" + mode[modeId] +"//" + names[configNumber];
					baseFolder = "data//" + functionalPath;
					analysisBaseFolder = "analysis//" + functionalPath;
					
					modeOn = mode[modeId];
					counter = 0;
					
					Step2_SerendipitySearchRunner.performSerendipitySearch();
				}
			}
			System.out.println("Finished with " + query[queryId]);
		}
	}

}
