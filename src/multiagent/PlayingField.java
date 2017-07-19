package multiagent;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import multiagent.gui.ServerFrame;
import multiagent.remote.IAgent;
import multiagent.remote.IPlayer;
import multiagent.util.AgentUtils;

/**
 *
 *
 * @author Marcel_Meinerz (marcel.meinerz@th-bingen.de)
 * @author Steffen_Hollenbach
 * @author Jasmin_Welschbillig
 *
 * @version 1.0
 */
public class PlayingField implements Serializable {

    private Field[][] playingField;
    private int size;
    private int tightness;
    private int max;
    private int xyhome;
    private int agentsValue;
    private HashMap<String, IPlayer> iPlayerList;
    private int targetAmount;
    private ArrayList<IAgent> list;
    private int count, playerCount, maxAgents;
    private ArrayList<int[]> spawnFields;
    private int spawnTemperature;

    /**
     * Konstruktor zum erstellen eines Spielfeldes
     *
     * @param max Maximale Anzahl von Rohstoffen auf einem Feld
     * @param size Dimension des Spielfeldes
     * @param tightness Verhältnismaeßiges Vorkommen von Rohstoffen auf dem
     * Spielfeld
     * @param list Liste aller Agenten auf dem Spielfeld
     * @param playerCount Anzahl der Spieler im Spiel
     * @param maxAgents Maximale Anzahl der moeglichen Agenten auf dem Spielfeld
     * pro Spieler
     */
    public PlayingField(int max, int size, int tightness, ArrayList<IAgent> list, int playerCount, int maxAgents) {
        this.max = max;
        this.size = size;
        this.tightness = tightness;
        this.list = list;
        this.playerCount = playerCount;
        this.maxAgents = maxAgents;
        playingField = new Field[size][size];
        xyhome = size / 2;
        spawnFields = new ArrayList<>();
        count = 0;
        spawnTemperature = 0;

    }

    /**
     * Konstruktor zum erstellen eines leeren Spielfeldes
     *
     * @param size
     */
    public PlayingField(int size) {
        this.size = size;
        playingField = new Field[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                playingField[i][j] = new Field(-1);
            }
        }

    }

    /**
     * Initialisierungsmethode für das Spielfeld, für Werte und Agenten
     */
    public void initPlayingField() {
        //i=y, j=x
        spawnFields.clear();
        xyhome = size / 2;
        count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ((i >= xyhome - 1 && i <= xyhome + 1) && (j >= xyhome - 1 && j <= xyhome + 1) && !(i == xyhome && j == xyhome)) {
                    // Spawn

                    spawnFields.add(new int[]{i, j});

                    if ((count < playerCount)) {
                        try {
                            IAgent agent = list.get(count++);
                            agent.setPosx(i);
                            agent.setPosy(j);
                            playingField[i][j] = new Field(agent);
                        } catch (RemoteException ex) {
                            Logger.getLogger(PlayingField.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        playingField[i][j] = new Field(0);
                    }
                } else if (i == xyhome && j == xyhome) {
                    // Mittel
                    playingField[i][j] = new Field(0);
                } else {
                    // Spielfeld
                    int random = (int) Math.round(Math.random() * 100);
                    if (random <= tightness) {
                        playingField[i][j] = new Field((int) Math.round(Math.random() * max));
                    } else {
                        playingField[i][j] = new Field(0);
                    }
                }
            }
        }
    }

    /**
     * Prueft ob sich im Umkreis von einem Agent weitere Agenten befinden und
     * der Weg frei ist zum laufen.
     *
     * @param agent Agent der seine Umgebung durchsuchen will
     * @param direction Richtung in die der Agent gehen will
     * @param agentArray Array aller Agenten
     * @return true wenn sich auf der Zielkoordiante des Agenten kein weiterer
     * Agent befindet, sonst false
     */
    public boolean requestField(IAgent agent, String direction, IAgent agentArray[]) {
        try {
            int x = agent.getPosx();
            int y = agent.getPosy();

            switch (direction) {
                case AgentUtils.LEFT: // links
                    if ((x > 0) && ((x - 1 != size / 2) || (y != size / 2))) {
                        if (playingField[x - 1][y].getAgent() == null) {
                            if (agent.checkIfOnSpawn(x - 1, y)) { //Naechster Zug endet auf Home-Feld
                                if (agent.checkSpawnIsPossible()) { //Es steht noch kein anderer auf dem Feld
                                    //Alles ok
                                } else if (!agent.checkIfOnSpawn()) { //Der Agent befindet sich bereits auf einem Home-Feld
                                    //Wenn nicht kein zugang moeglich
                                    return false;
                                }
                            }
                            x -= 1;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                    break;
                case AgentUtils.TOP: // oben
                    if ((y > 0) && ((y - 1 != size / 2) || (x != size / 2))) {
                        if (playingField[x][y - 1].getAgent() == null) {
                            if (agent.checkIfOnSpawn(x, y - 1)) { //Naechster Zug endet auf Home-Feld
                                if (agent.checkSpawnIsPossible()) { //Es steht noch kein anderer auf dem Feld
                                    //Alles ok
                                } else if (!agent.checkIfOnSpawn()) { //Der Agent befindet sich bereits auf einem Home-Feld
                                    //Wenn nicht kein zugang moeglich
                                    return false;
                                }
                            }
                            y -= 1;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                    break;
                case AgentUtils.RIGHT: // rechts
                    if ((x < size - 1) && ((x + 1 != size / 2) || (y != size / 2))) {
                        if (playingField[x + 1][y].getAgent() == null) {
                            if (agent.checkIfOnSpawn(x + 1, y)) { //Naechster Zug endet auf Home-Feld
                                if (agent.checkSpawnIsPossible()) { //Es steht noch kein anderer auf dem Feld
                                    //Alles ok
                                } else if (!agent.checkIfOnSpawn()) { //Der Agent befindet sich bereits auf einem Home-Feld
                                    //Wenn nicht kein zugang moeglich
                                    return false;
                                }
                            }
                            x += 1;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                    break;
                case AgentUtils.BOTTOM: // unten
                    if ((y < size - 1) && ((y + 1 != size / 2) || (x != size / 2))) {
                        if (playingField[x][y + 1].getAgent() == null) {
                            if (agent.checkIfOnSpawn(x, y + 1)) { //Naechster Zug endet auf Home-Feld
                                if (agent.checkSpawnIsPossible()) { //Es steht noch kein anderer auf dem Feld
                                    //Alles ok
                                } else if (!agent.checkIfOnSpawn()) { //Der Agent befindet sich bereits auf einem Home-Feld
                                    //Wenn nicht kein zugang moeglich
                                    return false;
                                }
                            }
                            y += 1;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                    break;
            }

            return true;
        } catch (RemoteException ex) {
            Logger.getLogger(PlayingField.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    /**
     * Besetzt das Feld im Spielfeld mit einem Agenten
     *
     * @param xPos X-Koordiante des Feldes
     * @param yPos Y-Koordiante des Feldes
     * @param value Agent der auf das Feld gesetzt wird
     */
    public void setOccupancy(int xPos, int yPos, IAgent value) {
        playingField[xPos][yPos].setIAgent(value);
    }

    /**
     * Diese Methode verteilt den Agent irgendwo auf dem Spielfeld.
     *
     * @param agent Agent der die Position wechselt. Ueblicherweise wird diese
     * Methode für alle Agenten aufgerufen, wenn die Agenten den Kern
     * blockieren.
     */
    public void spreadAgent(IAgent agent) {
        try {
            int xOld = agent.getPosx();
            int yOld = agent.getPosy();

            int x;
            int y;

            do {
                x = 0 + (int) (Math.random() * ((getSize() - 1 - 0) + 0));
                y = 0 + (int) (Math.random() * ((getSize() - 1 - 0) + 0));

            } while (playingField[x][y].getAgent() != null);

            // old Field
            setOccupancy(xOld, yOld, null);
            // new Field
            setOccupancy(x, y, agent);
            agent.setPosx(x);
            agent.setPosy(y);

        } catch (RemoteException ex) {
            Logger.getLogger(PlayingField.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Diese Methode bewegt den Agenten auf die ihm vorgegebene Position
     * (direction)
     *
     * @param agent Agent der auf eine neu Position gesetzt wird
     * @param direction Richtung, die die neue Position ermittelt.
     */
    public void moveAgent(IAgent agent, String direction) {
        try {
            boolean moved = false;
            int x = agent.getPosx();
            int y = agent.getPosy();

            int xOld = agent.getPosx();
            int yOld = agent.getPosy();

            switch (direction) {
                case AgentUtils.LEFT: // links
                    if ((x > 0) && ((x - 1 != size / 2) || (y != size / 2))) {
                        if (playingField[x - 1][y].getAgent() == null) {
                            if (agent.checkIfOnSpawn(x - 1, y)) { //N�chster Zug endet auf Home-Feld
                                if (agent.checkSpawnIsPossible()) { //Es steht noch kein anderer auf dem Feld
                                    //Alles ok
                                } else if (!agent.checkIfOnSpawn()) { //Der Agent befindet sich bereits auf einem Home-Feld
                                    //Wenn nicht kein zugang m�glich
                                    break;
                                }
                            }
                            x -= 1;
                            moved = true;
                        } else {
                            break;
                        }
                    }
                    break;
                case AgentUtils.TOP: // oben
                    if ((y > 0) && ((y - 1 != size / 2) || (x != size / 2))) {
                        if (playingField[x][y - 1].getAgent() == null) {
                            if (agent.checkIfOnSpawn(x, y - 1)) { //N�chster Zug endet auf Home-Feld
                                if (agent.checkSpawnIsPossible()) { //Es steht noch kein anderer auf dem Feld
                                    //Alles ok
                                } else if (!agent.checkIfOnSpawn()) { //Der Agent befindet sich bereits auf einem Home-Feld
                                    //Wenn nicht kein zugang m�glich
                                    break;
                                }
                            }
                            y -= 1;
                            moved = true;
                        } else {
                            break;
                        }

                    }
                    break;
                case AgentUtils.RIGHT: // rechts
                    if ((x < size - 1) && ((x + 1 != size / 2) || (y != size / 2))) {
                        if (playingField[x + 1][y].getAgent() == null) {
                            if (agent.checkIfOnSpawn(x + 1, y)) { //N�chster Zug endet auf Home-Feld
                                if (agent.checkSpawnIsPossible()) { //Es steht noch kein anderer auf dem Feld
                                    //Alles ok
                                } else if (!agent.checkIfOnSpawn()) { //Der Agent befindet sich bereits auf einem Home-Feld
                                    //Wenn nicht kein zugang m�glich
                                    break;
                                }
                            }
                            x += 1;
                            moved = true;
                        } else {
                            break;
                        }

                    }
                    break;
                case AgentUtils.BOTTOM: // unten
                    if ((y < size - 1) && ((y + 1 != size / 2) || (x != size / 2))) {
                        if (playingField[x][y + 1].getAgent() == null) {
                            if (agent.checkIfOnSpawn(x, y + 1)) { //N�chster Zug endet auf Home-Feld
                                if (agent.checkSpawnIsPossible()) { //Es steht noch kein anderer auf dem Feld
                                    //Alles ok
                                } else if (!agent.checkIfOnSpawn()) { //Der Agent befindet sich bereits auf einem Home-Feld
                                    //Wenn nicht kein zugang m�glich
                                    break;
                                }
                            }
                            y += 1;
                            moved = true;
                        } else {
                            break;
                        }

                    }
                    break;
            }
            if (moved) {
                try {
                    assert xOld!=x || yOld!=y : "Agents doesn't. move";
                } catch (AssertionError err) {
                    Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, err);
                    return;
                }
                // old Field
                setOccupancy(xOld, yOld, null);
                // new Field
                setOccupancy(x, y, agent);
                agent.setPosx(x);
                agent.setPosy(y);
            }

        } catch (RemoteException ex) {
            Logger.getLogger(PlayingField.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Diese Methode gibt die Anzahl der Rohstoffe zurueck die sich im Feld der
     * angegebenen Koordianten befinden
     *
     * @param xPos X-Koordiante auf dem Spielfeld
     * @param yPos Y-Koordinate auf dem Spielfeld
     * @return Anzahl an Rohstoffen
     */
    private int getResources(int xPos, int yPos) {
        return playingField[xPos][yPos].getResources();
    }

    /**
     * Diese Methode gibt die Anzahl der Rohstoffe zurueck, welche sich auf dem
     * momentanen Feld des Agenten befinden.
     *
     * @param agent Agent im Spielfeld
     * @return Anzahl an Rohstoffen
     */
    public int getResources(IAgent agent) {
        try {
            return getResources(agent.getPosx(), agent.getPosy());
        } catch (RemoteException ex) {
            Logger.getLogger(PlayingField.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    /**
     * Diese Methode setzt eine Menge von Rohstoffen auf das Feld, welches den
     * Agenten haelt.
     *
     * @param agent Agent im Spielfeld
     * @param value Menge an Rohstoffen
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void setResources(IAgent agent, int value) {
        try {
            int xPos = agent.getPosx();
            int yPos = agent.getPosy();

            if (value > agent.getLoad()) {
                value = agent.getLoad();
            }
            if (agent.checkIfOnSpawn()) {
                iPlayerList.get(agent.getName()).setPoints(iPlayerList.get(agent.getName()).getPoints() + value);
            } else {
                playingField[xPos][yPos].setResources(this.getResources(agent) + value);
                new SoundClip("bottom_feeder");
            }
            agent.setLoad(agent.getLoad() - value);

        } catch (RemoteException ex) {
            Logger.getLogger(PlayingField.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Traegt in das {@code RememberField} an der Position des Agenten die
     * übergebene Anzahl an Ressourcen ein.
     *
     * @param agent Agent im Spielfeld
     * @param value Wert der in das eigene Feld eingetragen wird
     *
     */
    public void setRememberResources(IAgent agent, int value) {
        try {
            int xPos = agent.getPosx();
            int yPos = agent.getPosy();

            if (xPos >= 0 && xPos < agent.getRememberFieldSize() && yPos >= 0 && yPos < agent.getRememberFieldSize()) {
                playingField[xPos][yPos].setResources(value);
            } else {
                System.out.println("Fehler in PlayingField.setRememberResources(agent, value): xPos = " + xPos + ", yPos = " + yPos + ", value = " + value);
            }

        } catch (RemoteException ex) {
            Logger.getLogger(PlayingField.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Traegt in das {@code RememberField} an der uebergebenen X-/Y-Position die
     * übergebene Anzahl an Ressourcen ein.
     *
     * @param x Koordinate x im RememberField
     * @param y Koordinate y im RememberField
     * @param value Wert der in das eigene Feld eingetragen wird
     *
     */
    public void setRememberResources(int x, int y, int value) {
        playingField[x][y].setResources(value);
    }

    /**
     * Diese Methode uebergibt die Ressourcen an der Position des Agenten
     *
     * @param agent Agent der die Rohstoffe traegt
     */
    public void takeResources(IAgent agent) {
        try {
            int xPos = agent.getPosx();
            int yPos = agent.getPosy();

            if ((agent.getCapacity() - agent.getLoad() > 0) && (playingField[xPos][yPos].getResources() > 0)) {
                try {
                    assert playingField[xPos][yPos].getResources() > 0  : "No resource in field";
                } catch (AssertionError err) {
                    Logger.getLogger(ServerFrame.class.getName()).log(Level.SEVERE, null, err);
                    return;
                }
                agent.setLoad(agent.getLoad() + 1);
                playingField[xPos][yPos].setResources(playingField[xPos][yPos].getResources() - 1);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(PlayingField.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Diese Methode gibt das Spielfeld als zwei-dimensionales Array zuruek.
     *
     * @return Spielfeld {@link Field}
     */
    public Field[][] getPlayingField() {
        return playingField;
    }

    /**
     * Diese Methode gibt die Mittelpunkt Koordiante zurueck x=y
     *
     * @return x=y
     */
    public int getXyhome() {
        return xyhome;
    }

    /**
     * Diese Methode setzt die Anzahl der Spieler im Spiel
     *
     * @param playerCount
     */
    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    /**
     * Diese Methode gibt die maximale Anzahl von Agenten pro Spieler zurueck.
     *
     * @return maximale Anzahl von Agenten
     */
    public int getMaxAgents() {
        return maxAgents;
    }

    /**
     * DIese Methode setzt die maximale Anzahl von Agenten pro Spieler
     *
     * @param maxAgents maximale Anzahl von Agenten
     */
    public void setMaxAgents(int maxAgents) {
        this.maxAgents = maxAgents;
    }

    /**
     * Diese Methode gibt die Felder Felder zurueck, auf denen Agenten gebaut
     * werden koennen.
     *
     * @return Liste mit {@link Field} Array. {@link ArrayList}
     */
    public ArrayList<int[]> getSpawnFields() {
        return spawnFields;
    }

    /**
     * Diese Methode setzt die Spielfelddimensionen.
     *
     * @param size Spielfelddimension (size,size)
     */
    public void setSize(int size) {
        this.size = size;
        playingField = new Field[size][size];
        xyhome = size / 2;
    }

    /**
     * Diese Methode gibt die groesse des Spielfeldes zurueck
     *
     * @return groesse des Spielfeldes
     */
    public int getSize() {
        return size;
    }

    /**
     * Diese Mehtode setzt den Wert fuer die prozentuallen Vorkommnisse von
     * Rohstoffen.
     *
     * @param tightness prozentuallen Vorkommnisse von Rohstoffen
     */
    public void setTightness(int tightness) {
        this.tightness = tightness;
    }

    /**
     * Diese methode setzt die maximale Anzahl an Rohstoffen pro Feld.
     *
     * @param max maximale Anzahl an Rohstoffen
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Diese Methode prueft ob sich ein Agent auf den angegebenen Koordianetn
     * befindet.
     *
     * @param x X-Koordiante
     * @param y Y-Koordiante
     * @return true wenn sich kein Agent auf den angegebenen Koordianetn
     * befindet, sonst false
     */
    public boolean requestField(int x, int y) {
        return playingField[x][y].getAgent() == null;
    }

    /**
     * Diese Methode gibt die momentane \"Temperatur\" zurueck.
     *
     * @return momentane \"Temperatur\"
     */
    public int getSpawnTemperature() {
        return spawnTemperature;
    }

    /**
     * Diese Methode setzt die momentane \"Temperatur\" .
     *
     * @param spawnTemperature momentane \"Temperatur\"
     */
    public void setSpawnTemperature(int spawnTemperature) {
        this.spawnTemperature = spawnTemperature;
    }

    /**
     * Diese Methode gibt eine {@link HashMap} zurueck mit allen Spielern.
     *
     * @return {@link HashMap}
     */
    public HashMap<String, IPlayer> getiPlayerList() {
        return iPlayerList;
    }

    /**
     * Diese Methode setzt eine {@link HashMap} mit allen Spielern
     *
     * @param iPlayerList {@link HashMap}
     */
    public void setiPlayerList(HashMap<String, IPlayer> iPlayerList) {
        this.iPlayerList = iPlayerList;
    }

    /**
     * Diese Methode gibt den aktuellen Wert eines Agenten zurueck
     *
     * @return Wert eines Agenten
     */
    public int getAgentsValue() {
        return agentsValue;
    }

    /**
     * Diese Methode setzt den Wert eines Agenten
     *
     * @param agentsValue Wert eines Agenten
     */
    public void setAgentsValue(int agentsValue) {
        this.agentsValue = agentsValue;
    }

    /**
     * Liefert die zu erzielende Punktzahl, die benoetigt wird um das Spiel zu
     * gewinnen.
     *
     * @return zu erzielende Punktzahl
     */
    public int getTargetAmount() {
        return targetAmount;
    }

    /**
     * Setzt die zu erzielende Punktzahl, die benoetigt wird um das Spiel zu
     * gewinnen.
     *
     * @param targetAmount zu erzielende Punktzahl
     */
    public void setTargetAmount(int targetAmount) {
        this.targetAmount = targetAmount;
    }

}
