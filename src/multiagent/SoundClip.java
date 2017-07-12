package multiagent;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import multiagent.util.AgentUtils;

/**
 *
 * @author Donni
 */
public class SoundClip  {

    /**
     *
     * @param clipTitle
     */
    public SoundClip(String clipTitle) { 
		this(clipTitle, 0);
	}
	
	
   // Constructor

    /**
     *
     * @param clipTitle
     * @param milliseconds
     */
   public SoundClip(String clipTitle, int milliseconds) {      
      try {	          
          File soundFile = AgentUtils.getFile(clipTitle + ".wav", "wav");
          
          if (!soundFile.exists()){
        	  System.out.println("Soundfile " + soundFile.getAbsolutePath() + " not found");
        	  return;
          }
          
          AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);     
          
          //Sleep until file is over
          if (milliseconds == -1){
        	  AudioFormat format = audioIn.getFormat();
        	  long frames = audioIn.getFrameLength();
        	  milliseconds = (int) ((frames+0.0) / format.getFrameRate() * 1000 - 100);   
          }
          
          
         // Get a sound clip resource.
         Clip clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
         clip.open(audioIn);
         clip.start();
         
         Thread.sleep(milliseconds);
         
         
         
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      } catch (InterruptedException e) {
             e.printStackTrace();
      }
      
   }
}