package multiagent;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JFrame;

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import multiagent.util.AgentUtils;

public class SoundLoopExample extends Application {

    String clipTitle = "SnorreTidemand_LeapOfFaith";
    File audioFile = new File("./src/resources/" + clipTitle + ".wav");
    MediaPlayer player;

    public SoundLoopExample() {
        //launch();
        try {
            start(null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        launch();
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
    public MediaPlayer getPlayer(){
        return player;
    }
}
