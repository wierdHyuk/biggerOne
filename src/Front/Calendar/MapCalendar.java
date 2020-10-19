package Front.Calendar;
import Front.Map.imagePanel;

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

class CalendarDataManager{ // 7*6�迭�� ��Ÿ�� �޷� ���� ���ϴ� class
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
        // 1���� ��ġ�� ������ ��¥�� ����
        int calStartingPos = (cal.get(Calendar.DAY_OF_WEEK)+7-(cal.get(Calendar.DAY_OF_MONTH))%7)%7;
        if(calMonth == 1) calLastDate = calLastDateOfMonth[calMonth] + leapCheck(calYear);
        else calLastDate = calLastDateOfMonth[calMonth];
        // �޷� �迭 �ʱ�ȭ
        for(int i = 0 ; i<CAL_HEIGHT ; i++){
            for(int j = 0 ; j<CAL_WIDTH ; j++){
                calDates[i][j] = 0;
            }
        }
        // �޷� �迭�� �� ä���ֱ�
        for(int i = 0, num = 1, k = 0 ; i<CAL_HEIGHT ; i++){
            if(i == 0) k = calStartingPos;
            else k = 0;
            for(int j = k ; j<CAL_WIDTH ; j++){
                if(num <= calLastDate) calDates[i][j]=num++;
            }
        }
    }
    private int leapCheck(int year){ // �������� Ȯ���ϴ� �Լ�
        if(year%4 == 0 && year%100 != 0 || year%400 == 0) return 1;
        else return 0;
    }
    public void moveMonth(int mon){ // ����޷� ���� n�� ���ĸ� �޾� �޷� �迭�� ����� �Լ�(1���� +12, -12�޷� �̵� ����)
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




public class MapCalendar extends CalendarDataManager{ // CalendarDataManager�� GUI + �ð�
    // â ������ҿ� ��ġ��
    JFrame mainFrame;

    ImageIcon icon = new ImageIcon ( Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.png")));
    JPanel calOpPanel;
    JButton todayBut;
    JLabel todayLab;
    //**
    JButton lMonBut;
    JButton nMonBut;


    JLabel yearLbl = new JLabel("��");

    JLabel monthLbl = new JLabel("��");



    JComboBox<Integer> yearCombo = new JComboBox<Integer>();

    DefaultComboBoxModel<Integer> yearModel = new DefaultComboBoxModel<Integer>();

    JComboBox<Integer> monthCombo = new JComboBox<Integer>();

    DefaultComboBoxModel<Integer> monthModel = new DefaultComboBoxModel<Integer>();


    ListenForCalOpButtons lForCalOpButtons = new ListenForCalOpButtons();
    ListenForCalCombos lForCalCombos = new ListenForCalCombos();
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
    JLabel bottomInfo = new JLabel("����� �ڷγ� ��Ȳ");
    //���, �޼���
    final String WEEK_DAY_NAME[] = { "��", "��", "ȭ", "��", "��", "��", "��" };
    final String title = "����� �ڷγ� ��Ȳ";

    //**
    int curyear, curmonth, curdate;
    //**



    public MapCalendar(){ //������� ������ ���ĵǾ� ����. �� �ǳ� ���̿� ���ٷ� ����

        mainFrame = new JFrame(title);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1400,700);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        mainFrame.setIconImage(icon.getImage());

        //**
        mainFrame.setTitle("����� �ڷγ� ��Ȳ");
        //**
        try{
            UIManager.setLookAndFeel ("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");//LookAndFeel Windows ��Ÿ�� ����
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

        //**
        lMonBut = new JButton("��");
        lMonBut.setToolTipText("Previous Month");
        lMonBut.addActionListener(lForCalOpButtons);
        nMonBut = new JButton("��");
        //**
        nMonBut.setToolTipText("Next Month");
        nMonBut.addActionListener(lForCalOpButtons);
        //**
        for(int i=curyear-100; i<=curyear+50; i++){

            yearModel.addElement(i);

        }

        yearCombo.setModel(yearModel);

        yearCombo.setSelectedItem(curyear);	//���� �⵵ ����
        yearCombo.addActionListener(lForCalCombos);



        for(int i=1; i<=12; i++){

            monthModel.addElement(i);

        }
        monthCombo.setModel(monthModel);

        monthCombo.setSelectedItem(curmonth+1);	//���� �� ����
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
        calOpGC.fill = GridBagConstraints.NONE;
        calOpPanel.add(todayBut,calOpGC);
        calOpGC.gridwidth = 3;
        calOpGC.gridx = 2;
        calOpGC.gridy = 1;
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
            //���Ϻ� ����
            if(i == 0) weekDaysName[i].setBackground(new Color(127, 96, 0));
            else if (i == 6) weekDaysName[i].setBackground(new Color(127, 96, 0));
            else weekDaysName[i].setBackground(new Color(234, 178, 0));
            weekDaysName[i].setOpaque(true);//JLabel�� ������ �⺻�� ����! setOpaque(true)�� �ؾ� ���� ���� ����!
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
        calPanel.setLayout(new GridLayout(0,7,2,2));//���� 0���� ������, ���� ������ 3, ���ڻ��� ������ 2, 2
        calPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        showCal(); // �޷��� ǥ��

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

        //calOpPanel, calPanel��  frameSubPanelWest�� ��ġ
        JPanel frameSubPanelWest = new JPanel();
        Dimension calOpPanelSize = calOpPanel.getPreferredSize();
        calOpPanelSize.height = 110;
        calOpPanel.setPreferredSize(calOpPanelSize);
        frameSubPanelWest.setLayout(new BorderLayout());
        frameSubPanelWest.add(calOpPanel,BorderLayout.NORTH);
        frameSubPanelWest.add(calPanel,BorderLayout.CENTER);

        //infoPanel, mapPanel��  frameSubPanelEast�� ��ġ
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

        //�ڴʰ� �߰��� bottom Panel..
        frameBottomPanel = new JPanel();
        frameBottomPanel.add(bottomInfo);




        //frame�� ���� ��ġ
        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(frameSubPanelWest, BorderLayout.WEST);
        mainFrame.add(frameSubPanelEast, BorderLayout.CENTER);
        mainFrame.add(frameBottomPanel, BorderLayout.SOUTH);
        mainFrame.setVisible(true);

        focusToday(); //���� ��¥�� focus�� �� (mainFrame.setVisible(true) ���Ŀ� ��ġ�ؾ���)

        //Thread �۵�(�ð�, bottomMsg �����ð��� ����)
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

            if(!(k ==0 && l == 0)) calDayOfMon = calDates[k][l]; //today��ư�� ���������� �� actionPerformed�Լ��� ����Ǳ� ������ ���� �κ�

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

