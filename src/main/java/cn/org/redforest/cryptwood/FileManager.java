package cn.org.redforest.cryptwood;

import java.io.*;
import java.nio.file.*;

public class FileManager {

    public static String getKeyPath(String projectPath) throws IOException {
        projectPath = projectPath.replace(':','_');
        String defaultKeyPath = CWConfig.getCustomPath();
        defaultKeyPath = defaultKeyPath.equals("HOME") ? System.getProperty("user.home") : defaultKeyPath;
        return Paths.get(defaultKeyPath, ".cryptUserDataKey", projectPath.replace(File.separator, "_")).toString();
    }

    public static String getCiphertextPath(String projectPath) {
        return Paths.get(projectPath, ".cryptUserData").toString();
    }

    public static void addToFile(byte[] cryptAns, byte[] key, byte[] iv, String path) throws IOException {
        String keyPath = getKeyPath(path);
        Files.createDirectories(Paths.get(keyPath));
        Files.write(Paths.get(keyPath + "key"), key);
        Files.write(Paths.get(keyPath + "iv"), iv);
        Files.write(Paths.get(getCiphertextPath(path)), cryptAns);
    }
}
