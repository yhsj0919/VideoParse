package xyz.yhsj.videoparse.extractors.youku.entity;

/**
 * 加密信息
 * Created by LOVE on 2016/2/15.
 */
public class SecurityEntity {
    private String encrypt_string;
    private int ip;

    public void setEncrypt_string(String encrypt_string) {
        this.encrypt_string = encrypt_string;
    }

    public void setIp(int ip) {
        this.ip = ip;
    }

    public String getEncrypt_string() {
        return encrypt_string;
    }

    public int getIp() {
        return ip;
    }
}
