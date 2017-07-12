package multiagent;

import java.io.File;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javax.swing.JFrame;
import multiagent.util.AgentUtils;

/**
 *
 * @author Donni
 */
public class SoundLoopExample extends Application {

    String clipTitle = "SnorreTidemand_LeapOfFaith";
    File audioFile = new File("./src/resources/" + clipTitle + ".wav");
    MediaPlayer player;

    /**
     *
     */
    public SoundLoopExample() {
        try {
            start(null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("launch");

        JFrame audioFrame = new JFrame("DEEP OCEAN MINING - THE FIRST RACE");
        audioFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        final JFXPanel fxPanel = new JFXPanel();
        audioFrame.add(fxPanel);

        clipTitle = "SnorreTidemand_LeapOfFaith.wav";
        audioFile = AgentUtils.getFile(clipTitle,"wav");

        String path = audioFile.toURI().toString();

        final Media media = new Media(path);
        player = new MediaPlayer(media);

        player.setOnReady(new Runnable() {
            @Override
            public void run() {

                player.setVolume(0.7);
                player.setCycleCount(Integer.MAX_VALUE);
                player.play();

            }

        });

    }

    /**
     *
     * @return
     */
    public MediaPlayer getPlayer(){
        return player;
    }
}
