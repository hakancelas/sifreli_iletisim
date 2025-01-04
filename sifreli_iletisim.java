import javax.crypto.Cipher;
import java.security.*;
import java.util.Base64;

public class SecureMessagingApp {

    // Anahtar çiftini oluştur
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    // Mesajı şifrele (Public key ile)
    public static String encryptMessage(String message, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedMessage = cipher.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(encryptedMessage);
    }

    // Şifreyi çöz (Private key ile)
    public static String decryptMessage(String encryptedMessage, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decodedMessage = Base64.getDecoder().decode(encryptedMessage);
        byte[] decryptedMessage = cipher.doFinal(decodedMessage);
        return new String(decryptedMessage);
    }

    public static void main(String[] args) {
        try {
            // Anahtar çiftlerini oluştur
            KeyPair keyPair1 = generateKeyPair(); // Gönderici
            KeyPair keyPair2 = generateKeyPair(); // Alıcı

            // Gönderici mesajı şifreler
            String message = "Bu, gizli mesajdır!";
            System.out.println("Gönderici Mesaj: " + message);
            String encryptedMessage = encryptMessage(message, keyPair2.getPublic());
            System.out.println("Şifreli Mesaj: " + encryptedMessage);

            // Alıcı mesajı çözer
            String decryptedMessage = decryptMessage(encryptedMessage, keyPair2.getPrivate());
            System.out.println("Çözülmüş Mesaj: " + decryptedMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}