class TimeType
{
    static String[] times={"Untimed","01 minute","10 minutes","30 minutes","1 hour","2 hours","3 hours"};
    int sec,min,hr;
    boolean unl=false;
    /**
     * if unlimited time is passed, the name labels in gui will show infinity symbol or maybe show nothing
     * this class converts options to hour, minutes, seconds which will then be displayed in the name labels
     * 
     */
    public TimeType(String t)
    {
        if(t.contains("timed"))
        {
            unl=true;
        }
        if(t.contains("inut"))
        {
            sec=0;
            min=Integer.parseInt(t.substring(0,2));
            hr=0;
        }
        if(t.contains("our"))
        {
            sec=0;
            min=0;
            hr=Integer.parseInt(t.substring(0,1));
        }
    }
    public TimeType(int s,int m,int h)
    {
        sec=s;
        min=m;
        hr=h;
    }
    public boolean isUnlimited()
    {
        return unl;
    }
    public int getSeconds()
    {
        return sec;
    }
    public int getMinutes()
    {
        return min;
    }
    public int getHours()
    {
        return hr;
    }
}