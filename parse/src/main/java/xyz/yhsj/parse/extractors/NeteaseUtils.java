package xyz.yhsj.parse.extractors;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import xyz.yhsj.parse.utils.HttpRequest;

/**
 * Created by LOVE on 2017/4/14 014.
 */

public class NeteaseUtils {

    public static String encryptedID(String SongID) {
        String result = "";

        byte[] secretKey = "3go8&$8*3*3h0k(2)2".getBytes();
        byte[] songId = SongID.getBytes();
        int keyLength = secretKey.length;
        for (int i = 0; i < songId.length; ++i) {
            songId[i] = (byte) (songId[i] ^ secretKey[i % keyLength]);
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            result = HttpRequest.Base64.encodeBytes(md5.digest(songId));
            result = result.replaceAll("/", "_");
            result = result.replaceAll("\\+", "-");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }
}
