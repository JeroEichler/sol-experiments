package solexperiments.runners;

import java.util.List;

import solexperiments.storage.ResultStorage;

public class AssistentRunner extends AbstractRunner {

	public static void main(String[] args) {
		copyList();
		batchCopyFile();

	}
	
	public static void copyList() {		
		
		List<String> list = ResultStorage.readList(baseFolder, "__successX");
		ResultStorage.saveEntity(analysisBaseFolder, "__base", list);
		
	}
	
	public static void batchCopyFile() {	
		
		for(int i=0; i<7; i++) {
			System.out.println("Here goes " + names[i]);
			baseFolder = queryOn + "//" + modeOn +"//" +  names[i];
			analysisBaseFolder = "analysis//" + baseFolder;
			copyList();
			System.out.println("-------------------------------------");
		}
		
	}
	

}
