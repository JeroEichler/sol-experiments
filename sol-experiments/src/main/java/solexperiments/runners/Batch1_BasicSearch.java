package solexperiments.runners;

public class Batch1_BasicSearch extends AbstractRunner{
	
	
	static int queryId = 0, modeId = 0;

	public static void main(String[] args) {
		
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
					baseFolder = "data//" + query[queryId] + "//" + mode[modeId] +"//" + names[configNumber];	
					queryOn = query[queryId];
					Step1_BasicSearchRunner.performBasicSearch();
				}
			}
			System.out.println("Finished with " + query[queryId]);
		}
	}

}
