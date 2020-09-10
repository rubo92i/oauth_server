package am.basic.notificator.auth.util;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class Md5Encoder implements PasswordEncoder {

    public String encode(CharSequence rawPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(rawPassword.toString().getBytes());
            byte[] messageDigest = digest.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                StringBuilder h = new StringBuilder(Integer.toHexString(0xFF & aMessageDigest));
                while (h.length() < 2) {
                    h.insert(0, "0");
                }
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }


    public boolean matches(CharSequence plain, String encoded) {
        return Objects.equals(encode(plain), encoded);
    }
}
