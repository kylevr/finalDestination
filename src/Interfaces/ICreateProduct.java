/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.rmi.Remote;

/**
 *
 * @author lesley
 */
 public interface ICreateProduct extends Remote {
    /**
    * Returns a boolean if the Product is created or not.
    * The GTIN argument specifies the specific number of a product.
    * The name argument is the name of a product.
    * The description argument is the description of a product
    * <p>
    * This method always returns immediately, wheter or not the product is created.
    * When the return value is true, the product is created and the user will be redirected to the home page.
    * When the return value is false, the user will get an error message why the product isn't created and will have to try it again.
    *
    * @param GTIN        determines the unique number of a product
    * @param name        is the name of a product and can't be empty
    * @param description is the descriptioin of a product and can't be empty
    * @return            true if the product is created or false if the product isn't created
    * @exception java.rmi.RemoteException  this method can throw a RemoteException since it uses RMI
    */
    public boolean createProduct(int GTIN, String name, String description)
            throws java.rmi.RemoteException;
}
