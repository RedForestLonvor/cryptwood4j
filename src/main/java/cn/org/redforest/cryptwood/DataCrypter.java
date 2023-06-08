package cn.org.redforest.cryptwood;

import com.google.gson.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class DataCrypter {

    private static String getProjectPath() {
        return new File(".").getAbsolutePath();
    }

    public static void encrypt(Object userData) throws Exception {
        Gson gson = new Gson();
        String userDataJson = gson.toJson(userData);
        byte[] key = AES256Cryptor.generateKey().getEncoded();
        byte[] iv = AES256Cryptor.generateIV();
        byte[] cryptAns = AES256Cryptor.aesCbcEncrypt(key, userDataJson.getBytes(), iv);
        FileManager.addToFile(cryptAns, key, iv, getProjectPath());
    }

    public static <T> T decrypt(Class<T> classOfT) throws Exception {
        Gson gson = new Gson();
        String projectPath = getProjectPath();
        byte[] key = Files.readAllBytes(Paths.get(FileManager.getKeyPath(projectPath) + "key"));
        byte[] iv = Files.readAllBytes(Paths.get(FileManager.getKeyPath(projectPath) + "iv"));
        byte[] userData = Files.readAllBytes(Paths.get(FileManager.getCiphertextPath(projectPath)));
        byte[] decryptedData = AES256Cryptor.aesCbcDecrypt(key, iv, userData);
        return gson.fromJson(new String(decryptedData), classOfT);
    }

    public static void setPath(String customPath) throws IOException {
        CWConfig.setPath(customPath);
    }
}
