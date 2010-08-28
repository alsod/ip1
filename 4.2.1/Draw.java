import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Draw extends JFrame {
  private Paper p = new Paper();

  public static void main(String[] args) {
    new Draw();
  }

  public Draw() {
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    getContentPane().add(p, BorderLayout.CENTER);

    setSize(640, 480);
    setVisible(true);
  }
}

class Paper extends JPanel {
  private HashSet hs = new HashSet();

  public Paper() {
    setBackground(Color.white);
    addMouseListener(new L1());
    addMouseMotionListener(new L2());
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.black);
    Iterator i = hs.iterator();
    while(i.hasNext()) {
      Point p = (Point)i.next();
      g.fillOval(p.x, p.y, 2, 2);
    }
  }

  private void addPoint(Point p) {
    hs.add(p);
    repaint();
  }

  class L1 extends MouseAdapter {
    public void mousePressed(MouseEvent me) {
      addPoint(me.getPoint());
    }
  }

  class L2 extends MouseMotionAdapter {
    public void mouseDragged(MouseEvent me) {
      addPoint(me.getPoint());
    }
  }
}
