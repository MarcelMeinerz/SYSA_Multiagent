package multiagent;

import java.awt.Color;
import java.io.Serializable;
import java.rmi.RemoteException;
import multiagent.remote.IAgent;

/**
 *
 * @author Marcel_Meinerz (marcel.meinerz@th-bingen.de)
 * @author Steffen_Hollenbach
 * @author Jasmin_Welschbillig
 *
 * @version 1.0
 */

public class Field implements Serializable {

    private int resources;
    private Color color;
    private IAgent agent;

    /**
     *
     * @param agent Agent der auf diesem Feld sitzt
     * @throws RemoteException
     */
    public Field(IAgent agent) throws RemoteException {
        this(0, agent.getColor(), agent);
    }

    /**
     *
     * @param resources Rohstoffe die auf diesem Feld liegen
     */
    public Field(int resources) {
        this(resources, Color.GRAY, null);
    }

    /**
     *
     * @param resources Rohstoffe die auf diesem Feld liegen
     * @param color Farbe des Spielers der auf diesem Feld ist
     */
    public Field(int resources, Color color) {
        this(resources, color, null);
    }

    /**
     *
     * @param resources Rohstoffe die auf diesem Feld liegen
     * @param color Farbe des Spielers der auf diesem Feld ist
     * @param agent Agent der auf diesem Feld sitzt
     */
    public Field(int resources, Color color, IAgent agent) {
        this.resources = resources;
        this.color = color;
        this.agent = agent;
    }

    /**
     * Diese Methode gibt die Anzahl der Rohstoffe, die auf diesem Feld liegen, zurück.
     * @return Anzahl der Rohstoffe
     */
    public int getResources() {
        return resources;
    }

    /**
     * Diese Methode setzt die Anzahl der Rohstoffe auf diesem Feld.
     * @param resources Rohstoff
     */
    public void setResources(int resources) {
        this.resources = resources;
    }

    /**
     * Diese Methode gibt die Farbe des Spielers zurück, der sich auf diesem Feld befindet zurück.
     * @return Spielerfarbe
     */
    public Color getColor() {
        return color;
    }

    /**
     * Diese Methode setzt die Farbe des Spielers, der sich auf dem Feld befindet.
     * @param color Farbe des Spielers
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Diese Methode gibt den Agenten, der sich auf diesem Feld befindet, zurück.
     * @return
     */
    public IAgent getAgent() {
        return agent;
    }

    /**
     * Diese Methode setzt einen Agent auf das Feld
     * @param agent Agent eines Spielers
     */
    public void setIAgent(IAgent agent) {
        this.agent = agent;
    }

}
