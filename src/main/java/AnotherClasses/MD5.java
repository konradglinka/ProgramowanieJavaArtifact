package AnotherClasses;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    public String getMD5Password(String input)
    {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] messageDigestArray = messageDigest.digest(input.getBytes());
            BigInteger bigInteger = new BigInteger(1, messageDigestArray);
            String md5result = bigInteger.toString(16);
            while (md5result.length() < 32) {
                md5result = "0" + md5result;
            }
            return md5result;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
