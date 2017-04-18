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
public class EmptyFieldException extends Exception{
    //Parameterless Constructor
      public EmptyFieldException() {}

      //Constructor that accepts a message
      public EmptyFieldException(String message)
      {
         super(message);
      }
}
