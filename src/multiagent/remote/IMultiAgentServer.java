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
public interface IMultiAgentServer extends Remote {

    /**
     * Diese Methode Uebergibt den Spieler an das Spiel
     * @param name  Name des Spielers
     * @param aThis  Strategy des Spielers
     * @return
     * @throws RemoteException
     */
    public boolean addPlayer(IPlayer name, IStrategy aThis) throws RemoteException;


    /**
     * Diese Methode gibt ein byte[] mit der IP-Adresse des Servers zurueck.
     * @return IP-Adresse des Servers
     * @throws RemoteException
     */
    public byte[] getIpAddr() throws RemoteException;

    /**
     * Diese Methode gibt einen String mit dem Hostname des Servers zurueck.
     * @return Hostname des Servers
     * @throws RemoteException
     */
    public String getHostname() throws RemoteException;
}
