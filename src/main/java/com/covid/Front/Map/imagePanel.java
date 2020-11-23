package com.covid.Front.Map;

import com.covid.Model.Const;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class imagePanel extends JPanel {
    Image image = null;
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 지도
        ImageIcon map = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/map.png")));
        image = map.getImage();
        g.drawImage(image, 0, 0, 840, 550, this);


        List<Long> counts = Const.counts;

        int [][] datas = {
                {185, 270},
                {160, 317},
                {160, 370},
                {279, 413},
                {364, 409},
                {399, 359},
                {283, 307},
                {260, 250},
                {353, 171},
                {368, 229},
                {417, 316},
                {471, 272},
                {459, 350},
                {530, 344},
                {556, 282},
                {408, 202},
                {500, 186},
                {522, 216},
                {566, 229},
                {646, 326},
                {690, 296},
                {603, 270},
                {623, 208},
                {593, 169},
                {532, 145}
        };

        if (counts != null) {
            for (int i=0;i<counts.size();i++) {
                Long count = counts.get(i);

                Color color=Color.GREEN;

                if(count>=3 && count<=5){
                    color=Color.ORANGE;
                }else if(count>5&&count<=7){
                    color=Color.YELLOW;
                }else if(count>7){
                    color=Color.red;
                }
                g.setColor(color);
                g.fillOval(datas[i][0],datas[i][1],15,15);


            }
        }

    }
}
