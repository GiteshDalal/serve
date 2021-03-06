package com.giteshdalal.serve.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.giteshdalal.serve.EnginePluginConstants;
import com.giteshdalal.serve.util.EnginePluginUtil;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;

import com.giteshdalal.serve.descriptor.ConfigurationDescriptor;
import com.giteshdalal.serve.descriptor.ModelDescriptor;
import com.giteshdalal.serve.descriptor.ModelFileDescriptor;

/**
 * @author gitesh
 *
 */
public class ResourceGenerator {

	public int generateFiles(final ModelFileDescriptor fileDescriptor, final String srcPath,
			final String templatePath) {
		int counter = 0;
		final ConfigurationDescriptor configuration = fileDescriptor.getConfiguration();

		Velocity.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, templatePath);
		Velocity.init();

		for (int index = 0; index < fileDescriptor.getModels().size(); index++) {
			final ModelDescriptor model = fileDescriptor.getModels().get(index);

			if (model.getHasResource()) {
				final VelocityContext context = new VelocityContext();
				context.put("model", model);
				context.put("config", configuration);

				final Template template = Velocity.getTemplate(EnginePluginConstants.RESOURCE_TEMPLATE_FILE);

				final File file = EnginePluginUtil.createNewFile(model.getName(), EnginePluginConstants.RESOURCE_JAVA,
						configuration.getResourcePackage(), srcPath);

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
