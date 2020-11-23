package com.covid.Front.Admin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//    관리자 버튼 눌렀을 시 새로운 창 열기
public class AdminWindow extends JFrame {
    public AdminWindow() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("ID : ");

        JLabel pswrd = new JLabel("PassWord : ");

        JTextField txtID = new JTextField(10);

        JPasswordField txtPass = new JPasswordField(10);

        JButton logBtn = new JButton("Log in");

        panel.add(label);
        panel.add(txtID);
        panel.add(pswrd);
        panel.add(txtPass);
        panel.add(logBtn);

        logBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = "test";
                String pass = "1234";

                if (id.equals(txtID.getText()) && pass.equals(txtPass.getText())) {
                    JOptionPane.showMessageDialog(null, "you have logged in successfully");
                } else {
                    JOptionPane.showMessageDialog(null, " you failed to log in ");
                }
            }

        });
        add(panel);
        setVisible(true);
        setSize(500, 70);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
