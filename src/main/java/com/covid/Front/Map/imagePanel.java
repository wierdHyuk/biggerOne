package com.covid.Front.Map;

import javax.swing.*;
import java.awt.*;

public class imagePanel extends JPanel {
    Image image = null;
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        // 지도
        ImageIcon map = new ImageIcon( Toolkit.getDefaultToolkit().getImage(getClass().getResource("/map.png")));
        image = map.getImage();
        g.drawImage(image, 0, 0, 840, 550, this);

        //은평구
        g.setColor(Color.RED);
        g.fillOval(353,171,15,15);

        //영등포구
        g.setColor(Color.PINK);
        g.fillOval(283,307,15,15);

        //금천구
        g.setColor(Color.BLUE);
        g.fillOval(279,413,15,15);

        //관악구
        g.setColor(Color.BLUE);
        g.fillOval(364,409,15,15);

        //동작구
        g.setColor(Color.BLUE);
        g.fillOval(399,359,15,15);

        //서초구
        g.setColor(Color.BLUE);
        g.fillOval(459,350,15,15);

        //서대문구
        g.setColor(Color.BLUE);
        g.fillOval(368,229,15,15);

        //종로구
        g.setColor(Color.BLUE);
        g.fillOval(408,202,15,15);

        //용산
        g.setColor(Color.BLUE);
        g.fillOval(417,316,15,15);

        //중구
        g.setColor(Color.BLUE);
        g.fillOval(471,272,15,15);

        //강남구
        g.setColor(Color.BLUE);
        g.fillOval(530,344,15,15);

        //송파구
        g.setColor(Color.BLUE);
        g.fillOval(646,326,15,15);

        //강동구
        g.setColor(Color.BLUE);
        g.fillOval(690,296,15,15);

        //노원구
        g.setColor(Color.BLUE);
        g.fillOval(593,169,15,15);

        //중량구
        g.setColor(Color.BLUE);
        g.fillOval(623,208,15,15);

        //광진구
        g.setColor(Color.BLUE);
        g.fillOval(603,270,15,15);

        //동대문구
        g.setColor(Color.BLUE);
        g.fillOval(566,229,15,15);

        //도봉구
        g.setColor(Color.BLUE);
        g.fillOval(532,145,15,15);

        //강북구
        g.setColor(Color.BLUE);
        g.fillOval(500,186,15,15);

        //성북구
        g.setColor(Color.BLUE);
        g.fillOval(522,216,15,15);

        //성동구
        g.setColor(Color.BLUE);
        g.fillOval(556,282,15,15);


        //강서구
        g.setColor(Color.GREEN);
        g.fillOval(185,270,15,15);

        //마포구
        g.setColor(Color.LIGHT_GRAY);
        g.fillOval(260,250,15,15);

        //양천구
        g.setColor(Color.BLUE);
        g.fillOval(160,317,15,15);

        //구로구
        g.setColor(Color.BLACK);
        g.fillOval(160,370,15,15);
    }
}
