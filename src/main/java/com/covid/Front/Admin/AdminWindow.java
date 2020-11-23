package com.covid.Front.Admin;

import com.covid.DAO.DataDAO;
import com.covid.Model.Const;
import com.covid.Model.PatientModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.List;

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
                    dispose();// 해당 창 종료
                    new AdminDBWindow(); // 새로운 db 관리자 창 띄우기
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

class AdminDBWindow extends JFrame {
    JPanel panWest, panSouth, p1, p2, p3,p4,p5;
    JTextField txtIdx, txtDate,txtPatientId, txtRegion,txtState;
    JButton btnInsert, btnDelete, btnSelect;

    JTable table;


    AdminDBWindow() {
        panWest = new JPanel(new GridLayout(3, 0));

        p1 = new JPanel(new FlowLayout());
        p1.add(new JLabel("id"));
        p1.add(txtIdx = new JTextField(12));
        panWest.add(p1);

        p2 = new JPanel(new FlowLayout());
        p2.add(new JLabel("확진 날짜"));
        p2.add(txtDate = new JTextField(12));
        panWest.add(p2);

        p3 = new JPanel(new FlowLayout());
        p3.add(new JLabel("환자 id"));
        p3.add(txtPatientId = new JTextField(12));
        panWest.add(p3);

        p4 = new JPanel(new FlowLayout());
        p4.add(new JLabel("지역"));
        p4.add(txtRegion = new JTextField(12));
        panWest.add(p4);

        p5 = new JPanel(new FlowLayout());
        p5.add(new JLabel("환자 상태"));
        p5.add(txtState = new JTextField(12));
        panWest.add(p5);

        add(panWest, BorderLayout.WEST);

        panSouth = new JPanel();
        panSouth.add(btnSelect = new JButton("조회"));
        panSouth.add(btnInsert = new JButton("추가"));
        panSouth.add(btnDelete = new JButton("삭제"));

        add(panSouth, BorderLayout.SOUTH);

        // 버튼과 리스너 연결
        btnSelect.addActionListener(lisner);
        btnInsert.addActionListener(lisner);
        btnDelete.addActionListener(lisner);

        setBounds(100, 100, 700, 300);
        setVisible(true);

        // 윈도우 창 모두 닫기
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 윈도우 창에서 새 창 열리는 것만 닫기
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // 4단계 - 내부 익명 클래스
    ActionListener lisner = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object obj = e.getSource(); // 버튼 받아옴

            if (obj == btnSelect) {
//    System.out.println("Select!");
                select();
            } else if (obj == btnInsert) {
//    System.out.println("Insert!");
                insert();
            } else if (obj == btnDelete) {
//    System.out.println("Delete!");
                delete();
            }
        }
    };

    void select() {

        List<PatientModel> patientModelList = DataDAO.selectAllFromTable();

        String[] header = {"데이터 id", "확진 날짜", "환자 id", "지역", "환자 상태"};
        String[][] contents = new String[patientModelList.size()][5];

        int index = 0; // patientModel 세로

        for (PatientModel patient : patientModelList
        ) {
            contents[index][0] = patient.getId();
            contents[index][1] = patient.getConfirmedDate();
            contents[index][2] = patient.getPatientId();
            contents[index][3] = patient.getRegion();
            contents[index][4] = String.valueOf(patient.getPatientState());
            index++;
        }


        JTable table = new JTable(contents, header);
        add(new JScrollPane(table));
        setVisible(true);
    }

    private void insert() {
        String id;
        String date;
        String patientId;
        String region;
        String state;



        id = txtIdx.getText();
        date = txtDate.getText();
        patientId = txtPatientId.getText();
        region=txtRegion.getText();
        state = txtState.getText();

        PatientModel patientModel = PatientModel.PatientModelBuilder(id,date,patientId,region,state);

        DataDAO.createNewData(patientModel);

    }

    private void delete() {
        String strIdx;

        strIdx = JOptionPane.showInputDialog(
                null, "삭제할 회원 번호를 입력하세요!");

        DataDAO.deleteData(strIdx);
    }


}

