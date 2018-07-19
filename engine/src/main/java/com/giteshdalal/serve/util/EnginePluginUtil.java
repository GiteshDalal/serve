package com.giteshdalal.serve.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.gradle.api.GradleException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.giteshdalal.serve.descriptor.ModelFileDescriptor;

/**
 * @author gitesh
 *
 */
public class EnginePluginUtil {

	public static void emptyDirectory(final String dir) throws IOException {
		EnginePluginUtil.emptyDirectory(new File(dir));
	}

	public static void emptyDirectory(final File dir) throws IOException {
		FileUtils.cleanDirectory(dir);
	}

	public static List<File> findFiles(final File base, final String regex) throws GradleException {
		if (!base.exists()) {
			throw new GradleException("[SERVE][ERROR] Unable to locate folder : " + base.getPath());
		}
		final List<File> results = new ArrayList<>();
		final File[] files = base.listFiles();
		for (final File file : files) {
			if (file.isDirectory()) {
				results.addAll(EnginePluginUtil.findFiles(file, regex));
			} else if (file.getName().matches(regex)) {
				results.add(file);
			}
		}
		return results;
	}

	public static List<ModelFileDescriptor> translateFiles(final List<File> yamlFiles) throws GradleException {
		final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		final List<ModelFileDescriptor> modelFileList = new ArrayList<>();
		for (final File f : yamlFiles) {
			try {
				modelFileList.add(mapper.readValue(f, ModelFileDescriptor.class));
			} catch (JsonParseException e) {
				throw new GradleException("[SERVE][ERROR] Failed to parse file : " + f.getName(), e);
			} catch (JsonMappingException e) {
				throw new GradleException("[SERVE][ERROR] Failed to map file : " + f.getName(), e);
			} catch (IOException e) {
				throw new GradleException("[SERVE][ERROR] Failed to process file : " + f.getName(), e);
			}
		}
		return modelFileList;
	}

	public static File createNewFile(final String name, final String suffix, final String javaPackage,
			final String path) {
		final String packagePath = StringUtils.isNotBlank(javaPackage)
				? javaPackage.replace('.', File.separatorChar) + File.separator
				: StringUtils.EMPTY;
		final String newFilePath = path + File.separator + packagePath + name + suffix;
		final File file = new File(newFilePath);
		final File parent = file.getParentFile();
		if (!parent.exists() && !parent.mkdirs()) {
			throw new IllegalStateException("Couldn't create dir: " + parent);
		}
		return file;
	}

	public static File createNewFile(final String name, final String suffix, final String path) {
		return EnginePluginUtil.createNewFile(name, suffix, StringUtils.EMPTY, path);
	}

	public static void appendToFile(final File file, final String text) {
		try {
			FileUtils.writeStringToFile(file, text, StandardCharsets.UTF_8, true);
		} catch (IOException e) {
			throw new IllegalStateException("Couldn't append text in file: " + file.getPath());
		}
	}

	public static void writeFile(final String template, final VelocityContext context, final File file) {
		Template t = Velocity.getTemplate(template);
		try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
			t.merge(context, writer);
			System.out.println("[SERVE][NEWSERVICE][INFO] Generated file: " + file.getPath());
		} catch (IOException e) {
			throw new GradleException("[SERVE][NEWSERVICE][ERROR] Failed to generate file: " + file.getPath(), e);
		}
	}

	public static void writeFile(final File resourceLoaderDirectory, final String template,
			final VelocityContext context, final File file) {
		Velocity.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, resourceLoaderDirectory.getAbsolutePath());
		Velocity.init();
		EnginePluginUtil.writeFile(template, context, file);
	}

	public static void writeFiles(final File resourceLoaderDirectory, final String tempParentPath,
			final VelocityContext context, final String path, final String group, final String replaceRegex,
			final String replaceWith) {
		for (File templateFile : resourceLoaderDirectory.listFiles()) {
			if (templateFile.isDirectory()) {
				final String newGroup = StringUtils.isNotBlank(group) ? group + "." + templateFile.getName()
						: templateFile.getName();
				final String templateParentPath = StringUtils.isNotBlank(tempParentPath)
						? tempParentPath + templateFile.getName() + File.separator
						: templateFile.getName() + File.separator;
				EnginePluginUtil.writeFiles(templateFile, templateParentPath, context, path, newGroup, replaceRegex,
						replaceWith);
			} else {
				final String name = StringUtils.isNotBlank(replaceRegex)
						? templateFile.getName().replaceAll(replaceRegex, replaceWith)
						: templateFile.getName();
				final File file = EnginePluginUtil.createNewFile(name, StringUtils.EMPTY, group, path);
				EnginePluginUtil.writeFile(tempParentPath + templateFile.getName(), context, file);
			}
		}
	}
}
