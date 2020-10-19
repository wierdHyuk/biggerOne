package Front.Map;

import javax.swing.*;
import java.awt.*;

public class imagePanel extends JPanel {
    Image image = null;
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        ImageIcon map = new ImageIcon( Toolkit.getDefaultToolkit().getImage(getClass().getResource("map.png")));
        image = map.getImage();

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth();
        int h = getHeight();
        Color color1 = new Color(0xF2BE65);
        Color color2 = Color.white;
        GradientPaint gp = new GradientPaint(10, 10, color1, 0, h, color1);

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
        g.drawImage(image, 0, 0, 840, 550, this);
    }
}
