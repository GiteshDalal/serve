package com.giteshdalal.serve;

import com.giteshdalal.serve.descriptor.ServiceDescriptor;
import com.giteshdalal.serve.generator.ServiceGenerator;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.util.Scanner;

/**
 * @author gitesh
 */
public class GenerateNewServiceTask extends DefaultTask {

	@TaskAction
	public void generateService() {
		final ServiceDescriptor service = new ServiceDescriptor();
		try (final Scanner scan = new Scanner(System.in)) {
			System.err.println(EnginePluginConstants.SIGNATURE);
			service.setName(askForName(scan));
			service.setGroup(askForGroup(scan));
			service.setPort(askForPort(scan));
		}

		final ServiceGenerator gen = new ServiceGenerator();
		gen.generate(service, super.getProject());

	}

	private String askForName(Scanner scan) {
		System.err.println("\n[SERVE]    Please enter the NAME for the new service");
		System.err.println("[SERVE]    (witherr 'service' suffix, for example 'myNewProduct')");
		final String name = scan.next();
		System.err.println("\n[SERVE]    Input received: " + name);

		if (!name.matches("[A-Za-z0-9]+")) {
			System.err.println("[SERVE][ERROR] NAME can only contain alphanumeric characters");
			return askForName(scan);
		} else {
			return name;
		}
	}

	private String askForGroup(Scanner scan) {
		System.err.println("\n[SERVE]    Please enter the GROUP for the new service");
		System.err.println("[SERVE]    (for example 'com.giteshdalal')");
		final String group = scan.next();
		System.err.println("\n[SERVE]    Input received: " + group);

		if (!group.matches("([A-Za-z0-9]+)([.][A-Za-z0-9]+)+")) {
			System.err.println("\n[SERVE][ERROR] Invalid input received: " + group);
			System.err.println("[SERVE][ERROR] GROUP should be like 'io.group.example'");
			return askForGroup(scan);
		} else {
			return group;
		}
	}

	private String askForPort(Scanner scan) {
		System.err.println("\n[SERVE]    Please enter the PORT number for the new service");
		System.err.println("[SERVE]    (for example '8100')");
		final String port = scan.next();
		System.err.println("\n[SERVE]    Input received: " + port);

		if (!port.matches("[0-9]{2,6}")) {
			System.err.println("\n[SERVE][ERROR] Invalid input received: " + port);
			System.err.println("[SERVE][ERROR] PORT can only contain numeric characters");
			return askForPort(scan);
		} else {
			return port;
		}
	}
}
