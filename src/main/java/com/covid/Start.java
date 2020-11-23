package com.covid;

import com.covid.Front.Calendar.MapCalendar;

import javax.swing.*;

public class Start {
    public static void main(String[] args) throws Exception {

        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new MapCalendar();
            }
        });
    }
}
