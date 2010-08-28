
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

public class Draw extends JFrame {

    protected static Set hs = new ConcurrentSkipListSet(new PointComparator());
    protected static DatagramSocket socket;
    protected static DatagramPacket packet;
    protected static byte[] buf = new byte[256];
    protected static InetAddress adress;
    protected static int port;
    private Paper p = new Paper();

    public static void main(String[] args) throws SocketException, UnknownHostException {

        socket = new DatagramSocket(Integer.parseInt(args[0]));
        System.out.println(args[0]);
        adress = InetAddress.getByName(args[1]);
        port = Integer.parseInt(args[2]);



        new Draw();

    }

    public Draw() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().add(p, BorderLayout.CENTER);

        setSize(640, 480);
        setVisible(true);

        Thread lt = new ListenThread(socket, this);
        lt.start();
    }

    public void addPoint(Point pnt) {
        p.addPoint(pnt);
    }

    private static class PointComparator implements Comparator<Point> {
        @Override
        public int compare(Point p1, Point p2) {
            int xDistance = p2.x - p1.x;
            if (xDistance == 0) {
                return p2.y - p1.y;
            }
            return xDistance;
        }
    } 
}

class Paper extends JPanel {

    public Paper() {
        try {
            setBackground(Color.white);
            addMouseListener(new L1());
            addMouseMotionListener(new L2());



        } catch (Exception ex) {
            Logger.getLogger(Paper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        Iterator i = Draw.hs.iterator();
        while (i.hasNext()) {

            Point p = (Point) i.next();
            g.fillOval(p.x, p.y, 10, 10);

        }
    }

    protected void addPoint(Point p) {
        Draw.hs.add(p);
        repaint();
    }

    class L1 extends MouseAdapter {

        public void mousePressed(MouseEvent me) {
            addPoint(me.getPoint());
            sendPoint(me.getPoint());
        }
    }

    class L2 extends MouseMotionAdapter {

        public void mouseDragged(MouseEvent me) {
            addPoint(me.getPoint());
            sendPoint(me.getPoint());
        }
    }

    private void sendPoint(Point pnt) {
        String msg = Integer.toString(pnt.x) + " " + Integer.toString(pnt.y) + " ";
        Draw.buf = msg.getBytes();
        Draw.packet = new DatagramPacket(Draw.buf, Draw.buf.length, Draw.adress, Draw.port);

        try {
            Draw.socket.send(Draw.packet);
        } catch (IOException ex) {
        }
    }
}


