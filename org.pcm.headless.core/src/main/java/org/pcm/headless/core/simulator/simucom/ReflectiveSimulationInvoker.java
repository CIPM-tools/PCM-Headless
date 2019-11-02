package org.pcm.headless.core.simulator.simucom;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

import org.pcm.headless.core.util.ReflectionUtil;

public class ReflectiveSimulationInvoker {
	private static final String AGENT_MAIN_CLASS = "agent.main.Initializer";

	private File jar;

	private URLClassLoader classloader;

	public ReflectiveSimulationInvoker(File simuComJar) {
		this.jar = simuComJar;
	}

	public void invokeSimulation(Map<String, Object> configMap) {
		classloader = prepareClassloader(this.jar);

		// get classes
		Class<?> initializerClass = ReflectionUtil.getClassFromClassloader(AGENT_MAIN_CLASS, classloader);

		// init agent
		ReflectionUtil.directlyInvokeMethod(initializerClass, "initialize", null, true);

		// start simulation
		ReflectionUtil.directlyInvokeMethod(initializerClass, "triggerSimulation", null, false, configMap);
	}

	public void close() {
		// close whole classloader
		try {
			classloader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private URLClassLoader prepareClassloader(File jar) {
		try {
			return new URLClassLoader(new URL[] { jar.toURI().toURL() },
					ReflectiveSimulationInvoker.class.getClassLoader());
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
