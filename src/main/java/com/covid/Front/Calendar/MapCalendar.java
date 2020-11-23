package com.covid.Front.Calendar;
import com.covid.Front.Map.imagePanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

class CalendarDataManager{ // 7*6배열에 나타낼 달력 값을 구하는 class
    static final int CAL_WIDTH = 7;
    final static int CAL_HEIGHT = 6;
    int calDates[][] = new int[CAL_HEIGHT][CAL_WIDTH];
    int calYear;
    int calMonth;
    int calDayOfMon;
    final int calLastDateOfMonth[]={31,28,31,30,31,30,31,31,30,31,30,31};
    int calLastDate;
    Calendar today = Calendar.getInstance();
    Calendar cal;

    public CalendarDataManager(){
        setToday();
    }
    public void setToday(){
        calYear = today.get(Calendar.YEAR);
        calMonth = today.get(Calendar.MONTH);
        calDayOfMon = today.get(Calendar.DAY_OF_MONTH);
        makeCalData(today);
    }
    private void makeCalData(Calendar cal){
        // 1일의 위치와 마지막 날짜를 구함
        int calStartingPos = (cal.get(Calendar.DAY_OF_WEEK)+7-(cal.get(Calendar.DAY_OF_MONTH))%7)%7;
        if(calMonth == 1) calLastDate = calLastDateOfMonth[calMonth] + leapCheck(calYear);
        else calLastDate = calLastDateOfMonth[calMonth];
        // 달력 배열 초기화
        for(int i = 0 ; i<CAL_HEIGHT ; i++){
            for(int j = 0 ; j<CAL_WIDTH ; j++){
                calDates[i][j] = 0;
            }
        }
        // 달력 배열에 값 채워넣기
        for(int i = 0, num = 1, k = 0 ; i<CAL_HEIGHT ; i++){
            if(i == 0) k = calStartingPos;
            else k = 0;
            for(int j = k ; j<CAL_WIDTH ; j++){
                if(num <= calLastDate) calDates[i][j]=num++;
            }
        }
    }
    private int leapCheck(int year){ // 윤년인지 확인하는 함수
        if(year%4 == 0 && year%100 != 0 || year%400 == 0) return 1;
        else return 0;
    }
    public void moveMonth(int mon){ // 현재달로 부터 n달 전후를 받아 달력 배열을 만드는 함수(1년은 +12, -12달로 이동 가능)
        calMonth += mon;
        if(calMonth>11) while(calMonth>11){
            calYear++;
            calMonth -= 12;
        } else if (calMonth<0) while(calMonth<0){
            calYear--;
            calMonth += 12;
        }
        cal = new GregorianCalendar(calYear,calMonth,calDayOfMon);
        makeCalData(cal);
    }
    public int getMonth() {
        System.out.println("month:" + calMonth);
        return calMonth;
    }
    public int getYear() {
        System.out.println("year:" +calYear);
        return calYear;
    }
}




public class MapCalendar extends CalendarDataManager{ // CalendarDataManager의 GUI + 시계
    // 창 구성요소와 배치도
    JFrame mainFrame;

    ImageIcon icon = new ImageIcon ( Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));
    JPanel calOpPanel;
    JButton todayBut;
    JLabel todayLab;
    //**
    JButton lMonBut;
    JButton nMonBut;

    //관리자 모드
    JPanel adminPanel;
    JButton adminBut;
    JLabel adminLabel;


    JLabel yearLbl = new JLabel("년");

    JLabel monthLbl = new JLabel("월");



    JComboBox<Integer> yearCombo = new JComboBox<Integer>();

    DefaultComboBoxModel<Integer> yearModel = new DefaultComboBoxModel<Integer>();

    JComboBox<Integer> monthCombo = new JComboBox<Integer>();

    DefaultComboBoxModel<Integer> monthModel = new DefaultComboBoxModel<Integer>();


    ListenForCalOpButtons lForCalOpButtons = new ListenForCalOpButtons();
    ListenForCalCombos lForCalCombos = new ListenForCalCombos();
    ActionForAdminButton lForAAdminButtons = new ActionForAdminButton();
//**

    JPanel calPanel;
    JButton weekDaysName[];
    JButton dateButs[][] = new JButton[6][7];
    listenForDateButs lForDateButs = new listenForDateButs();

    JPanel infoPanel;
    JLabel infoClock;

    JPanel mapPanel;
    JLabel selectedDate;
    imagePanel mapArea;
    JScrollPane mapAreaSP;


    JPanel frameBottomPanel;
    JLabel bottomInfo = new JLabel("서울시 코로나 현황");
    //상수, 메세지
    final String WEEK_DAY_NAME[] = { "일", "월", "화", "수", "목", "금", "토" };
    final String title = "서울시 코로나 현황";

    //**
    int curyear, curmonth, curdate;
    //**



    public MapCalendar(){ //구성요소 순으로 정렬되어 있음. 각 판넬 사이에 빈줄로 구별

        mainFrame = new JFrame(title);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1400,700);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        mainFrame.setIconImage(icon.getImage());

        //**
        mainFrame.setTitle("서울시 코로나 현황");
        //**
        try{
            UIManager.setLookAndFeel ("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");//LookAndFeel Windows 스타일 적용
            SwingUtilities.updateComponentTreeUI(mainFrame) ;
        }catch(Exception e){
            bottomInfo.setText("ERROR : LookAndFeel setting failed");
        }
//**
        curyear =today.get(Calendar.YEAR);
        curmonth = today.get(Calendar.MONTH);
//**

        calOpPanel = new JPanel();
        todayBut = new JButton("Today");
        todayBut.setToolTipText("Today");
        todayBut.addActionListener(lForCalOpButtons);
        todayLab = new JLabel(today.get(Calendar.MONTH)+1+"/"+today.get(Calendar.DAY_OF_MONTH)+"/"+today.get(Calendar.YEAR));


        // 관리자
        adminPanel = new JPanel();
        adminBut = new JButton("관리자");
        adminBut.addActionListener(lForAAdminButtons);
        adminBut.setToolTipText("관리자 모드");


        //**
        lMonBut = new JButton("◀");
        lMonBut.setToolTipText("Previous Month");
        lMonBut.addActionListener(lForCalOpButtons);
        nMonBut = new JButton("▶");
        //**
        nMonBut.setToolTipText("Next Month");
        nMonBut.addActionListener(lForCalOpButtons);
        //**
        for(int i=curyear-100; i<=curyear+50; i++){

            yearModel.addElement(i);

        }

        yearCombo.setModel(yearModel);

        yearCombo.setSelectedItem(curyear);	//현재 년도 선택
        yearCombo.addActionListener(lForCalCombos);



        for(int i=1; i<=12; i++){

            monthModel.addElement(i);

        }
        monthCombo.setModel(monthModel);

        monthCombo.setSelectedItem(curmonth+1);	//현재 월 선택
        monthCombo.addActionListener(lForCalCombos);

        //** 

        calOpPanel.setLayout(new GridBagLayout());
        GridBagConstraints calOpGC = new GridBagConstraints();
        calOpGC.gridx = 1;
        calOpGC.gridy = 1;
        calOpGC.gridwidth = 2;
        calOpGC.gridheight = 1;
        calOpGC.weightx = 1;
        calOpGC.weighty = 1;
        calOpGC.insets = new Insets(5,5,0,0);
        calOpGC.anchor = GridBagConstraints.WEST;

        // admin
        GridBagConstraints adminCalOp = new GridBagConstraints();
        calOpGC.gridx = 1;
        calOpGC.gridy = 1;
        calOpGC.gridwidth = 2;
        calOpGC.gridheight = 1;
        calOpGC.weightx = 1;
        calOpGC.weighty = 1;
        calOpGC.insets = new Insets(7,7,0,0);
        calOpGC.anchor = GridBagConstraints.WEST;
        // 관리자
        calOpPanel.add(adminBut,adminCalOp);



        calOpPanel.add(todayBut,calOpGC);
        calOpGC.gridwidth = 3;
        calOpGC.gridx = 2;
        calOpGC.gridy = 1;calOpGC.fill = GridBagConstraints.NONE;


        calOpPanel.add(todayLab,calOpGC);
        calOpGC.anchor = GridBagConstraints.CENTER;
        calOpGC.gridwidth = 1;
        calOpGC.gridx = 1;
        calOpGC.gridy = 2;
        //**
        calOpPanel.add(lMonBut,calOpGC);
        //**
        calOpGC.gridwidth = 1;
        calOpGC.gridx = 2;
        calOpGC.gridy = 2;
        //**
        calOpPanel.add(yearCombo,calOpGC);
        //**
        calOpGC.gridwidth = 1;
        calOpGC.gridx = 3;
        calOpGC.gridy = 2;
        calOpPanel.add(yearLbl,calOpGC);
        //**
        calOpGC.gridwidth = 1;
        calOpGC.gridx = 4;
        calOpGC.gridy = 2;
        //**
        calOpPanel.add(monthCombo,calOpGC);
        //**
        calOpGC.gridwidth = 1;
        calOpGC.gridx = 5;
        calOpGC.gridy = 2;
        //**
        calOpPanel.add(monthLbl,calOpGC);
        //**
        calOpGC.gridwidth = 1;
        calOpGC.gridx = 6;
        calOpGC.gridy = 2;
        //**
        calOpPanel.add(nMonBut,calOpGC);
        //**

        calPanel=new JPanel();
        weekDaysName = new JButton[7];
        for(int i=0 ; i<CAL_WIDTH ; i++){
            System.out.print(WEEK_DAY_NAME[i]);
            weekDaysName[i]=new JButton(WEEK_DAY_NAME[i]);
            weekDaysName[i].setBorderPainted(false);//??
            weekDaysName[i].setContentAreaFilled(false);
            weekDaysName[i].setForeground(Color.WHITE);
            //요일별 색깔
            if(i == 0) weekDaysName[i].setBackground(new Color(127, 96, 0));
            else if (i == 6) weekDaysName[i].setBackground(new Color(127, 96, 0));
            else weekDaysName[i].setBackground(new Color(234, 178, 0));
            weekDaysName[i].setOpaque(true);//JLabel의 배경색은 기본이 투명! setOpaque(true)로 해야 배경색 적용 가능!
            weekDaysName[i].setFocusPainted(false);
            calPanel.add(weekDaysName[i]);
        }
        for(int i=0 ; i<CAL_HEIGHT ; i++){
            for(int j=0 ; j<CAL_WIDTH ; j++){
                dateButs[i][j]=new JButton();
                dateButs[i][j].setBorderPainted(false);
                dateButs[i][j].setContentAreaFilled(false);
                dateButs[i][j].setBackground(Color.WHITE);
                dateButs[i][j].setOpaque(true);
                dateButs[i][j].addActionListener(lForDateButs);
                calPanel.add(dateButs[i][j]);
            }
        }
        calPanel.setLayout(new GridLayout(0,7,2,2));//행은 0으로 가변적, 열의 개수는 3, 격자사이 간격은 2, 2
        calPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        showCal(); // 달력을 표시

        infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        infoClock = new JLabel("", SwingConstants.RIGHT);
        infoClock.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel.add(infoClock, BorderLayout.NORTH);
        selectedDate = new JLabel("<Html><font size=3>"+(today.get(Calendar.MONTH)+1)+"/"+today.get(Calendar.DAY_OF_MONTH)+"/"+today.get(Calendar.YEAR)+"&nbsp;(Today)</html>", SwingConstants.LEFT);
        selectedDate.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));



        mapPanel=new JPanel();
        mapPanel.setBorder(BorderFactory.createTitledBorder("Location : SEOUL"));
        mapArea = new imagePanel();


        mapPanel.setLayout(new BorderLayout());
        mapPanel.add(selectedDate, BorderLayout.NORTH);
        mapPanel.add(mapArea,BorderLayout.CENTER);

        //calOpPanel, calPanel을  frameSubPanelWest에 배치
        JPanel frameSubPanelWest = new JPanel();
        Dimension calOpPanelSize = calOpPanel.getPreferredSize();
        calOpPanelSize.height = 110;
        calOpPanel.setPreferredSize(calOpPanelSize);
        frameSubPanelWest.setLayout(new BorderLayout());
        frameSubPanelWest.add(calOpPanel,BorderLayout.NORTH);
        frameSubPanelWest.add(calPanel,BorderLayout.CENTER);

        //infoPanel, mapPanel을  frameSubPanelEast에 배치
        JPanel frameSubPanelEast = new JPanel();
        Dimension infoPanelSize=infoPanel.getPreferredSize();
        infoPanelSize.height = 65;
        infoPanel.setPreferredSize(infoPanelSize);
        frameSubPanelEast.setLayout(new BorderLayout());
        frameSubPanelEast.add(infoPanel,BorderLayout.NORTH);
        frameSubPanelEast.add(mapPanel,BorderLayout.CENTER);

        Dimension frameSubPanelWestSize = frameSubPanelWest.getPreferredSize();
        frameSubPanelWestSize.width = 550;
        frameSubPanelWest.setPreferredSize(frameSubPanelWestSize);

        //뒤늦게 추가된 bottom Panel..
        frameBottomPanel = new JPanel();
        frameBottomPanel.add(bottomInfo);




        //frame에 전부 배치
        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(frameSubPanelWest, BorderLayout.WEST);
        mainFrame.add(frameSubPanelEast, BorderLayout.CENTER);
        mainFrame.add(frameBottomPanel, BorderLayout.SOUTH);
        mainFrame.setVisible(true);

        focusToday(); //현재 날짜에 focus를 줌 (mainFrame.setVisible(true) 이후에 배치해야함)

        //Thread 작동(시계, bottomMsg 일정시간후 삭제)
        ThreadConrol threadCnl = new ThreadConrol();
        threadCnl.start();
    }
    private void focusToday(){
        if(today.get(Calendar.DAY_OF_WEEK) == 1)
            dateButs[today.get(Calendar.WEEK_OF_MONTH)][today.get(Calendar.DAY_OF_WEEK)-1].requestFocusInWindow();
        else
            dateButs[today.get(Calendar.WEEK_OF_MONTH)-1][today.get(Calendar.DAY_OF_WEEK)-1].requestFocusInWindow();
    }

    private void showCal(){

        for(int i=0;i<CAL_HEIGHT;i++){
            for(int j=0;j<CAL_WIDTH;j++){
                String fontColor="black";
                if(j==0) fontColor="red";
                else if(j==6) fontColor="blue";

                File f =new File("MemoData/"+calYear+((calMonth+1)<10?"0":"")+(calMonth+1)+(calDates[i][j]<10?"0":"")+calDates[i][j]+".txt");
                if(f.exists()){
                    dateButs[i][j].setText("<html><b><font color="+fontColor+">"+calDates[i][j]+"</font></b></html>");
                }
                else dateButs[i][j].setText("<html><font color="+fontColor+">"+calDates[i][j]+"</font></html>");

                JLabel todayMark = new JLabel("<html><font color=green>*</html>");
                dateButs[i][j].removeAll();
                if(calMonth == today.get(Calendar.MONTH) &&
                        calYear == today.get(Calendar.YEAR) &&
                        calDates[i][j] == today.get(Calendar.DAY_OF_MONTH)){
                    dateButs[i][j].add(todayMark);
                    dateButs[i][j].setToolTipText("Today");
                }

                if(calDates[i][j] == 0) dateButs[i][j].setVisible(false);
                else dateButs[i][j].setVisible(true);
            }
        }
    }

    //**
    private class ListenForCalOpButtons implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            int selecyear, selecmonth, diffyear, diffmonth;
            if(e.getSource() == todayBut){
                setToday();
                lForDateButs.actionPerformed(e);
                focusToday();
            }
            else {
                if(e.getSource() == lMonBut) {
                    moveMonth(-1);
                }
                else if(e.getSource() == nMonBut) {
                    moveMonth(1);
                }

                monthCombo.setSelectedItem(getMonth()+1);
                yearCombo.setSelectedItem(getYear());
            }


            showCal();
        }
    }

    private class ListenForCalCombos implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            int selecyear, selecmonth, diffyear, diffmonth;
            selecmonth = (Integer)monthCombo.getSelectedItem();
            selecyear = (Integer)yearCombo.getSelectedItem();

            diffmonth = selecmonth -(getMonth() + 1);
            diffyear = selecyear - (getYear());

            moveMonth(diffmonth);
            moveMonth(diffyear * 12);


            showCal();
        }
    }

//    관리자 버튼 눌렀을 시 새로운 창 열기
    class AdminWindow extends JFrame{
        AdminWindow(){
            setTitle("로그인");

            JPanel adminWindowContainer = new JPanel();
            setContentPane(adminWindowContainer);

            JLabel newLbal = new JLabel("로그인 ");

            adminWindowContainer.add(newLbal);

            setSize(500,300);
            setResizable(false);
            setVisible(true);
        }
    }

    // 관리자 버튼 누를시 action
    private class ActionForAdminButton implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            new AdminWindow();
        }
    }



//**

    private class listenForDateButs implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            int k=0,l=0;
            for(int i=0 ; i<CAL_HEIGHT ; i++){
                for(int j=0 ; j<CAL_WIDTH ; j++){
                    if(e.getSource() == dateButs[i][j]){
                        k=i;
                        l=j;
                    }
                }
            }

            if(!(k ==0 && l == 0)) calDayOfMon = calDates[k][l]; //today버튼을 눌렀을때도 이 actionPerformed함수가 실행되기 때문에 넣은 부분

            cal = new GregorianCalendar(calYear,calMonth,calDayOfMon);

            String dDayString = new String();
            int dDay=((int)((cal.getTimeInMillis() - today.getTimeInMillis())/1000/60/60/24));
            if(dDay == 0 && (cal.get(Calendar.YEAR) == today.get(Calendar.YEAR))
                    && (cal.get(Calendar.MONTH) == today.get(Calendar.MONTH))
                    && (cal.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH))) dDayString = "Today";
            else if(dDay >=0) dDayString = "D-"+(dDay+1);
            else if(dDay < 0) dDayString = "D+"+(dDay)*(-1);

            selectedDate.setText("<Html><font size=3>"+(calMonth+1)+"/"+calDayOfMon+"/"+calYear+"&nbsp;("+dDayString+")</html>");


        }
    }
    private class ThreadConrol extends Thread{
        public void run(){
            boolean msgCntFlag = false;
            int num = 0;
            String curStr = new String();
            while(true){
                try{
                    today = Calendar.getInstance();
                    String amPm = (today.get(Calendar.AM_PM)==0?"AM":"PM");
                    String hour;
                    if(today.get(Calendar.HOUR) == 0) hour = "12";
                    else if(today.get(Calendar.HOUR) == 12) hour = " 0";
                    else hour = (today.get(Calendar.HOUR)<10?" ":"")+today.get(Calendar.HOUR);
                    String min = (today.get(Calendar.MINUTE)<10?"0":"")+today.get(Calendar.MINUTE);
                    String sec = (today.get(Calendar.SECOND)<10?"0":"")+today.get(Calendar.SECOND);
                    infoClock.setText(amPm+" "+hour+":"+min+":"+sec);

                    sleep(1000);
                    String infoStr = bottomInfo.getText();

                    if(infoStr != " " && (msgCntFlag == false || curStr != infoStr)){
                        num = 5;
                        msgCntFlag = true;
                        curStr = infoStr;
                    }
                    else if(infoStr != " " && msgCntFlag == true){
                        if(num > 0) num--;
                        else{
                            msgCntFlag = false;
                            bottomInfo.setText(" ");
                        }
                    }
                }
                catch(InterruptedException e){
                    System.out.println("Thread:Error");
                }
            }
        }
    }

}

