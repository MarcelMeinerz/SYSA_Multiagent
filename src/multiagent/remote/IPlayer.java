/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiagent.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;



/**
 *
 * @author Marcel_Meinerz (marcel.meinerz@th-bingen.de)
 * @author Steffen_Hollenbach
 * @author Jasmin_Welschbillig
 * 
 * @version 1.0
 */
public interface IPlayer extends Remote{

    /**
     * Methode zum Verbindungsaufbau mit dem Server
     * @param name Name des Spielers
     * @param strat Strategy des Spielers
     * @return true wenn Verbindung aufgebaut wurde, sonst false
     * @throws RemoteException
     */
    public boolean connect(String name, IStrategy strat) throws RemoteException;
            
    /**
     * Liefert den Name des Spielers
     * @return Name des Spielers
     * @throws RemoteException
     */
    public String getName()throws RemoteException;

    /**
     * Liefert die Startegy des Spielers.
     * @return Startegy des Spielers
     * @throws RemoteException
     */
    public IStrategy getStrategy() throws RemoteException;
    
    /**
     * Methode zum setzen der Punkte des Spielers.
     * @param points Punkte des Spielers
     * @throws RemoteException
     */
    public void setPoints(int points) throws RemoteException;
    
    /**
     * Liefert die Punkte des Spielers.
     * @return Punkte des Spielers
     * @throws RemoteException
     */
    public int getPoints() throws RemoteException;
    
    /**
     * Methode zum schliessen des Clients.
     * @throws RemoteException
     */
    public void dispose() throws RemoteException;
    
    /**
     * Methode zum setzen des Namens des Spielers
     * @param name Name des Spielers
     * @throws RemoteException
     */
    public void setName(String name) throws RemoteException;
    
    /**
     * Methode zum neustart der Strategie.
     * @throws RemoteException
     */
    public void resetStrategy() throws RemoteException;
}
