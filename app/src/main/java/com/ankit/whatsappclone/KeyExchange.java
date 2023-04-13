package com.ankit.whatsappclone;
import javax.crypto.KeyAgreement;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class KeyExchange {
    private final KeyPairGenerator keyPairGenerator;
    private final KeyAgreement keyAgreement;

    public KeyExchange() throws Exception {
        // Generate a new key pair
        keyPairGenerator = KeyPairGenerator.getInstance("DH");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Initialize the key agreement with the private key
        keyAgreement = KeyAgreement.getInstance("DH");
        keyAgreement.init(keyPair.getPrivate());
    }

    public byte[] generatePublicKey() throws Exception {
        // Generate a public key from the private key
        PublicKey publicKey = keyPairGenerator.generateKeyPair().getPublic();

        // Return the encoded public key
        return publicKey.getEncoded();
    }

    public byte[] generateSharedSecret(byte[] otherPublicKeyBytes) throws Exception {
        // Decode the other party's public key
        KeyFactory keyFactory = KeyFactory.getInstance("DH");
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(otherPublicKeyBytes);
        PublicKey otherPublicKey = keyFactory.generatePublic(x509KeySpec);

        // Generate the shared secret
        keyAgreement.doPhase(otherPublicKey, true);
        byte[] sharedSecret = keyAgreement.generateSecret();

        // Return the shared secret
        return sharedSecret;
    }
}