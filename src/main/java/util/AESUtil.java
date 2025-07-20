
package util;


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESUtil {

    public static String cifrar(String textoPlano, String clave) {
        try {
            byte[] keyBytes = clave.getBytes("UTF-8");
            SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] encrypted = cipher.doFinal(textoPlano.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return "[error]";
        }
    }

    public static String descifrar(String textoCifrado, String clave) {
        try {
            byte[] keyBytes = clave.getBytes("UTF-8");
            SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decoded = Base64.getDecoder().decode(textoCifrado);
            byte[] original = cipher.doFinal(decoded);

            return new String(original, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return "[error]";
        }
    }
}
