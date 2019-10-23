package org.pcm.headless.api;

import java.io.File;

import org.pcm.headless.api.client.PCMHeadlessClient;
import org.pcm.headless.api.client.SimulationClient;

public class HeadlessClientTest {

	public static void main(String[] args) {
		PCMHeadlessClient client = new PCMHeadlessClient("http://127.0.0.1:8080/");
		if (client.isReachable(3000)) {
			System.out.println("Backend erreichbar.");

			File allocationFile = new File("examples/cocome/cocome.allocation");
			File monitorRepositoryFile = new File("examples/cocome/cocome.monitorrepository");
			File repositoryFile = new File("examples/cocome/cocome.repository");
			File resourceEnvironmentFile = new File("examples/cocome/cocome.resourceenvironment");
			File systemFile = new File("examples/cocome/cocome.system");
			File usageFile = new File("examples/cocome/cocome.usagemodel");

			SimulationClient sim = client.prepareSimulation();
			sim.setAllocation(allocationFile);
			sim.setMonitorRepository(monitorRepositoryFile);
			sim.setRepository(repositoryFile);
			sim.setSystem(systemFile);
			sim.setUsageModel(usageFile);
			sim.setResourceEnvironment(resourceEnvironmentFile);

			sim.createTransitiveClosure();
			sim.sync();

			sim.clearLocal();
		}
	}

}
