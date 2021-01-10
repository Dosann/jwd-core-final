package com.epam.jwd.core_final.util;

import com.epam.jwd.core_final.domain.ApplicationProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PropertyReaderUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyReaderUtil.class);

    private static final Properties properties = new Properties();
    public static ApplicationProperties applicationProperties;

    private PropertyReaderUtil() {
    }

    /**
     * try-with-resource using FileInputStream
     *
     * @see {https://www.netjstech.com/2017/09/how-to-read-properties-file-in-java.html for an example}
     * <p>
     * as a result - you should populate {@link ApplicationProperties} with corresponding
     * values from property file
     */
    public static void loadProperties() {
        LOGGER.info("Loading properties from application.properties file...");

        final String propertiesFileName = "src/main/resources/application.properties";
        FileInputStream iStream = null;
        try {
            iStream = new FileInputStream(propertiesFileName);
            properties.load(iStream);
        } catch (IOException e) {
            LOGGER.error("Exception occurred: {}", e.toString());
        } finally {
            try {
                if (iStream != null) {
                    iStream.close();
                }
            } catch (IOException e) {
                LOGGER.error("Exception occurred: {}", e.toString());
            }
        }
        populateApplicationProperties();

        LOGGER.info("Application properties has been loaded and populated");
    }

    private static void populateApplicationProperties() {
        LOGGER.info("Populating properties...");

        applicationProperties = new ApplicationProperties(
                properties.getProperty("inputRootDir"),
                properties.getProperty("outputRootDir"),
                properties.getProperty("crewFileName"),
                properties.getProperty("missionsFileName"),
                properties.getProperty("spaceshipsFileName"),
                Integer.valueOf(properties.getProperty("fileRefreshRate")),
                properties.getProperty("dateTimeFormat")
        );
    }
}
