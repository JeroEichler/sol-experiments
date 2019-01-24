package solexperiments.runners;

import solengine.configuration.Config;
import solengine.configuration.QESystemConfiguration;

public class About {

	public static void main(String[] args) {
		System.out.println("Version: " + Config.version);
		
		// test
		System.out.println(Config.qeConfiguration.DifferenceInversion);		
		Config.qeConfiguration = new QESystemConfiguration(3);
		System.out.println(Config.qeConfiguration.DifferenceInversion);
	}

}
