package com.covid;

import com.covid.Front.Calendar.MapCalendar;
import com.covid.Model.PatientModel;
import com.covid.Util.DataHandler.ReadCSV;

import javax.swing.*;
import java.util.List;

public class Start {
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new MapCalendar();
            }
        });
    }
}
