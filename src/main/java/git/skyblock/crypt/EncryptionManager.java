package git.skyblock.crypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

public class EncryptionManager
{
    private KeyPair keypair;
    private SecureRandom random;
    public EncryptionManager()
    {
        try
        {
            KeyPairGenerator pairGenerator = KeyPairGenerator.getInstance("RSA");
            pairGenerator.initialize(1024);

            this.keypair = pairGenerator.generateKeyPair();

            this.random = new SecureRandom();
        }
        catch (Exception e)
        {

        }
    }

    public SecretKey bytesToSecret(byte[] bytes)
    {
        return new SecretKeySpec(decryptUsingKey(keyPair().getPrivate(), bytes), "AES");
    }

    public static byte[] decryptUsingKey(Key key, byte[] bytes) {
        return cipherData(2, key, bytes);
    }

    private static byte[] cipherData(int mode, Key key, byte[] data) {
        try
        {
            return setupCipher(mode, key.getAlgorithm(), key).doFinal(data);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private static Cipher setupCipher(int mode, String transformation, Key key) {
        try
        {
            Cipher cipher4 = Cipher.getInstance(transformation);
            cipher4.init(mode, key);
            return cipher4;
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException var4) {
            var4.printStackTrace();
        }
        return null;
    }

    public byte[] getRandomToken()
    {
        byte[] token = new byte[4];
        random.nextBytes(token);

        return token;
    }

    public KeyPair keyPair()
    {
        return this.keypair;
    }
}
