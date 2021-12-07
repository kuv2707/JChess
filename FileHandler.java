class FileHandler
{
    final static String not_end="nn",yes_end="yy";
    public static void writeMove(String alg)
    {
        if(Environment.canRecordGame()==false)
        return;
        Environment.gameFileWriter.println(alg);
        try
        {
            Environment.gameFileWriter.flush();
        }
        catch (Exception ioe)
        {
            //ioe.printStackTrace();
        }
    }
    public static void exitTask()
    {
        if(Environment.canRecordGame() &&  !game.isGameActive())
            writeMove(yes_end);
    }
}