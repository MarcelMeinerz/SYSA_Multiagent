/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiagent;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.SplashScreen;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import multiagent.gui.ServerFrame;
import multiagent.util.AgentUtils;
import sun.misc.IOUtils;

/**
 *
 * @author Marcel_Meinerz (marcel.meinerz@th-bingen.de)
 * @author Steffen_Hollenbach
 * @author Jasmin_Welschbillig
 *
 * @version 1.0
 */
public class MultiAgent {

    private static SplashScreen mySplash;
    private static Graphics2D splashGraphics;
    private static Rectangle2D splashTextArea, splashProgressArea;
    private static String[] operation = new String[]{"Lade Altmeister", "Male Agents", "Baue Spielfeld auf", "Setze Resourcen", "Lade Strategien", "Lade Cheats", "Lösche Doku", "Schreibe 1.0 in SYSA", "Reiße Weltherrschaft an mich", "Reiße Weltherrschaft an mich"};

    private static Runnable runRun;
    static int width, height;
    static double scale = 0.7;
    static JFrame videoFrame;
    static MediaPlayer player;

    public static void main(String[] args) {
        Platform.setImplicitExit(false);

        runRun = new Runnable() {

            @Override
            public void run() {
                java.awt.EventQueue.invokeLater(() -> {
                    ServerFrame frame = new ServerFrame();
                    frame.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent e) {
                            frame.disposeAll();
                        }
                    });
                    frame.setVisible(true);
                    player.pause();
                    videoFrame.dispose();
                    new SoundClip("Loaded");

                });

            }
        };

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Platform.setImplicitExit(false);
                splashInit();           // initialize splash overlay drawing parameters
                appInit();              // simulate what an application would do 
                // before starting
                if (mySplash != null) // check if we really had a spash screen
                {
                    mySplash.close();   // if so we're now done with it
                }
                initAndShowGUI();
            }
        });

    }

    private static void initAndShowGUI() {
        // This method is invoked on Swing thread
        videoFrame = new JFrame("DEEP OCEAN MINING - THE FIRST RACE");
        videoFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                new Thread(runRun).start();
            }
        };
        videoFrame.addWindowListener(exitListener);
        videoFrame.setResizable(false);
        final JFXPanel fxPanel = new JFXPanel();
        videoFrame.add(fxPanel);

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int x = (int) ((gd.getDisplayMode().getWidth() - videoFrame.getWidth()) / 4);
        int y = (int) ((gd.getDisplayMode().getHeight() - videoFrame.getHeight()) / 4);
        videoFrame.setLocation(x, y);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
            }
        });
    }

    private static void initFX(JFXPanel fxPanel) {
        // This method is invoked on JavaFX thread
        Scene scene = null;
        try {
            scene = start();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        fxPanel.setScene(scene);

    }

    public static Scene start() throws Exception {
        //URL url2 = MultiAgent.class.getResource("/multiagent/resources/Intro_Final_v1.mp4");
        final BorderPane root = new BorderPane();
        
        URI uri = AgentUtils.getFile("Intro_Final_v1.mp4", "mp4").toURI();
        final Media media = new Media("File://"+uri.getPath());
        player = new MediaPlayer(media);
        final MediaView view = new MediaView(player);

        root.getChildren().add(view);

        // Scale Image View
        view.setScaleX(scale);
        view.setScaleY(scale);

        Scene scene = new Scene(root);

        player.setOnReady(new Runnable() {
            @Override
            public void run() {
                width = (int) (player.getMedia().getWidth());
                height = (int) (player.getMedia().getHeight());

                // Move Image View
                view.setTranslateX(width * (scale - 1) / 2);
                view.setTranslateY(height * (scale - 1) / 2 - 10);

                //stage.setWidth(w * scale);
                //stage.setHeight(h * scale + 20);
                videoFrame.setSize((int) (width * scale), (int) (height * scale));
                videoFrame.setVisible(true);
                player.play();
                player.setOnEndOfMedia(runRun);
                player.setOnStopped(runRun);
                player.setOnHalted(runRun);
                player.setOnError(runRun);

            }
        });

        return scene;
    }

    private static void splashInit() {
        mySplash = SplashScreen.getSplashScreen();
        if (mySplash != null) {   // if there are any problems displaying the splash this will be null
            Dimension ssDim = mySplash.getSize();
            int height = ssDim.height;
            int width = ssDim.width;
            // stake out some area for our status information

            splashProgressArea = new Rectangle2D.Double(0, height * .92, width, 12);

            // create the Graphics environment for drawing status info
            splashGraphics = mySplash.createGraphics();
            Font font = new Font("Dialog", Font.BOLD, 14);

            splashGraphics.setFont(font);

            // initialize the status info
            splashText("Starting");
            splashProgress(0);
        }
    }

    /**
     * Display text in status area of Splash. Note: no validation it will fit.
     *
     * @param str - text to be displayed
     */
    public static void splashText(String str) {
        if (mySplash != null && mySplash.isVisible()) {   // important to check here so no other methods need to know if there
            // really is a Splash being displayed

            Dimension ssDim = mySplash.getSize();
            int height = ssDim.height;
            int width = ssDim.width;
            splashGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
            splashTextArea = new Rectangle2D.Double(3., height * .85, width * .45, 20.);
            //splashGraphics.clearRect(0, 0, width, height);
            // erase the last status text
            splashGraphics.setPaint(new Color(0f, 0f, 0f, 0f));
            splashGraphics.fill(splashTextArea);
            splashGraphics.setComposite(AlphaComposite.SrcOver);
            // draw the text
            splashGraphics.setPaint(Color.WHITE);
            splashGraphics.drawString(str, (int) (splashTextArea.getX() + 10), (int) (splashTextArea.getY() + 15));

            // make sure it's displayed
            mySplash.update();
        }
    }

    /**
     * Display a (very) basic progress bar
     *
     * @param pct how much of the progress bar to display 0-100
     */
    public static void splashProgress(int pct) {
        if (mySplash != null && mySplash.isVisible()) {

            // Note: 3 colors are used here to demonstrate steps
            // erase the old one
            splashGraphics.setPaint(Color.LIGHT_GRAY);
            splashGraphics.fill(splashProgressArea);

            // draw an outline
            splashGraphics.setPaint(Color.BLUE);
            splashGraphics.draw(splashProgressArea);

            // Calculate the width corresponding to the correct percentage
            int x = (int) splashProgressArea.getMinX();
            int y = (int) splashProgressArea.getMinY();
            int wid = (int) splashProgressArea.getWidth();
            int hgt = (int) splashProgressArea.getHeight();

            int doneWidth = Math.round(pct * wid / 100.f);
            doneWidth = Math.max(0, Math.min(doneWidth, wid - 1));  // limit 0-width

            // fill the done part one pixel smaller than the outline
            splashGraphics.setPaint(Color.GREEN);
            splashGraphics.fillRect(x, y + 1, doneWidth, hgt - 1);

            // make sure it's displayed
            mySplash.update();
        }
    }

    /**
     * just a stub to simulate a long initialization task that updates the text
     * and progress parts of the status in the Splash
     */
    private static void appInit() {
        for (int i = 1; i <= 10; i++) {
            int pctDone = i * 10;
            splashText(operation[i - 1]);
            splashProgress(pctDone);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                // ignore it
            }
        }
    }
}
