package git.skyblock.util;

public class Logger
{
    private final String INFO_STRING;
    private final String WARNING_STRING;
    private final String ERROR_STRING;
    private final String LOG_STRING;
    public Logger(String owner)
    {
        this.INFO_STRING = "[" + owner + "] [INFO] ";
        this.WARNING_STRING = "[" + owner + "] [WARN] ";
        this.ERROR_STRING = "[" + owner + "] [ERROR] ";
        this.LOG_STRING = "[" + owner + "] [LOG] ";
    }

    public void info(String text)
    {
        System.out.println(this.INFO_STRING + text);
    }

    public void error(String text)
    {
        System.out.println(this.ERROR_STRING + text);
    }

    public void warn(String text)
    {
        System.out.println(this.WARNING_STRING + text);
    }

    public void log(String text)
    {
        System.out.println(this.LOG_STRING + text);
    }
}
