package com.ankit.whatsappclone;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
public class AESEncryption {
    private SecretKey secretKey;
    private Cipher cipher;

    public AESEncryption() throws Exception {
        // Generate a secret key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        secretKey = keyGenerator.generateKey();

        // Initialize the cipher with the secret key
        cipher = Cipher.getInstance("AES");
    }

    public byte[] encrypt(String message) throws Exception {
        // Initialize the cipher for encryption
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        // Encrypt the message
        byte[] inputData = message.getBytes();
        byte[] encryptedData = cipher.doFinal(inputData);

        return encryptedData;
    }

    public String decrypt(byte[] encryptedData) throws Exception {
        // Initialize the cipher for decryption
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        // Decrypt the message
        byte[] decryptedData = cipher.doFinal(encryptedData);
        String decryptedMessage = new String(decryptedData);

        return decryptedMessage;
    }
    /*private static final String ALGORITHM = "AES";
    private static final String KEY = "mySecretKey12345"; // The secret key should be kept secure

    public static String encrypt(String data) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getEncoder().encodeToString(encryptedData);
        }
        return data;
    }

    public static String decrypt(String encryptedData) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedData = new byte[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        }
        return new String(decryptedData);
    }*/
}
