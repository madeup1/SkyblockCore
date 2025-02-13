package git.skyblock.crypt;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;

public class CryptPair
{
    private Cipher sendCipher;
    private Cipher receiveCipher;
    private SecretKeySpec sharedSecret;

    public CryptPair(SecretKeySpec sharedSecret)
    {
        try
        {
            this.sendCipher = Cipher.getInstance("RSA");
            this.sendCipher.init(Cipher.ENCRYPT_MODE, sharedSecret);

            this.receiveCipher = Cipher.getInstance("RSA");
            this.receiveCipher.init(Cipher.DECRYPT_MODE, sharedSecret);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        this.sharedSecret = sharedSecret;
    }

    public byte[] encrypt(byte[] data)
    {
        try
        {
            return this.sendCipher.doFinal(data);
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
            return this.receiveCipher.doFinal(data);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return new byte[0];
    }

    public SecretKeySpec sharedSecret()
    {
        return this.sharedSecret;
    }
}
