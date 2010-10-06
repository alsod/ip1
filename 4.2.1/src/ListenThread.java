
import java.awt.Point;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author André Karlsson
 *
 */
public class ListenThread extends Thread {

    private DatagramSocket socket;
    private byte[] buf = new byte[256];
    private Draw draw;

    public ListenThread(DatagramSocket socket, Draw draw) {
        this.socket = socket;
        this.draw = draw;
    }

    @Override
    public void run() {
        // get response
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        while (true) {
            try {
                socket.receive(packet);
            } catch (IOException ex) {
                Logger.getLogger(ListenThread.class.getName()).log(Level.SEVERE, null, ex);
            }

            // display response
            String received = new String(packet.getData());
            String[] xy = received.split(" ");

            Point p = new Point(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));
            draw.addPoint(p);
            
        }
    }
}
