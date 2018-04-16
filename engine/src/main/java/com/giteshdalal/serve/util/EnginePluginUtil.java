package com.giteshdalal.serve.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.giteshdalal.serve.descriptor.ModelFileDescriptor;
import org.gradle.api.GradleException;
import org.gradle.internal.impldep.org.codehaus.plexus.util.FileUtils;

/**
 * @author gitesh
 */
public class EnginePluginUtil {

    public static void emptyDirectory(final String dir) throws IOException {
        EnginePluginUtil.emptyDirectory(new File(dir));
    }

    public static void emptyDirectory(final File dir) throws IOException {
        FileUtils.cleanDirectory(dir);
    }

    public static List<File> findInputFilesIn(File resources, String searchRegex)
            throws GradleException {
        if (!resources.exists()) {
            throw new GradleException("[SERVE][ERROR] Unable to locate folder : " + resources.getPath());
        }
        return EnginePluginUtil.findFilesIn(resources, searchRegex);
    }

    public static List<File> findFilesIn(final File base, final String regex) {
        final List<File> results = new ArrayList<>();
        final File[] files = base.listFiles();
        for (final File file : files) {
            if (file.isDirectory()) {
                results.addAll(EnginePluginUtil.findFilesIn(file, regex));
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

    public static File createNewFile(final String name, final String suffix, final String javaPackage, final String path) {
        final String packagePath = javaPackage.replace('.', File.separatorChar);
        final String newFilePath = path + File.separator + packagePath + File.separator + name + suffix;
        final File file = new File(newFilePath);
        final File parent = file.getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }
        return file;
    }
}
