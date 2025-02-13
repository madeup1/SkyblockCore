package git.skyblock.crypt;

import javax.crypto.KeyGenerator;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.SecureRandom;

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
