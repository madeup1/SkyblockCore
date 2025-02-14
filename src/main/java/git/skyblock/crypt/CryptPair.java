package git.skyblock.crypt;

import git.skyblock.util.EncryptionUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CryptPair
{
    private Cipher sendCipher;
    private Cipher receiveCipher;
    private SecretKey sharedSecret;

    public CryptPair(SecretKey sharedSecret)
    {
        this.sendCipher = EncryptionUtils.getCipher(1, sharedSecret);
        this.receiveCipher = EncryptionUtils.getCipher(2, sharedSecret);

        this.sharedSecret = sharedSecret;
    }

    public byte[] encrypt(byte[] data)
    {
        try
        {
            return this.sendCipher.update(data);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return new byte[0];
    }

    public byte[] decrypt(byte[] data)
    {
        try
        {
            return this.receiveCipher.update(data);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return new byte[0];
    }

    public SecretKey sharedSecret()
    {
        return this.sharedSecret;
    }
}
