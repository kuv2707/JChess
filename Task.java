abstract class Task implements Runnable
{
    abstract public void perform();
    public void run()
    {
        Environment.log("doing task");
        perform();
    }
}