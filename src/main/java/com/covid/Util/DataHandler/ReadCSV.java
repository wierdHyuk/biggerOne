package com.covid.Util.DataHandler;

import com.covid.Model.PatientModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadCSV {
    public static List<PatientModel> getData() throws Exception{
        try {
            //반환용 리스트
            List<PatientModel> patientModels = new ArrayList<>();
            BufferedReader br ;

            File file = new File("data", "covid19!.csv");
            br = Files.newBufferedReader(Paths.get(file.getAbsolutePath()));
            String line;

            int count = 0;

            while ((line = br.readLine()) != null) {
                if (count == 0) {
                    count++;
                    continue;
                }
                String[] array = line.split(",");
                //배열에서 리스트 반환

                String id = array[0];
                String confirmedDate = array[1];

                // null 처리
                if(confirmedDate!=null && !confirmedDate.equals("")) {
                    confirmedDate = confirmedDate.substring(0, confirmedDate.length() - 1); // 마지막 문자열 지우기 (.)
                    String[] toProcessConfirmedDate = confirmedDate.split("\\.");
                    int month = Integer.parseInt(toProcessConfirmedDate[0]);
                    int date = Integer.parseInt(toProcessConfirmedDate[1]);

                    confirmedDate = String.valueOf(month) + "." + String.valueOf(date);

                }

                String patientId = array[2];
                String region = array[5];
                String patientState = array[9];

                // patientModel 생성
                PatientModel patientModel = PatientModel.PatientModelBuilder(id, confirmedDate, patientId, region, patientState);

                // list 에 저장
                patientModels.add(patientModel);
            }

            return patientModels;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }
}


