import java.net.URL;
import java.util.HashMap;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
class audio
{
    static Clip clip;
    static HashMap<String, URL> Name_Stream=new HashMap<>();
      
    public static void load() throws Exception
    {
        Name_Stream.put("win",(audio.class.getResource("/audio/winplay.wav")));
        Name_Stream.put("lose",(audio.class.getResource("/audio/loseplay.wav")));
        Name_Stream.put("put",(audio.class.getResource("/audio/putplay.wav")));
        Name_Stream.put("invalid",(audio.class.getResource("/audio/invalidplay.wav")));
        Name_Stream.put("thischeck",(audio.class.getResource("/audio/thischeck.wav")));
        Name_Stream.put("thatcheck",(audio.class.getResource("/audio/thatcheck.wav")));
        Name_Stream.put("castle",(audio.class.getResource("/audio/castle.wav")));
        Name_Stream.put("capture",(audio.class.getResource("/audio/capture.wav")));
        Name_Stream.put("choose",(audio.class.getResource("/audio/choice.wav")));
        Name_Stream.put("start",(audio.class.getResource("/audio/start.wav")));
        Name_Stream.put("draw",(audio.class.getResource("/audio/draw.wav")));
        
    }
    public static void main()
    {
        try
        {
            load();
            //play("start");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
    public static void play(String nam)
    {
        
        new Thread(new Runnable()
        {
            public void run()
            {
                
                try
                {
                    clip=AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(Name_Stream.get(nam)));
                    clip.loop(0);
                    clip.start();
                    
                }
                catch (Exception lue)
                {
                    lue.printStackTrace();
                }
                
            }
        }).start();
        
    }
    
}