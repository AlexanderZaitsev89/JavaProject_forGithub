/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author alexanza
 */
public class mainPanel extends JPanel {
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Alex
 */

    private JPanel form,buttons,text;
    private JButton b1, b2,b3,b4,b5;
    private JTextArea forOUTPUT;
    private JLabel Lname,LlastName,Lnationality,Lprofession,Lyear;
    private JTextField Tname,TlastName,Tyear;  
    private JComboBox Cnationality,Cprofession;
    private String []nationalities={"Select nationality...","Danish","English","Estonian","Finnish","German","Russian","Swedish"};
    private String []professions={"Select profession...","Doctor","IT engineer","Pilot","Farmer","Fireman","Artist","Footballer"};
    private String fileName="dataStorage.txt";
    private int year;
    File file = new File(fileName);// This does not actually create a file
    private String resultOfReading;
    private String temp="";
    private int last=0;
    private JTable table;
    private ResultSet r;
    private Statement s;
    Connection c ;
        public mainPanel ( ) {

        
        //at first  3 main panell
        text=new JPanel();
        form=new JPanel();
        buttons=new JPanel();
        
        // Component for the text panel
        forOUTPUT = new JTextArea("Welcome to this application!!"
                +"\n"+ "Our company wishes you a pleasent expereince with it"+"\n"
                + "At first connect to database and then add some records, you can print and update them"+"\n"
        +"(if you put the curcon on a button or form question-you woould see some tips)");
        
        // Component for the form panel  
        Lname = new JLabel("Enter your name:");
        LlastName = new JLabel("Enter your last name name:");
        Lnationality = new JLabel("Choose your nationality from the list:");
        Lprofession = new JLabel("Choose your profession from the list:");
        Tname= new JTextField(30);
        TlastName= new JTextField(30);       
        Cnationality=new JComboBox(nationalities);
        Cprofession=new JComboBox(professions);
               
        // Components for buttons panel
        b1 = new JButton("Connecting to database");      
        b2 = new JButton("Add record");        
        b3 = new JButton("Read the information from DB");
        b4 = new JButton("Update a record"); 
        b5 = new JButton("Close connection to DB");
       
        //Setting layout for main window and adding 3 panels to it
        this.setLayout(new GridLayout(3,1));
       
        this.add(text);
        this.add(form);
        this.add(buttons);
        
        //Setting layout for text window and adding components to it
        text.setBorder(BorderFactory.createTitledBorder("Output Window"));
        text.add(forOUTPUT);
        
        //Setting layout for form window and adding components to it
        form.setLayout(new GridLayout(4,2,10,10));
        form.setBorder(BorderFactory.createTitledBorder("That's a form"));
        form.add(Lname);
        Lname.setToolTipText("Enter the name as it is written in your passport");
        form.add(Tname);      
        form.add(LlastName);
        LlastName.setToolTipText("Enter the last name as it is written in your passport");
        form.add(TlastName);
        form.add(Lnationality );
        Lnationality.setToolTipText("Just choose somethin,no big deal");
        form.add(Cnationality );
        form.add(Lprofession);
        Lprofession.setToolTipText("Just choose somethin,no big deal");
        form.add(Cprofession);
        
        //Setting layout for buttons window and adding components to it
        buttons.setBorder(BorderFactory.createTitledBorder("Menu"));
        buttons.setLayout(new GridLayout(5,1));
        buttons.add(b1);
        b1.setToolTipText("Connects to metropolia data base");        
        buttons.add(b2);
        b2.setToolTipText("Writes the data from the window into the DB");
        buttons.add(b3);
        b3.setToolTipText("Prints the content of DB in output window");
        buttons.add(b4);
        b4.setToolTipText("Searches DB to find pilots and set their name as NOT NEEDED ");
        buttons.add(b5);
        b5.setToolTipText("For closing the connection to DB ");
        

        
        b1.addActionListener(new ActionListener( ) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        openDB();
                    } catch (Exception ex) {
                        Logger.getLogger(mainPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
        });
        
        b2.addActionListener(new ActionListener( ) {
            public void actionPerformed(ActionEvent e) {
                try {              
                    saveToDB();
                } catch (Exception ex) {
                    Logger.getLogger(mainPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        b3.addActionListener(new ActionListener( ) {
            public void actionPerformed(ActionEvent e) {
                try {             
                    openDBReading();
                } catch (Exception ex) {
                    Logger.getLogger(mainPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        b4.addActionListener(new ActionListener( ) {
            public void actionPerformed(ActionEvent e) {
                try {             
                    updateDB();
                } catch (Exception ex) {
                    Logger.getLogger(mainPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        b5.addActionListener(new ActionListener( ) {
            public void actionPerformed(ActionEvent e) {
                try {             
                    closeDB();
                } catch (Exception ex) {
                    Logger.getLogger(mainPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    private void openDB()throws Exception{       
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://mysql.metropolia.fi:3306/";
        LoginDialog d = new LoginDialog(null);
        String user = d.getUser();
        String pwd = d.getPassword();
        
        c = DriverManager.getConnection(url + user, user, pwd);
        forOUTPUT.setText("A new connection to DB is set"+"\n"+ "You can add"
                + "a new recond by filling the form and pressing the button Add reconrd ");
    }
    
    private void saveToDB()throws Exception{ 
        String ins = "INSERT INTO CVdatabase VALUES(?, ?, ?, ?)";
        PreparedStatement ps = c.prepareStatement(ins);
       //System.out.print(Tname.getText());
      // System.out.print(TlastName.getText());
      //  System.out.print(Cnationality.getSelectedItem().toString());
        ps.setString(1, Tname.getText());
        ps.setString(2, TlastName.getText());
        ps.setString(3, Cnationality.getSelectedItem().toString());
        ps.setString(4, Cprofession.getSelectedItem().toString());
        ps.executeUpdate();
        Tname.setText("");
        TlastName.setText("");
        Cnationality.setSelectedIndex(0);
        Cprofession.setSelectedIndex(0);
        forOUTPUT.setText("A new record was added to DB");

        //JOptionPane.showMessageDialog(null, message);
    }
    
    private void openDBReading()throws Exception{
       
        String columnNames[] = { "Name", "Last NAme", "Nationality", "Profession" };
        String dataVAlues[][]=new String[50][4];  
        int i=0;    
        String sql = "SELECT * FROM CVdatabase";
        Statement s = c.createStatement();
        ResultSet r = s.executeQuery(sql);
        String report = "See the results of SELECT operation:\n";
        while(r.next()){ 
            report += "\n" + r.getString(1) + " " + r.getString(2) + " " +
            r.getString(3) + " " + r.getString(4);
        dataVAlues[i][0]=r.getString(1);   
        dataVAlues[i][1]=r.getString(2);     
        dataVAlues[i][2]=r.getString(3);
        dataVAlues[i][3]=r.getString(4); 
          i++;  
        }
        table = new JTable( dataVAlues, columnNames );
        JScrollPane sp = new JScrollPane(table);
       // text.add(sp, BorderLayout.NORTH);
       forOUTPUT.setText("You can see the result in a newly opened window");
       //////
       JOptionPane.showMessageDialog(null,sp );


    }  
    private void updateDB()throws Exception{ 

        String sql = "UPDATE CVdatabase SET Name = 'Not_NEEDED'" +
                             " WHERE Profession = 'Pilot' ";
        Statement s = c.createStatement();
        int r = s.executeUpdate(sql);
        forOUTPUT.setText("DB was updated");
    }   
     private void closeDB()throws Exception{ 
           c.close();
        forOUTPUT.setText("The connection to DB is closed");
    }

    

    
// LAST BRACKET    
}

    

