package com.giteshdalal.serve;

import java.io.File;

public interface EnginePluginConstants {

    String GENSRC = "gensrc";
    String RESOURCES = "src" + File.separator + "main" + File.separator + "resources";
    String TEMPLATES = RESOURCES + File.separator + "templates";

    String SIGNATURE = "\n" + "[GITESHDALAL]    \n"
            + "[GITESHDALAL]      ____ _ ___ ____ ____ _  _    ___  ____ _    ____ _   \n"
            + "[GITESHDALAL]      | __ |  |  |___ [__  |__|    |  \\ |__| |    |__| |   \n"
            + "[GITESHDALAL]      |__] |  |  |___ ___] |  |    |__/ |  | |___ |  | |___\n"
            + "[GITESHDALAL]                                                           \n"
            + "[SERVE]                                                                   \n"
            + "[SERVE]                                                                   \n"
            + "[SERVE]           .M\"\"\"bgd `7MM\"\"\"YMM  `7MM\"\"\"Mq.`7MMF'   `7MF'`7MM\"\"\"YMM \n"
            + "[SERVE]          ,MI    \"Y   MM    `7    MM   `MM. `MA     ,V    MM    `7 \n"
            + "[SERVE]          `MMb.       MM   d      MM   ,M9   VM:   ,V     MM   d   \n"
            + "[SERVE]            `YMMNq.   MMmmMM      MMmmdM9     MM.  M'     MMmmMM   \n"
            + "[SERVE]          .     `MM   MM   Y  ,   MM  YM.     `MM A'      MM   Y  ,\n"
            + "[SERVE]          Mb     dM   MM     ,M   MM   `Mb.    :MM;       MM     ,M\n"
            + "[SERVE]          P\"Ybmmd\"  .JMMmmmmMMM .JMML. .JMM.    VF      .JMMmmmmMMM\n"
            + "[SERVE]                                                                   \n"
            + "[SERVE]                                                                   \n";

    String MODEL_TEMPLATE_FILE = "model.vm";
    String RESOURCE_TEMPLATE_FILE = "resource.vm";
    String REPOSITORY_TEMPLATE_FILE = "repository.vm";

    String MODEL_JAVA = "Model.java";
    String RESOURCE_JAVA = "Resource.java";
    String REPOSITORY_JAVA = "Repository.java";

	String YML_SEARCH_REGEX = ".*models[.]((yml)|(yaml))";
}
