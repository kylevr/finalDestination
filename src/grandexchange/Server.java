/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grandexchange;

import Classes.Grand_Exchange;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Jorian
 */
public class Server {

    // Set port number
    private static final int portNumber = 1099;

    // Set binding name for Grand Exchange
    private static final String bindingName = "GrandExchangeServer";

    // References to registry and Grand Exchange
    private Registry registry = null;
    private Grand_Exchange GE = null;
        
    public Server(){
        
        // Print port number for registry
        System.out.println("Server: Port number " + portNumber);

        // Create Grand Exchange
        try {
            GE = new Grand_Exchange();
            System.out.println("Server: Grand Exchange created !");
        } 
        catch (RemoteException ex) {
            System.out.println("Server: Cannot create Grand Exchange Object");
            System.out.println("Server: RemoteException: " + ex.getMessage());
            GE = null;
        }

        // Create registry at port number
        try {
            registry = LocateRegistry.createRegistry(portNumber);
            System.out.println("Server: Registry created on port number " + portNumber);
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot create registry");
            System.out.println("Server: RemoteException: " + ex.getMessage());
            registry = null;
        }

        // Bind Grand Exchange using registry
        try {
            registry.rebind(bindingName, GE);
        } catch (RemoteException ex) {
            System.out.println("Server: Cannot bind Grand Exchange");
            System.out.println("Server: RemoteException: " + ex.getMessage());
        }
    }    
        
    public static void main(String[] args) {
        Server server = new Server();
    }
    
}
