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
 * @author Marcel_Meinerz (marcel.meinerz@th-bingen.de)
 * @author Steffen_Hollenbach
 * @author Jasmin_Welschbillig
 *
 * @version 1.0
 */
public final class SoundLoopExample extends Application {

    String clipTitle;
    File audioFile;
    MediaPlayer player;

    /**
     * Konstruktor zur initialisierung einer Hintergrund-Schleife
     */
    public SoundLoopExample() {
        try {
            start(null);
        } catch (Exception e) {
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

        player.setOnReady(() -> {
            player.setVolume(0.7);
            player.setCycleCount(Integer.MAX_VALUE);
            player.play();
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
