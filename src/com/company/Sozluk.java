package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

/**
 * Created by tolga on 5/25/15.
 */
public class Sozluk {
    private JPanel mainPanel;
    private JTextField textFieldWord;
    private JButton btnAra;
    private JButton btnChangeButton;
    private JTextPane textPane1;
    private JPanel jPanelUst;
    private JPanel jPanelAlt;
    private JLabel lbl2;
    private JLabel lbl1;
    private JTextPane textPane2;
    private String langtolang="tren";
    private ResultSet resultSet;
    private Statement statement;
    DefaultTableModel defaultTableModel;

    public Sozluk() {


        btnChangeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Icon i = new ImageIcon();
                i = lbl1.getIcon();
                lbl1.setIcon(lbl2.getIcon());
                lbl2.setIcon(i);
                if (langtolang.equals("tren")) {
                    langtolang = "entr";
                } else {
                    langtolang = "tren";
                }
            }
        });


        btnAra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (langtolang.equals("tren")) {
                        findEnglishWord();
                    } else {
                        findTurkishWord();
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
                textPane2.setText("");
            }
        });


        textFieldWord.addKeyListener(new KeyAdapter() {
            int count=0;
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(count>3)
                {
                    try {
                        tabloDoldur();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
                count++;
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sozluk");
        frame.setContentPane(new Sozluk().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }
    public void baglan() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/sozluk","root","12345");
        statement=connection.createStatement();
    }

    public void findTurkishWord() throws SQLException, ClassNotFoundException {
        textPane1.setText("");
        baglan();
        System.out.println(textFieldWord.getText());
        resultSet=statement.executeQuery("select * from ENTR where ENTR.A='" + textFieldWord.getText() + "'");
        while(resultSet.next())
        {
            textPane1.setText(textPane1.getText()+"\n"+resultSet.getString(2)+","+resultSet.getString(3));
        }


    }
    public void findEnglishWord() throws SQLException, ClassNotFoundException {
        textPane1.setText("");
        baglan();
        System.out.println(textFieldWord.getText());
        resultSet=statement.executeQuery("select * from TREN where TREN.A='"+textFieldWord.getText()+"'");
        while(resultSet.next())
        {
            textPane1.setText(textPane1.getText() + "\n" + resultSet.getString(2) + "," + resultSet.getString(3));
        }
    }
    public void tabloDoldur() throws SQLException, ClassNotFoundException {
        baglan();
        if(langtolang.equals("tren"))
        {
            resultSet=statement.executeQuery("select * from TREN where TREN.A LIKE '"+textFieldWord.getText()+"%"+"'");
        }
        else
        {
            resultSet=statement.executeQuery("select * from ENTR where ENTR.A LIKE '"+textFieldWord.getText()+"%"+"'");
        }

        while(resultSet.next())
        {
            textPane2.setText(resultSet.getString(1));
        }

    }

    public String getLangtolang() {
        return langtolang;
    }

    public void setLangtolang(String langtolang) {
        this.langtolang = langtolang;
    }
}
