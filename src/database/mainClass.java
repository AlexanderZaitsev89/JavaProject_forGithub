/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
/**
 *
 * @author alexanza
 */
public class mainClass {
  
    public static void main(String[ ] args) {
        JFrame w = new JFrame("Amazing Application");
        w.add(new mainPanel (), BorderLayout.NORTH);       
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        w.pack( ); 
        w.setLocationRelativeTo(null);// for displaying window in the middle of the screen.should be used after pack() method
        w.setVisible(true);
    }  
}
