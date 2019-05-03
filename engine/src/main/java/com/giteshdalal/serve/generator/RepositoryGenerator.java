package com.giteshdalal.serve.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.giteshdalal.serve.EnginePluginConstants;
import com.giteshdalal.serve.descriptor.ConfigurationDescriptor;
import com.giteshdalal.serve.descriptor.ModelDescriptor;
import com.giteshdalal.serve.descriptor.ModelFileDescriptor;
import com.giteshdalal.serve.util.EnginePluginUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

/**
 * @author gitesh
 */
public class RepositoryGenerator {

	public int generateFiles(final ModelFileDescriptor fileDescriptor, final String srcPath, final String templatePath) {
		int counter = 0;
		final ConfigurationDescriptor configuration = fileDescriptor.getConfiguration();

		VelocityEngine repoEngine = new VelocityEngine();
		repoEngine.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, templatePath);
		repoEngine.init();

		for (int index = 0; index < fileDescriptor.getModels().size(); index++) {
			final ModelDescriptor model = fileDescriptor.getModels().get(index);

			if (model.getHasRepo()) {
				final VelocityContext context = new VelocityContext();
				context.put("model", model);
				context.put("config", configuration);
				context.put("StringUtils", StringUtils.class);

				final Template template = repoEngine.getTemplate(EnginePluginConstants.REPOSITORY_TEMPLATE_FILE);

				final File file = EnginePluginUtil.createNewFile(model.getName(), EnginePluginConstants.REPOSITORY_JAVA,
						configuration.getRepositoryPackage(), srcPath);

				try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
					template.merge(context, writer);
					counter++;
					System.out.println("[SERVE][INIT][GENERATED] " + file.getPath());
				} catch (IOException e) {
					System.out.println("[SERVE][INIT][FAILED]" + file.getPath() + "\n" + e.getMessage());
				}
			}
		}

		return counter;
	}

}
