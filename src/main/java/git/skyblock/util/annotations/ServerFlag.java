package git.skyblock.util.annotations;

import git.skyblock.util.Flags;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ServerFlag
{
    String value();

    Flags.ServerFlagType type();
}

