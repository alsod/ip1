
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
     * @return Container Container holding filelists and arrowbuttons
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

    /**
     * Take array of FTPFiles and add them to remote list and also
     * to a map that maps the files to its index in the list. This to make
     * it possible to retrieve file selected in list
     *
     * @param files List of files to add to remote list component
     */
    public void populateRemoteList(FTPFile[] files) {

        remoteListModel.clear(); // Remove all items in list

        /* Add possibility to go back in folder hierarchy */
        String parent = "..";
        FTPFile parentFile = new FTPFile();
        parentFile.setName("..");

        remoteListModel.addElement(parent);
        int index = remoteListModel.indexOf(parent);
        remoteFileMap.put(index, parentFile);

        /* Add all directories first*/
        for (FTPFile file : files) {
            if (file.isDirectory()) {
                String element = "[folder] " + file.getName();
                remoteListModel.addElement(element);
                index = remoteListModel.indexOf(element);
                remoteFileMap.put(index, file);

            }
        }

        /* Then add all files */
        for (FTPFile file : files) {
            if (file.isFile()) {
                String element = "[file] " + file.getName();
                remoteListModel.addElement(element);
                index = remoteListModel.indexOf(element);
                remoteFileMap.put(index, file);
            }
        }
    }

    /**
     * Take array of Files and add them to remote list and also
     * to a map that maps the files to its index in the list. This to make
     * it possible to retrieve file selected in list
     *
     * @param files List of files to add to local list component
     */
    public void populateLocalList(File[] files) {

        localListModel.clear(); // Remove all items in list

        /* Add possibility to go back in folder hierarchy */
        String parent = "..";
        File parentFile = new File(parent);

        localListModel.addElement(parent);
        int index = localListModel.indexOf(parent);
        localFileMap.put(index, parentFile);


        /* Add all directories */
        for (File file : files) {
            if (file.isDirectory()) {
                String element = "[folder] " + file.getName();
                localListModel.addElement(element);
                index = localListModel.indexOf(element);
                localFileMap.put(index, file);

            }
        }

        /* Add all files */
        for (File file : files) {
            if (file.isFile()) {
                String element = "[file] " + file.getName();
                localListModel.addElement(element);
                index = localListModel.indexOf(element);
                localFileMap.put(index, file);
            }
        }
    }

    /**
     * Get file based on selection in local file list
     * @return selected file
     */
    public File getLocalFile() {
        return localFileMap.get(localList.getSelectedIndex());
    }

    /**
     * Get file based on selection in remote file list
     * @return selected file
     */
    public FTPFile getRemoteFile() {
        return remoteFileMap.get(remoteList.getSelectedIndex());
    }

    /**
     * Add actionlistener to components in menu.
     * @param al actionlistener given by controller
     */
    public void addActionListeners(ActionListener al) {
        uploadButton.setActionCommand("upload");
        uploadButton.addActionListener(al);

        downloadButton.setActionCommand("download");
        downloadButton.addActionListener(al);


    }

    /**
     * Add mouselistener
     * @param ml mouselistener given by controller
     */
    public void setMouseListener(MouseListener ml) {
        localList.setName("localList");
        localList.addMouseListener(ml);

        remoteList.setName("remoteList");
        remoteList.addMouseListener(ml);
    }

    /**
     * Enable and disable the components in this Panel
     * @param bool set components enabled if true, disabled if false
     */
    public void enableGUI(boolean bool) {
        localList.setEnabled(bool);
        remoteList.setEnabled(bool);
        downloadButton.setEnabled(bool);
        uploadButton.setEnabled(bool);
    }
}
