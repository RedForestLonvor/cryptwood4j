package cn.org.redforest.cryptwood;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CWConfig {
    private static final String CONFIG_FILE = "config.ini";
    private static final String DEFAULT_KEY_PATH = "HOME";

    private static boolean configExists() {
        File tmpDir = new File(CONFIG_FILE);
        return !tmpDir.exists();
    }

    public static String getCustomPath() throws IOException {
        Properties prop = new Properties();
        if (configExists()) {
            prop.setProperty("keyPath", DEFAULT_KEY_PATH);
            prop.store(new FileWriter(CONFIG_FILE), null);
        } else {
            prop.load(new FileReader(CONFIG_FILE));
        }
        return prop.getProperty("keyPath", DEFAULT_KEY_PATH);
    }

    public static void setPath(String customPath) throws IOException {
        if (configExists()) {
            Files.createDirectories(Paths.get(customPath));
        }
        File customPathFile = new File(customPath);
        if (!customPathFile.exists()) {
            if (customPathFile.isAbsolute()) {
                System.out.println("Path does not exist, creating directory(s)");
                customPathFile.mkdirs();
            } else {
                throw new IllegalArgumentException("Illegal key path! (\\ for Windows / for Linux)");
            }
        } else if (customPathFile.isFile()) {
            throw new IllegalArgumentException("Expected directory path, not a file path");
        }
        Properties prop = new Properties();
        prop.load(new FileReader(CONFIG_FILE));
        prop.setProperty("keyPath", customPath);
        prop.store(new FileWriter(CONFIG_FILE), null);
    }
}
