
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicArrowButton;
import org.apache.commons.net.ftp.FTPFile;

/**
 * @author Andre Karlsson
 *
 * Class is used to create a pane to be used in the main window of the application
 */
public class ContentPane {

    private DefaultListModel localListModel;
    private DefaultListModel remoteListModel;
    private JButton uploadButton;
    private JButton downloadButton;
    private JList localList;
    private JList remoteList;
    private JScrollPane scrollPaneLeft;
    private JScrollPane scrollPaneRight;
    Map<Integer, File> localFileMap = new HashMap<Integer, File>();
    Map<Integer, FTPFile> remoteFileMap = new HashMap<Integer, FTPFile>();

    /**
     * Create components and add them to a Container which is then returned to caller
     * @return Container
     */
    public Container createContentPane() {
        //Create the content-pane-to-be.
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setOpaque(true);

        JPanel arrowPane = new JPanel();
        arrowPane.setLayout(new BoxLayout(arrowPane, BoxLayout.Y_AXIS));

        // Create file lists
        localListModel = new DefaultListModel();
        localList = new JList(localListModel);
        localList.setSelectedIndex(0);
        localList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        uploadButton = new BasicArrowButton(SwingConstants.EAST);
        downloadButton = new BasicArrowButton(SwingConstants.WEST);

        arrowPane.add(downloadButton);
        arrowPane.add(uploadButton);

        remoteListModel = new DefaultListModel();
        remoteList = new JList(remoteListModel);
        remoteList.setSelectedIndex(0);
        remoteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        scrollPaneLeft = new JScrollPane(localList);
        scrollPaneRight = new JScrollPane(remoteList);

        scrollPaneLeft.setPreferredSize(new Dimension(340, 460));
        scrollPaneRight.setPreferredSize(new Dimension(340, 460));

        //Add the text area to the content pane.
        contentPane.add(scrollPaneLeft, BorderLayout.WEST);
        contentPane.add(scrollPaneRight, BorderLayout.EAST);
        contentPane.add(arrowPane, BorderLayout.CENTER);
        return contentPane;
    }

    public void populateRemoteList(FTPFile[] files) {

        for (FTPFile file : files) {
            if (file.isDirectory()) {
                String element = "[folder] " + file.getName();
                remoteListModel.addElement(element);
                int index = remoteListModel.indexOf(element);
                remoteFileMap.put(index, file);

            }
        }

        for (FTPFile file : files) {
            if (file.isFile()) {
                String element = "[file] " + file.getName();
                remoteListModel.addElement(element);
                int index = remoteListModel.indexOf(element);
                remoteFileMap.put(index, file);
            }
        }
    }

    public void populateLocalList(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                String element = "[folder] " + file.getName();
                localListModel.addElement(element);
                int index = localListModel.indexOf(element);
                localFileMap.put(index, file);

            }
        }

        for (File file : files) {
            if (file.isFile()) {
                String element = "[file] " + file.getName();
                localListModel.addElement(element);
                int index = localListModel.indexOf(element);
                localFileMap.put(index, file);
            }
        }
    }

    public File getLocalFile() {
        return localFileMap.get(localList.getSelectedIndex());
    }

    public FTPFile getRemoteFile() {
        return remoteFileMap.get(remoteList.getSelectedIndex());
    }

    public void setMouseListener(MouseListener ml) {
        localList.setName("localList");
        localList.addMouseListener(ml);

        remoteList.setName("remoteList");
        remoteList.addMouseListener(ml);
    }
}
