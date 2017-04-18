/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

/**
 *
 * @author lesley
 */
public class NotEnoughMoneyException extends Exception{
    //Parameterless Constructor
      public NotEnoughMoneyException() {}

      //Constructor that accepts a message
      public NotEnoughMoneyException(String message)
      {
         super(message);
      }
}
