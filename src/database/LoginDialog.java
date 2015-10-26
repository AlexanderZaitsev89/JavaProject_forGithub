package database;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginDialog extends JDialog {

    private JButton login, cancel;
    private String u, p;
    private char [ ] pw;
    private JTextField uf;
    private JPasswordField pf;

    public LoginDialog(Frame f) {
        super(f, "Connect to MySQL server", true);
        uf = new JTextField(10);
        pf = new JPasswordField(10);
        login = new JButton("Login");
        cancel = new JButton("Cancel");
        JPanel upper = new JPanel(new GridLayout(2,2,10,10));
        upper.add(new JLabel("Username"));
        upper.add(uf);
        upper.add(new JLabel("Password"));
        upper.add(pf);
        this.add(upper, BorderLayout.NORTH);
        JPanel lower = new JPanel();
        lower.add(login);
        lower.add(cancel);
        this.add(lower, BorderLayout.SOUTH);
        login.addActionListener(new ActionListener( )  {
            public void actionPerformed(ActionEvent e)  {
                u = uf.getText();
                pw = pf.getPassword();
                setVisible(false);
            }
        });
        cancel.addActionListener(new ActionListener( )  {
            public void actionPerformed(ActionEvent e)  {
                u = " ";
                pw = new char[1];
                pw[0] = ' ';
                setVisible(false);
            }
        });
        this.pack();
        this.setVisible(true);
    }

    public String getUser()  {
        return u;
    }

    public String getPassword()  {
        StringBuffer pb = new StringBuffer();
        for(int i=0; i < pw.length; i++)
            pb.append(pw[i]);
        return pb.toString();
    }

}


