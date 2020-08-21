package sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;



/**
 * Music controller class for the in game music
 */
public enum MusicController {
	THEME("resources/music/wav/Poker Theme.wav", "resources/music/wav/Poker Theme Loop.wav"),       // bullet

	SECRET("resources/music/wav/SuperSecretHiddenTheme.wav", "resources/music/wav/SuperSecretHiddenThemeLoop.wav");

   // Nested class for specifying volume
   public static enum Volume {
      MUTE, LOW, MEDIUM, HIGH
   }


   static volatile boolean off = false;
   public static Volume volume = Volume.LOW;

   // Each sound effect has its own clip, loaded with its own sound file.
   private Clip intro_clip, loop_clip;

   // Constructor to construct each element of the enum with its own sound file.
   MusicController(String songIntroName, String songLoopName) {
      try {
         // Use URL (instead of File) to read from disk and JAR.
         //URL url = this.getClass().getClassLoader().getResource(soundFileName);
         File intro = new File(songIntroName);
    	 File loop = new File(songLoopName);

    	  // Set up an audio input stream piped from the sound file.
         AudioInputStream introInputStream = AudioSystem.getAudioInputStream(intro);
         AudioInputStream loopInputStream = AudioSystem.getAudioInputStream(loop);
         // Get a clip resource.
         intro_clip = AudioSystem.getClip();
         loop_clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
         intro_clip.open(introInputStream);
         loop_clip.open(loopInputStream);
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }

   /**
    * Plays the music
    */
   public void play() {
      if (volume != Volume.MUTE) {
    	  off = false;
         if (loop_clip.isRunning())
            loop_clip.stop();
         if (intro_clip.isRunning())
             intro_clip.stop();

         MusicThread musicThread = new MusicThread();
         musicThread.start();


      }
   }

   // Optional static method to pre-load all the sound files.
   public static void init() {
      values(); // calls the constructor for all the elements
   }

   /**
    * Stops the music
    */
   public void stop() {
	   if (loop_clip.isRunning()) loop_clip.stop();
	   if (intro_clip.isRunning()) intro_clip.stop();
	   off = true;
   }

   private class MusicThread extends Thread {


	   public void run(){

		   intro_clip.setFramePosition(0); // rewind to the beginning
		   loop_clip.setFramePosition(0);
	       intro_clip.start();     // Start playing
	       while(intro_clip.getFramePosition()<intro_clip.getFrameLength()-6 && !off)
	    	   Thread.yield();


	       loop_clip.loop(Clip.LOOP_CONTINUOUSLY);
	       if (off) {
	    	   intro_clip.stop();
	    	   loop_clip.stop();
	    	   return;
	       }

		}
   }




}
