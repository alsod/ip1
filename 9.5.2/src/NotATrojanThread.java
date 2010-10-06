


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * NotATrojanThread.java
 * @author Andre Karlsson
 *
 * Assignment 9.5.2 Internetprogrammering 1
 * 
 * List files in root directory and send to client
 *
 */
public class NotATrojanThread extends Thread{

    Socket c;
    PrintWriter out;
    File dir = new File("/");
    String[] list;

    public NotATrojanThread(Socket c, String password) throws IOException {
        this.c = c;
        out = new PrintWriter(c.getOutputStream(), true);
        list = dir.list();
    }


    @Override
    public void run() {
        /* send list of files and directories in path given to file*/
        out.println("ok");
        for (String file : list) {
            out.println(file);

        }
        out.close();
    }

}
