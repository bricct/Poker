import java.io.*;
import javax.sound.sampled.*;

/** Houses the sounf effects for the game and allows them to be played
 * 
 */
public enum SoundEffects {
   DRAW("draw.wav"),   // explosion
   SHUFFLE("shuffle.wav"),         // gong
   CHIP("chips.wav");       // bullet

   // Nested class for specifying volume
   public static enum Volume {
      MUTE, LOW, MEDIUM, HIGH
   }

   public static Volume volume = Volume.LOW;

   // Each sound effect has its own clip, loaded with its own sound file.
   private Clip clip;

   // Constructor to construct each element of the enum with its own sound file.
   SoundEffects(String soundFileName) {
      try {
         // Use URL (instead of File) to read from disk and JAR.
         //URL url = this.getClass().getClassLoader().getResource(soundFileName);
         File file = new File(soundFileName);


    	  // Set up an audio input stream piped from the sound file.
         AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
         // Get a clip resource.
         clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
         clip.open(audioInputStream);
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }

   // Play or Re-play the sound effect from the beginning, by rewinding.
   public void play() {
      if (volume != Volume.MUTE) {
         if (clip.isRunning())
            clip.stop();   // Stop the player if it is still running
         clip.setFramePosition(0); // rewind to the beginning
         clip.start();     // Start playing
      }
   }

   // Optional static method to pre-load all the sound files.
   static void init() {
      values(); // calls the constructor for all the elements
   }
}
