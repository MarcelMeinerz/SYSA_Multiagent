/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiagent.remote;

import java.awt.Color;
import java.rmi.Remote;
import java.rmi.RemoteException;
import multiagent.PlayingField;

/**
 *
 * @author Marcel_Meinerz (marcel.meinerz@th-bingen.de)
 * @author Steffen_Hollenbach
 * @author Jasmin_Welschbillig
 * 
 * @version 1.0
 */
public interface IAgent extends Remote {

    /**
     * Diese Method geibt den Name des Spielers zurueck.
     * @return Name des Spielers
     * @throws RemoteException
     */
    public String getName() throws RemoteException;

    /**
     * Diese Methode liefert die X-Koordinate zurueck, an der sich der Agent befindet.
     * @return X-Koordinate
     * @throws RemoteException
     */
    public int getPosx() throws RemoteException;

    /**
     * Methode zum setzen der X-Koordiante.
     * @param posx X-Koordiante
     * @throws RemoteException
     */
    public void setPosx(int posx) throws RemoteException;

    /**
     * Diese Methode liefert die Y-Koordinate zurueck, an der sich der Agent befindet.
     * @return Y-Koordinate
     * @throws RemoteException
     */
    public int getPosy() throws RemoteException;

    /**
     * Methode zum setzen der Y-Koordiante.
     * @param posy Y-Koordiante
     * @throws RemoteException
     */
    public void setPosy(int posy) throws RemoteException;

    /**
     * Diese Methode liefert die maximale Tragfaehigkeit des Agents.
     * @return maximale Tragfaehigkeit
     * @throws RemoteException
     */
    public int getCapacity() throws RemoteException;

    /**
     * Methode zum setzen der maximalen Tragfaehigkeit eines Agenten.
     * @param capacity maximalen Tragfaehigkeit eines Agenten
     * @throws RemoteException
     */
    public void setCapacity(int capacity) throws RemoteException;

    /**
     * Diese Methode liefert die momentane aufgenommene Ladung des Agents.
     * @return momentane aufgenommene Ladung
     * @throws RemoteException
     */
    public int getLoad() throws RemoteException;

    /**
     * Methode zum setzen der momentan aufgenommenen Ladung eines Agenten
     * @param load momentan aufgenommenen Ladung
     * @throws RemoteException
     */
    public void setLoad(int load) throws RemoteException;

    /**
     * Diese Methode setzt die Order des Agenten auf Bewegung in die angegebene Richtung direction.
     * @param direction
     * <br>Richtungen:
     * <br><BLOCKQUOTE>     - links : {@link gameclient.AgentUtils#LEFT } </BLOCKQUOTE>
     * <BLOCKQUOTE>     - rechts : {@link gameclient.AgentUtils#RIGHT } </BLOCKQUOTE>
     * <BLOCKQUOTE>     - hoch : {@link gameclient.AgentUtils#TOP } </BLOCKQUOTE>
     * <BLOCKQUOTE>     - runter : {@link gameclient.AgentUtils#BOTTOM } </BLOCKQUOTE>
     * @throws RemoteException
     */
    public void go(String direction) throws RemoteException;

    /**
     * Diese Methode setzt die Order des Agenten, dass er den Rohstoff auf seinem momentanen Feld aufnehmen soll.
     * @throws RemoteException
     */
    public void take() throws RemoteException;

    /**
     * Diese Methode setzt prueft ob sich Rohstoffe auf seinem momentanen Feld aufnehmen befinden.
     * @return Rohstoffe
     * @throws RemoteException
     */
    public int check() throws RemoteException;

    /**
     * Diese Methode setzt alle Rohstoffe die der Agent traegt auf dem Feld ab, auf dem er sich befindet.
     * {@link #put(int) }
     * @throws RemoteException
     */
    public void put() throws RemoteException;

    /**
     * Diese Methode setzt eine bestimmte Anzahl von Rohstoffe die der Agent traegt auf dem Feld ab, auf dem er sich befindet.
     * @param value Anzahl der Rohstoffe die abgelegt werden sollen.
     * {@link #put() }
     * @throws RemoteException
     */
    public void put(int value) throws RemoteException;

    /**
     * Diese Methode gibt die aktuelle Order des Agents zurueck.
     * @return Order
     * @throws RemoteException
     */
    public String getOrder() throws RemoteException;

    /**
     * Methode zum setzen der Order eines Spielers im Zug
     * @param order Order eines Spielers
     * @throws RemoteException
     */
    public void setOrder(String order) throws RemoteException;

    /**
     * Liefert die Spielerfarbe.
     * @return Spielerfarbe
     * @throws RemoteException
     */
    public Color getColor() throws RemoteException;

    /**
     * Methode zum setzen der Spielerfarbe
     * @param color Spielerfarbe
     * @throws RemoteException
     */
    public void setColor(Color color) throws RemoteException;

    /**
     * Liefert die Strategie des Spielers
     * @return Strategie des Spielers
     * @throws RemoteException
     */
    public IStrategy getStrategy() throws RemoteException;

    /**
     * Diese Methode prueft ob das Feld in der angebenen Richtung frei ist.
     * @param direction
     * <br>Richtungen:
     * <br><BLOCKQUOTE>     - links : {@link gameclient.AgentUtils#LEFT } </BLOCKQUOTE>
     * <BLOCKQUOTE>     - rechts : {@link gameclient.AgentUtils#RIGHT } </BLOCKQUOTE>
     * <BLOCKQUOTE>     - hoch : {@link gameclient.AgentUtils#TOP } </BLOCKQUOTE>
     * <BLOCKQUOTE>     - runter : {@link gameclient.AgentUtils#BOTTOM } </BLOCKQUOTE>
     * @return true wenn Feld in der angebenen Richtung frei, sonst false
     * @throws RemoteException
     */
    public boolean requestField(String direction) throws RemoteException;

    /**
     * Liefert die X- bzw. Y-Koordinate des Zentrums des PlayingFields.
     * Hierbei sind die Werte der X- und Y-Koordinate gleich.
     * @return Koordinate des Zentrums
     * @throws RemoteException
     */
    public int getHomeXY() throws RemoteException;

    /**
     * Prueft ob der Roboter sich auf einem Spawn-Feld befindet.
     * @return true wenn der Roboter sich auf einem Spawnfeld befindet, sonst false
     * {@link #checkIfOnSpawn(int, int)}
     * @throws RemoteException
     */
    public boolean checkIfOnSpawn() throws RemoteException;
    
    /**
     * Prüft ob das Feld mit den uebergebenen X-/Y-Koordinaten ein Spawn-Feld ist.
     * @param x X-Koordinate
     * @param y Y-Koordinate
     * @return true wenn der Roboter sich auf einem Spawnfeld befindet, sonst false
     * @throws RemoteException
     * {@link #checkIfOnSpawn()}
     */
    public boolean checkIfOnSpawn(int x, int y) throws RemoteException;

    /**
     * Liefert die geplannte aktuelle Ablagemenge von Rohstoffen.
     * @return geplannte aktuelle Ablagemenge von Rohstoffen
     * @throws RemoteException
     */
    public int getPlanedPut() throws RemoteException;

    /**
     * Methode zum gezielten ablegen von Rohstoffen.
     * @param planedPut Anzahl an Rohstoffen die abgelegt werden
     * @throws RemoteException
     */
    public void setPlanedPut(int planedPut) throws RemoteException;

    /**
     * Liefert den aktuellen Punktestand des Spielers zurueck.
     * @return aktuellen Punktestand des Spielers
     * @throws RemoteException
     */
    public int getPoints() throws RemoteException;

    /**
     * Methode zum setzen/aktualisieren des Punktestands.
     * @param points neuer Punktestand
     * @throws RemoteException
     */
    public void setPoints(int points) throws RemoteException;

    /**
     * Liefert die zu erzielende Punktzahl, die benoetigt wird um das Spiel zu gewinnen.
     * @return zu erzielende Punktzahl
     * @throws RemoteException
     */
    public int getTargetAmount()throws RemoteException;
    
    /**
     * Diese Methode setzt die Order des Agenten auf {@link gameclient.AgentUtils#BUY}
     * Mit Hilfe dieser Methode wird der Kaufvorgang initialisiert.
     * @throws RemoteException
     */
    public void buy()throws RemoteException; 
    
    /**
     * Liefert den Wert eines Roboters (Agent), der entrichtet werden muss um einen solchen zu erwerben.
     * @return Wert eines Roboters (Agent)
     * @throws RemoteException
     */
    public int getAgentsValue()throws RemoteException;
    
    /**
     * Liefert die maximale Anzahl an Robotern (Agent), die ein Spieler besitzen kann
     * @return maximale Anzahl an Robotern (Agent)
     * @throws RemoteException
     */
    public int getMaxAgents()throws RemoteException;
    
    /**
     * Gibt einen booleschen Wert zurueck, der darueber Auskunft gibt, ob die finanziellen Mittel vorhanden sind, einen neuen Roboter zu erwerben.
     * @return true wenn Roboter (Agent) gekauft werden kann, sonst false
     * @throws RemoteException
     */
    public boolean hasEnoughToBuy() throws RemoteException;
    
    /**
     * Gibt einen booleschen Wert zurueck, der angibt ob die maximale Roboteranzahl bereits erreicht wurde
     * @return true wenn maximale Roboteranzahl bereits erreicht, sonst false
     * {@link #getMaxAgents() }
     * @throws RemoteException
     */
    public boolean hasMaxAgents() throws RemoteException;
    
    /**
     * Prueft ob sich kein Agent des Spielers auf einem Spawn-Feld befindet
     * @return true wenn sich kein Agent des Spielers auf einem Spawn-Feld befindet, sonst false
     * @throws RemoteException
     */
    public boolean checkSpawnIsPossible() throws RemoteException;
    
    /**
     * Liefert aus dem multidimensionalen {@code Array} {@code CustomData} den Datensatz an der Stelle [i][j]
     * @param i maximale Anzahl an moeglichen Agents 
     * @param j moegliche Anzahl von zu speichernden Datensaetzen 
     * @return Wert der sich auf den angegebenen Koordinaten befindet. Default-Value = -1
     * {@link #getMaxAgents() }
     * @throws RemoteException
     */
    public int getCustomData(int i, int j) throws RemoteException;

    /**
     * Schreibt den uebergebenen Wert data in das multidimensionale Array CustomData an Stelle [i][j]
     * @param i maximale Anzahl an moeglichen Agents
     * @param j moegliche Anzahl von zu speichernden Datensaetzen
     * @param data Wert der an das Array uebergeben wird
     * {@link #getMaxAgents() } {@link #getCustomData(int, int) }
     * @throws RemoteException
     */
    public void setCustomData(int i, int j, int data) throws RemoteException;
    
    /**
     * Gibt einen booleschen Wert zurueck, der Auskunft gibt ob ein neuer Roboter gekauft werden kann 
     * <br>Bedingung: 
     * <br><BLOCKQUOTE>- finanzielle Mittel vorhanden, </BLOCKQUOTE>
     * <BLOCKQUOTE>- kein anderer Agent auf Spawn-Feld, </BLOCKQUOTE> 
     * <BLOCKQUOTE>- maximale Anzahl an Robotern wurde noch nicht erreicht  </BLOCKQUOTE>
     * @return true wenn Bedingungen erfüllt sind, sonst false
     * {@link #hasEnoughToBuy() } {@link #checkIfOnSpawn() } {@link #hasMaxAgents() }
     * @throws RemoteException
     */
    public boolean buyPossible() throws RemoteException;
    
    /**
     * Liefert ein {@code Array} mit allen Agenten des Spielers zurueck
     * @return Array mit allen Agenten des Spielers
     * {@link #this}
     * @throws RemoteException
     */
    public IAgent[] getAgentArray() throws RemoteException;

    /**
     * Methode zum setzen des {@code Array} mit allen Agenten des Spielers
     * @param agentArray {@code Array} mit allen Agenten des Spielers
     * @throws RemoteException
     */
    public void setAgentArray(IAgent[] agentArray) throws RemoteException;
	
    /**
     * Liefert die für das Feld mit den uebergebenen X-/Y-Koordinaten gemerkte Anzahl an Ressourcen.
     * @param x Koordinate x im RememberField
     * @param y Koordinate y im RememberField
     * @return Wert der an der Stelle x,y liegt
     * @throws RemoteException
     */
    public int getRememberResources(int x, int y)  throws RemoteException;

    /**
     * Traegt in das {@code RememberField} an der eigenen Position die uebergebene Anzahl an Ressourcen ein.
     * @param resources Wert der in das eigene Feld eingetragen wird
     * {@link #setRememberResources(int, int, int) }
     * @throws RemoteException
     */
    public void setRememberResources(int resources)  throws RemoteException;
    
    /**
     * Traegt in das {@code RememberField} an der uebergebenen X-/Y-Position die übergebene Anzahl an Ressourcen ein.
     * @param x Koordinate x im RememberField
     * @param y Koordinate y im RememberField
     * @param resources Wert der in das eigene Feld eingetragen wird
     * {@link #setRememberResources(int) }
     * @throws RemoteException
     */
    public void setRememberResources(int x, int y, int resources)  throws RemoteException;

    /**
     * Liefert die Groesse des Spielfeldes zurueck.
     * @return Groesse des Spielfeldes
     * @throws RemoteException
     */
    public int getRememberFieldSize() throws RemoteException;

    /**
     * Diese Methode gibt das {@code RememberField} zurueck.
     * @return {@code RememberField}
     * @throws RemoteException
     */
    public PlayingField getRememberField()  throws RemoteException;
    
    /**
     * Diese Methode setzt das {@code RememberField}.
     * @param rememberField {@code RememberField}
     * @throws RemoteException
     */
    public void setRememberField(PlayingField rememberField) throws RemoteException;

    /**
     * Ueberschreibt die {@code RememberField} der anderen Agenten des Spielers mit dem {@code RememberField} des aufrufenden Agenten.
     * @throws RemoteException
     */
    public void mergeRememberField() throws RemoteException;
	
}
