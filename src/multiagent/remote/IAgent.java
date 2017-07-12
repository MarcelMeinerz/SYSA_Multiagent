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
     *
     * @return
     * @throws RemoteException
     */
    public String getName() throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public int getPosx() throws RemoteException;

    /**
     *
     * @param posx
     * @throws RemoteException
     */
    public void setPosx(int posx) throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public int getPosy() throws RemoteException;

    /**
     *
     * @param posy
     * @throws RemoteException
     */
    public void setPosy(int posy) throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public int getCapacity() throws RemoteException;

    /**
     *
     * @param capacity
     * @throws RemoteException
     */
    public void setCapacity(int capacity) throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public int getLoad() throws RemoteException;

    /**
     *
     * @param load
     * @throws RemoteException
     */
    public void setLoad(int load) throws RemoteException;

    /**
     *
     * @param direction
     * @throws RemoteException
     */
    public void go(String direction) throws RemoteException;

    /**
     *
     * @throws RemoteException
     */
    public void take() throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public int check() throws RemoteException;

    /**
     *
     * @throws RemoteException
     */
    public void put() throws RemoteException;

    /**
     *
     * @param value
     * @throws RemoteException
     */
    public void put(int value) throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public String getOrder() throws RemoteException;

    /**
     *
     * @param order
     * @throws RemoteException
     */
    public void setOrder(String order) throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public Color getColor() throws RemoteException;

    /**
     *
     * @param color
     * @throws RemoteException
     */
    public void setColor(Color color) throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public IStrategy getStrategy() throws RemoteException;

    /**
     *
     * @param direction
     * @return
     * @throws RemoteException
     */
    public boolean requestField(String direction) throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public int getHomeXY() throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public boolean checkIfOnSpawn() throws RemoteException;
    
    /**
     *
     * @param x
     * @param y
     * @return
     * @throws RemoteException
     */
    public boolean checkIfOnSpawn(int x, int y) throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public int getPlanedPut() throws RemoteException;

    /**
     *
     * @param planedPut
     * @throws RemoteException
     */
    public void setPlanedPut(int planedPut) throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public int getPoints() throws RemoteException;

    /**
     *
     * @param points
     * @throws RemoteException
     */
    public void setPoints(int points) throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public int getTargetAmount()throws RemoteException;
    
    /**
     *
     * @throws RemoteException
     */
    public void buy()throws RemoteException; 
    
    /**
     *
     * @return
     * @throws RemoteException
     */
    public int getAgentsValue()throws RemoteException;
    
    /**
     *
     * @return
     * @throws RemoteException
     */
    public int getMaxAgents()throws RemoteException;
    
    /**
     *
     * @return
     * @throws RemoteException
     */
    public boolean hasEnoughToBuy() throws RemoteException;
    
    /**
     *
     * @return
     * @throws RemoteException
     */
    public boolean hasMaxAgents() throws RemoteException;
    
    /**
     *
     * @return
     * @throws RemoteException
     */
    public boolean checkSpawnIsPossible() throws RemoteException;
    
    /**
     *
     * @param i
     * @param j
     * @return
     * @throws RemoteException
     */
    public int getCustomData(int i, int j) throws RemoteException;

    /**
     *
     * @param i
     * @param j
     * @param data
     * @throws RemoteException
     */
    public void setCustomData(int i, int j, int data) throws RemoteException;
    
    /**
     *
     * @return
     * @throws RemoteException
     */
    public boolean buyPossible() throws RemoteException;
    
    /**
     *
     * @return
     * @throws RemoteException
     */
    public IAgent[] getAgentArray() throws RemoteException;

    /**
     *
     * @param agentArray
     * @throws RemoteException
     */
    public void setAgentArray(IAgent[] agentArray) throws RemoteException;
	
    /**
     *
     * @param x
     * @param y
     * @return
     * @throws RemoteException
     */
    public int getRememberResources(int x, int y)  throws RemoteException;

    /**
     *
     * @param resources
     * @throws RemoteException
     */
    public void setRememberResources(int resources)  throws RemoteException;
    
    /**
     *
     * @param x
     * @param y
     * @param resources
     * @throws RemoteException
     */
    public void setRememberResources(int x, int y, int resources)  throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public int getRememberFieldSize() throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public PlayingField getRememberField()  throws RemoteException;
    
    /**
     *
     * @param rememberField
     * @throws RemoteException
     */
    public void setRememberField(PlayingField rememberField) throws RemoteException;

    /**
     *
     * @throws RemoteException
     */
    public void initializeRememberField() throws RemoteException;

    /**
     *
     * @throws RemoteException
     */
    public void mergeRememberField() throws RemoteException;
	
}
