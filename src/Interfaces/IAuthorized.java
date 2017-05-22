/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Classes.User;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author piete
 */
public interface IAuthorized extends Remote{
    
    /**
     * logs a user in the application.
     * boolean isAuthorized of this user is set to true.
     * @param username
     * @param password
     * @return the user that will be logged into the application. Null if no user is found with given username and password.
     * @throws RemoteException
     */
    public User login (String username, String password) throws RemoteException;

    /**
     * logs user with specified username out of the application.
     * boolean isAuthorized of this user is set to false.
     * @param username
     * @return
     * @throws RemoteException
     */
    public Void logout(String username) throws RemoteException;

    /**
     * registers a new user to the application.
     * @param username
     * @param password
     * @param alias
     * @param email
     * @return "Failed to register user:: + (specific errormessages)" when failed, and "Succesfully registered new user!" if successful 
     * @throws RemoteException
     */
    public String registerUser(String username, String password, String alias, String email) throws RemoteException;

    /**
     * sets if user with specified username will be authorized or not
     * @param username username of user you want to (de)authorize
     * @param isAuthorized 
     * @return
     * @throws RemoteException
     */
    public boolean setIsAuthorized(String username, boolean isAuthorized) throws RemoteException;
}
