/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Classes.User;
import java.rmi.RemoteException;

/**
 *
 * @author piete
 */
public interface IAuthorized {
    User login (String username, String password) throws RemoteException;
}
