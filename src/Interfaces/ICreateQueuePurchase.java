/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

/**
 *
 * @author lesley
 */
 public interface ICreateQueuePurchase extends java.rmi.Remote {
    /**
    * Returns a boolean if the Queue_Purchase is created or not.
    * The placerID argument specifies the id of the user that places this Queue_Purchase.
    * The productID is the id of the product the user wants to buy.
    * The quantity argument must specify the amount of products the user wants to buy.
    * Then the minPrice and maxPrice determine the price range the user wants to spend for the product.
    * <p>
    * This method always returns immediately, wheter or not the queue purchase is created.
    * When the return value is true, the queue purchase is created and the user will be redirected to the new queue purchase.
    * When the return value is false, the user will get an error message why the queue purchase isn't created and will have to try it again.
    *
    * @param Quantity  The amount of products the user wants to buy
    * @param minPrice  The lowest price the user wants to spend for the product. minPrice >= 0
    * @param maxPrice  The maximum price the user wants to spend for the product. maxPrice > 0
    * @param productID determines the id of the product the user wants to buy
    * @param placerID  determines the id of the user that places this queue purchase
    * @return          true if the queue purchase is created or false if the queue purchase isn't created
    * @exception java.rmi.RemoteException  this method can throw a RemoteException since it uses RMI
    */
    public boolean createQueuePurchase(int Quantity, double minPrice, double maxPrice, int productID, int placerID)
            throws java.rmi.RemoteException;
}
