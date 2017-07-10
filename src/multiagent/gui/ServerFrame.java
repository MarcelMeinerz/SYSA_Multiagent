/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiagent.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javax.media.CannotRealizeException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.media.Player;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.swing.JOptionPane;

import multiagent.AgentImpl;
import multiagent.MultiAgent;
import multiagent.remote.IAgent;
import multiagent.PlayingField;
import multiagent.SoundClip;
import multiagent.SoundLoopExample;
import multiagent.remote.IMultiAgentServer;
import multiagent.remote.IPlayer;
import multiagent.network.MultiAgentServer;
import multiagent.remote.IStrategy;
import multiagent.util.AgentUtils;

/**
 *
 * @author Marcel_Meinerz (marcel.meinerz@th-bingen.de)
 * @author Steffen_Hollenbach
 * @author Jasmin_Welschbillig
 *
 * @version 1.0
 */
public class ServerFrame extends javax.swing.JFrame implements Serializable {

    private IMultiAgentServer server;
    private ConfigurationDlg dialog;
    private final ArrayList<IAgent> agentList;
    private final HashMap<String, IPlayer> iPlayerList;
    private int playerCount;
    private final PlayingField playingField;
    public boolean running;
    public boolean aborted;
    private RenderLife renderLife;
    private int targetAmount, agentsValue;
    private final HashMap<String, ArrayList<IAgent>> playersAgentsMap;
    private String[][] agentsOnSpawn;
    //Sound indicators
    private String dominator;
    private boolean firstBlood;
    SoundLoopExample sl;
    static int width, height;
    static double scale = 0.7;
    static JFrame videoFrame;
    static MediaPlayer player;
    Player mediaPlayer;

    /**
     * Creates new form ServerFrame
     */
    public ServerFrame() {
        //Sound indicators
        dominator = "";
        firstBlood = false;
        initComponents();
        jLabel1.setIcon(new ImageIcon(getClass().getResource("/resources/title.jpg")));
        dialog = new ConfigurationDlg(this, true);
        setTitle("Game server");
        agentList = new ArrayList<>();
        playerCount = 0;
        running = true;
        aborted = true;
        playersAgentsMap = new HashMap<>();
        iPlayerList = new HashMap<>();
        agentsOnSpawn = new String[][]{{"random", "random", "random"}, {"random", "random", "random"}, {"random", "random", "random"}};
        String selected = String.valueOf(dialog.getFieldDimension().getSelectedItem());
        String selected2 = String.valueOf(dialog.getTightnessField().getSelectedItem());
        playingField = new PlayingField(Integer.parseInt(dialog.getResourceField().getText()),
                Integer.parseInt(selected.substring(selected.indexOf("x") + 1)),
                Integer.parseInt(selected2.substring(0, selected2.indexOf("%"))),
                agentList,
                playerCount,
                Integer.parseInt(String.valueOf(dialog.getCountAgents().getSelectedItem())));
        setLocationRelativeTo(null);
        pack();
        boolean connect;
        try {

            connect = true;
            this.jTextArea1.setText("Server starts...");
            server = new MultiAgentServer(this);
            String ip = "";
            int errorBit = 256;
            for (int i = 0; i < server.getIpAddr().length; i++) {
                int currentIP = server.getIpAddr()[i];
                if (currentIP < 0) {
                    currentIP += errorBit;
                }
                ip += currentIP + ".";
            }
            ip = ip.substring(0, ip.length() - 1);
            System.out.println(ip);
            Naming.rebind("//" + ip + "/server", server);
            this.jTextArea1.setText("Servername: " + ip);
            this.jTextArea1.append("\nServer is online");
        } catch (MalformedURLException | RemoteException e) {
            this.jTextArea1.append("\nServer is offline, something goes wrong!!!");
            connect = false;
        }
        if (dialog.getSoundBox().isSelected()) {
            sl = new SoundLoopExample();
        }
        reconnectBtn.setEnabled(!connect);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        console = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        playerList = new javax.swing.JPanel();
        start = new javax.swing.JButton();
        config = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        reconnectBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(8);
        jTextArea1.setEnabled(false);
        console.setViewportView(jTextArea1);

        playerList.setBorder(javax.swing.BorderFactory.createTitledBorder("Player"));
        playerList.setLayout(new javax.swing.BoxLayout(playerList, javax.swing.BoxLayout.Y_AXIS));

        start.setText("Start game");
        start.setMaximumSize(new java.awt.Dimension(97, 23));
        start.setMinimumSize(new java.awt.Dimension(97, 23));
        start.setPreferredSize(new java.awt.Dimension(97, 23));
        start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startActionPerformed(evt);
            }
        });

        config.setText("Configuration");
        config.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configActionPerformed(evt);
            }
        });

        jLabel1.setToolTipText("Click for trailer");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(20, 20, 20)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 84, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        reconnectBtn.setText("Reconnect");
        reconnectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reconnectBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(console)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(config, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(start, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(reconnectBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(playerList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(start, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(config)
                        .addGap(9, 9, 9)
                        .addComponent(reconnectBtn)))
                .addGap(8, 8, 8)
                .addComponent(playerList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(console, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        getContentPane().add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startActionPerformed
        //JOptionPane.showMessageDialog(this, "No game available!", "Here could stay your title", JOptionPane.INFORMATION_MESSAGE);
        if (!aborted) {
            return;
        }
        this.sl.getPlayer().stop();
        if (dialog.getSoundBox().isSelected()) {
            new SoundClip("Play", -1);
            try {
                this.sl.start(null);
            } catch (Exception ex) {
                Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        reload();
        playingField.setPlayerCount(playerCount);

        String selected = String.valueOf(dialog.getFieldDimension().getSelectedItem());
        String selected2 = String.valueOf(dialog.getTightnessField().getSelectedItem());

        playingField.setSize(Integer.parseInt(selected.substring(selected.indexOf("x") + 1)));
        playingField.setTightness(Integer.parseInt(selected2.substring(0, selected2.indexOf("%"))));
        playingField.setMax(Integer.parseInt(dialog.getResourceField().getText()));
        playingField.setAgentsValue(Integer.parseInt(dialog.getAgentsValue().getText()));
        playingField.setTargetAmount(Integer.parseInt(dialog.getTargetAmountField().getText()));
        playingField.setMaxAgents(Integer.parseInt(String.valueOf(dialog.getCountAgents().getSelectedItem())));
        playingField.initPlayingField();
        playingField.setiPlayerList(iPlayerList);
        targetAmount = Integer.parseInt(dialog.getTargetAmountField().getText());
        agentsValue = Integer.parseInt(dialog.getAgentsValue().getText());
        renderLife = new RenderLife(playingField);
        renderLife.repaint();
        agentList.forEach((agent) -> {
            try {
                System.out.println(agent.getName() + ": PosX:" + agent.getPosx() + "/ PosY:" + agent.getPosy());
                agent.setRememberField(new PlayingField(playingField.getSize()));
                agent.getRememberField().setiPlayerList(playingField.getiPlayerList());
                agent.setCapacity(Integer.parseInt(dialog.getCapacityField().getText()));
            } catch (RemoteException ex) {
                Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        start.setText("Restart");
        running = true;
        aborted = false;
        this.jTextArea1.setText("");
        runGame();
    }//GEN-LAST:event_startActionPerformed

    private void configActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configActionPerformed
        java.awt.EventQueue.invokeLater(() -> {
            if (dialog == null) {
                dialog = new ConfigurationDlg(this, true);
            }
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    dialog.dispose();
                }
            });
            dialog.setVisible(true);
        });
    }//GEN-LAST:event_configActionPerformed

    private void reconnectBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reconnectBtnActionPerformed
        try {
            this.jTextArea1.setText("Server starts...");
            server = new MultiAgentServer(this);
            String ip = "";
            int errorBit = 256;
            for (int i = 0; i < server.getIpAddr().length; i++) {
                int currentIP = server.getIpAddr()[i];
                if (currentIP < 0) {
                    currentIP += errorBit;
                }
                ip += currentIP + ".";
            }
            ip = ip.substring(0, ip.length() - 1);
            System.out.println(ip);
            Naming.rebind("//" + ip + "/server", server);
            this.jTextArea1.setText("Servername: " + ip);
            this.jTextArea1.append("\nServer is online");
        } catch (MalformedURLException | RemoteException e) {
            this.jTextArea1.append("\nServer is offline, something goes wrong!!!");
        }
    }//GEN-LAST:event_reconnectBtnActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        this.sl.getPlayer().stop();
        /*try {
            Desktop.getDesktop().open(new File(MultiAgent.class.getResource("./../resources/Intro_Final_v1.mp4").getPath()));
        } catch (IOException ex) {
            Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        try {
            mediaPlayer = Manager.createRealizedPlayer(MultiAgent.class.getResource("./../resources/Intro_Final_v1.mp4"));
            mediaPlayer.realize();
            JFrame frame = new JFrame("DEEP OCEAN MINING - THE FIRST RACE");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            WindowListener exitListener = new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    mediaPlayer.stop();
                    frame.dispose();
                }
            };
            frame.addWindowListener(exitListener);
            frame.setResizable(false);
            Container con = frame.getContentPane();
            Component visualcomp = mediaPlayer.getVisualComponent();
            con.add(visualcomp, BorderLayout.CENTER);
            con.doLayout();
            mediaPlayer.start();
        } catch (MalformedURLException ex) {
            Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | NoPlayerException | CannotRealizeException ex) {
            Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        //initAndShowGUI();
        this.sl.getPlayer().play();
    }//GEN-LAST:event_jLabel1MouseClicked

    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
        /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
 /*try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
 /*java.awt.EventQueue.invokeLater(() -> {
            new ServerFrame().setVisible(true);
        });
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton config;
    private javax.swing.JScrollPane console;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JPanel playerList;
    private javax.swing.JButton reconnectBtn;
    private javax.swing.JButton start;
    // End of variables declaration//GEN-END:variables

    public boolean addNewAgent(IPlayer player, IStrategy strat) {
        if (!aborted) {
            return false;
        }

        try {
            if (running) {
                for (String name : iPlayerList.keySet()) {
                    if (player.getName().equals(name)) {
                        player.setName(name + "_copy");
                        return addNewAgent(player, strat);
                    }
                }
            }

            iPlayerList.put(player.getName(), player);
            playerCount++;
            PlayerPanel panel = new PlayerPanel(player.getName());
            this.playerList.add(panel);
            ArrayList<IAgent> tmp = new ArrayList<>();
            IAgent agent = new AgentImpl(player.getName(), strat, playingField);
            agent.setColor(panel.getColor());
            agent.setPosx(-1);
            agent.setPosy(-1);
            agentList.add(agent);
            tmp.add(agent);
            playersAgentsMap.put(player.getName(), tmp);
            this.jTextArea1.append("\n" + player.getName() + " added.");
            playerList.repaint();
            playerList.revalidate();
            pack();

        } catch (RemoteException ex) {
            Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    private IAgent[] createIAgentArray(String name) {
        IAgent[] output = new IAgent[playersAgentsMap.get(name).size()];

        for (int i = 0; i < output.length; i++) {
            output[i] = playersAgentsMap.get(name).get(i);
        }
        return output;
    }

    public void runGame() {
        start.setEnabled(false);
        Runnable tsk = () -> {
            while (running) {
                agentList.clear();
                playersAgentsMap.entrySet().stream().map((pair) -> (ArrayList<IAgent>) pair.getValue()).map((iAgentList) -> {
                    return iAgentList;
                }).forEachOrdered((iAgentList) -> {
                    agentList.addAll(iAgentList);
                });
                for (IAgent iAgent : agentList) {
                    try {
                        try {
                            iAgent.setAgentArray(createIAgentArray(iAgent.getName())); //Immer notwendig als erstes 
                            iAgent.getStrategy().nextAction(iAgent); // TODO Auto-generated catch block
                        } catch (RemoteException ex) {
                            Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        switch (iAgent.getOrder()) {
                            case AgentUtils.CHECK:
                                break;
                            case AgentUtils.LEFT:
                                playingField.moveAgent(iAgent, AgentUtils.LEFT);
                                break;
                            case AgentUtils.RIGHT:
                                playingField.moveAgent(iAgent, AgentUtils.RIGHT);
                                break;
                            case AgentUtils.TOP:
                                playingField.moveAgent(iAgent, AgentUtils.TOP);
                                break;
                            case AgentUtils.BOTTOM:
                                playingField.moveAgent(iAgent, AgentUtils.BOTTOM);
                                break;
                            case AgentUtils.TAKE:
                                playingField.takeResources(iAgent);
                                break;
                            case AgentUtils.PUT:
                                playingField.setResources(iAgent, iAgent.getPlanedPut());
                                break;
                            case AgentUtils.BUY:
                                findNextAvailableAgent(playersAgentsMap.get(iAgent.getName()));
                                break;
                            default:
                                System.out.println("Planed Exit");
                                System.exit(0);
                        }

                    } catch (RemoteException ex) {
                        Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                checkSpawnTemp();
                checkPointSounds();

                if (!(checkPoints() && checkRemainingRes()) || renderLife.isClosed()) {
                    new SoundClip("EndOfRound", -1);
                    aborted = true;
                    running = false;
                    start.setEnabled(true);
                    //The Winner is...
                    String message = getWinnerList();
                    if (!message.equals("")) {
                        JOptionPane.showMessageDialog(this, message, "The Winner is...", JOptionPane.OK_OPTION);
                    }

                }
                renderLife.repaint();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        };
        Thread t = new Thread(tsk);
        t.start();
    }

    private boolean findNextAvailableAgent(ArrayList<IAgent> arrayList) {

        for (IAgent agent : arrayList) {
            try {
                if (agent.checkIfOnSpawn()) {
                    return false;
                }
            } catch (RemoteException e) {
                return false;
            }
        }

        for (IAgent agent : arrayList) {
            try {
                if (arrayList.size() < Integer.parseInt(String.valueOf(dialog.getCountAgents().getSelectedItem()))) {
                    if (iPlayerList.get(agent.getName()).getPoints() >= agentsValue) {
                        for (int[] spawn : playingField.getSpawnFields()) {
                            if (playingField.requestField(spawn[0], spawn[1])) {
                                System.out.println("Empty spawn found");
                                IAgent newAgent = new AgentImpl(agent.getName(), agent.getStrategy(), playingField);
                                newAgent.setColor(agent.getColor());
                                newAgent.setPosx(spawn[0]);
                                newAgent.setPosy(spawn[1]);
                                newAgent.setCapacity(Integer.parseInt(dialog.getCapacityField().getText()));
                                newAgent.getRememberField().setiPlayerList(playingField.getiPlayerList());
                                playingField.setOccupancy(spawn[0], spawn[1], newAgent);
                                ArrayList<IAgent> templist = new ArrayList<>();
                                for (IAgent list : playersAgentsMap.get(agent.getName())) {
                                    templist.add(list);
                                }
                                templist.add(newAgent);
                                playersAgentsMap.put(agent.getName(), templist);
                                iPlayerList.get(agent.getName()).setPoints(iPlayerList.get(agent.getName()).getPoints() - agentsValue);
                                return true;
                            }

                        }

                    }
                }
            } catch (RemoteException e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        return false;

    }

    private boolean checkRemainingRes() {
        for (int i = 0; i < playingField.getPlayingField().length; i++) {
            for (int j = 0; j < playingField.getPlayingField()[i].length; j++) {
                if (playingField.getPlayingField()[i][j].getResources() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkPoints() {

        for (Map.Entry pair : playersAgentsMap.entrySet()) {
            ArrayList<IAgent> iAgentList = (ArrayList<IAgent>) pair.getValue();

            int points = 0;
            try {
                points = iPlayerList.get(iAgentList.get(0).getName()).getPoints();
            } catch (RemoteException ex) {
                Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                for (Component comp : playerList.getComponents()) {
                    if (comp instanceof PlayerPanel) {
                        PlayerPanel panel = (PlayerPanel) comp;
                        if (panel.getName().equals(iAgentList.get(0).getName())) {
                            panel.setPlayerPoints(points);
                        }
                    }

                }
            } catch (RemoteException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            if (points >= targetAmount) {
                try {
                    System.out.println(iAgentList.get(0).getName() + " has won with " + points + " points");
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return false;
            }
        }

        return true;
    }

    public int getPoints(String name) {
        int points = 0;
        ArrayList<IAgent> iAgentList = playersAgentsMap.get(name);
        for (Iterator<IAgent> iterator2 = iAgentList.iterator(); iterator2.hasNext();) {
            IAgent iAgent = (IAgent) iterator2.next();
            try {
                points = points + iAgent.getPoints();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return points;
    }

    private String getWinnerList() {
        String list = "";

        ArrayList<IPlayer> arrlist = sortPlayerAfterPoints();

        for (IPlayer i : arrlist) {
            try {
                list += i.getName();
                list += ": " + i.getPoints() + "\n";
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        try {
            if (iPlayerList.size() > 0) {
                if (playersAgentsMap.get(arrlist.get(0).getName()).get(0).getColor().equals(Color.decode(AgentUtils.ROT))) {
                    new SoundClip("red_team_is_the_winner");
                } else if (playersAgentsMap.get(arrlist.get(0).getName()).get(0).getColor().equals(Color.decode(AgentUtils.BLAU))) {
                    new SoundClip("blue_team_is_the_winner");
                } else {
                    new SoundClip("Flawless_victory");
                }
            } else {
                new SoundClip("players_left");
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return list;
    }

    public void disposeAll() {
        iPlayerList.entrySet().stream().map((pair) -> (IPlayer) pair.getValue()).forEachOrdered((player) -> {
            try {
                player.dispose();
            } catch (RemoteException ex) {
                System.exit(0);
            }
        });
        reset();
        dispose();
        System.exit(0);
    }

    private void reload() {
        if (running == false) {
            agentsOnSpawn = new String[][]{{"random", "random", "random"}, {"random", "random", "random"}, {"random", "random", "random"}};
            PlayerPanel.reset();
            playersAgentsMap.clear();
            agentList.clear();
            playerCount = 0;
            playerList.removeAll();
            iPlayerList.entrySet().forEach((pair) -> {
                IPlayer player = (IPlayer) pair.getValue();
                try {
                    player.setPoints(0);
                    player.resetStrategy();
                    addNewAgent(player, player.getStrategy());
                } catch (RemoteException ex) {
                    Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        }

    }

    private void reset() {
        PlayerPanel.reset();
        playersAgentsMap.clear();
        agentList.clear();
        playerCount = 0;
        playerList.removeAll();
        iPlayerList.clear();
        dominator = "";
        firstBlood = false;
    }

    public boolean checkSpawnTemp() {

        int xStart = playingField.getXyhome() - 1;
        int yStart = playingField.getXyhome() - 1;
        int home = playingField.getXyhome();
        boolean changed = false;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if (xStart + i != home || yStart + j != home) {
                    try {
                        String tmp;
                        if (playingField.getPlayingField()[xStart + i][yStart + j].getAgent() != null) {
                            tmp = playingField.getPlayingField()[xStart + i][yStart + j].getAgent().getName();
                        } else {
                            tmp = "SoWirdSichKeinSpielerNennen123";
                        }

                        //System.out.println("Compare '" + tmp + "' with '" + agentsOnSpawn[i][j] + "' : " + agentsOnSpawn[i][j].equals(tmp) + ", " + i + j);
                        if (!agentsOnSpawn[i][j].equals(tmp)) {
                            changed = true;
                        }
                        agentsOnSpawn[i][j] = tmp;

                    } catch (RemoteException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

            }
        }

        if (!changed) {
            playingField.setSpawnTemperature(playingField.getSpawnTemperature() + 1);
            //System.out.println("Temperature raised to " + spawnTemp);
        } else {
            playingField.setSpawnTemperature(0);
            //System.out.println("Temperature normal, set to 0");
        }

        if (playingField.getSpawnTemperature() >= 10) {
            new SoundClip("BallReset", -1);
            System.out.println("Temperature too hot, explosion");
            playingField.setSpawnTemperature(0);

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (xStart + i != home || yStart + j != home) {
                        IAgent agent = playingField.getPlayingField()[xStart + i][yStart + j].getAgent();
                        if (agent != null) {
                            playingField.spreadAgent(agent);
                        }
                    }

                }
            }

        }

        return false;
    }

    private ArrayList<IPlayer> sortPlayerAfterPoints() {
        ArrayList<IPlayer> arrlist = new ArrayList<IPlayer>();

        for (Map.Entry pair : iPlayerList.entrySet()) {
            IPlayer ip = (IPlayer) pair.getValue();
            arrlist.add(ip);
        }

        Collections.sort(arrlist, new Comparator<IPlayer>() {
            @Override
            public int compare(IPlayer p1, IPlayer p2) {
                try {
                    if (p1.getPoints() == p2.getPoints()) {
                        return 0;
                    } else if (p1.getPoints() > p2.getPoints()) {
                        return -1;
                    } else {
                        return 1;
                    }
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return 0;
            }
        });

        return arrlist;
    }

    private void checkPointSounds() {
        ArrayList<IPlayer> arrlist = sortPlayerAfterPoints();

        try {
            if (iPlayerList.size() > 1) {
                if (arrlist.get(0).getPoints() >= (arrlist.get(1).getPoints() + (playersAgentsMap.get(arrlist.get(1).getName()).get(0).getCapacity()) * 2)) {
                    if (!dominator.equals(arrlist.get(0).getName())) {
                        new SoundClip("Dominating");
                    }
                    dominator = arrlist.get(0).getName();
                }
            }

            if (iPlayerList.size() > 0) {
                if (arrlist.get(0).getPoints() > 0 && !firstBlood) {
                    new SoundClip("first_blood");
                    firstBlood = true;
                }
            }

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void initAndShowGUI() {
        // This method is invoked on Swing thread
        videoFrame = new JFrame("DEEP OCEAN MINING - THE FIRST RACE");
        videoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                player.pause();
                videoFrame.dispose();
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
        URI url2 = MultiAgent.class.getResource("./../resources/Intro_Final_v1.mp4").toURI();
        final BorderPane root = new BorderPane();

        final Media media = new Media("File://" + url2.getPath());
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
                player.setOnEndOfMedia(this);
                player.setOnStopped(this);
                player.setOnHalted(this);
                player.setOnError(this);

            }
        });

        return scene;
    }
}
