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
     * @param agent
     * @throws RemoteException
     */
    public Field(IAgent agent) throws RemoteException {
        this(0, agent.getColor(), agent);
    }

    /**
     *
     * @param resources
     */
    public Field(int resources) {
        this(resources, Color.GRAY, null);
    }

    /**
     *
     * @param resources
     * @param color
     */
    public Field(int resources, Color color) {
        this(resources, color, null);
    }

    /**
     *
     * @param resources
     * @param color
     * @param agent
     */
    public Field(int resources, Color color, IAgent agent) {
        this.resources = resources;
        this.color = color;
        this.agent = agent;
    }

    /**
     *
     * @return
     */
    public int getResources() {
        return resources;
    }

    /**
     *
     * @param resources
     */
    public void setResources(int resources) {
        this.resources = resources;
    }

    /**
     *
     * @return
     */
    public Color getColor() {
        return color;
    }

    /**
     *
     * @param color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     *
     * @return
     */
    public IAgent getAgent() {
        return agent;
    }

    /**
     *
     * @param agent
     */
    public void setIAgent(IAgent agent) {
        this.agent = agent;
    }

}
