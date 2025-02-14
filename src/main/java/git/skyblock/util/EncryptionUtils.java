package git.skyblock.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;

public class EncryptionUtils
{
    public static Cipher getCipher(int mode, Key key)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
            cipher.init(mode, key, new IvParameterSpec(key.getEncoded()));

            return cipher;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
