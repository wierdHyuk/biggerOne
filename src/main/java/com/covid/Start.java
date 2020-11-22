package com.covid;

import com.covid.DAO.DataDAO;
import com.covid.Front.Calendar.MapCalendar;
import com.covid.Model.PatientModel;
import com.covid.Util.DataHandler.ReadCSV;

import javax.swing.*;
import java.util.List;

public class Start {
    public static void main(String[] args) throws Exception {

        List<PatientModel> patientModelList = ReadCSV.getData();
//
//        for (PatientModel patient: patientModelList) {
//            System.out.println(patient.getId() + "  " + patient.getConfirmedDate() + "  "+ patient.getPatientId() + "  " + patient.getRegion() + "  " + patient.getPatientState());
//        }
        DataDAO.createDb();
        DataDAO.createTable();
        DataDAO.modelToDB(patientModelList);




        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new MapCalendar();
            }
        });
    }
}
