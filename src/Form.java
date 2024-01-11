import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Form extends JFrame {

    Editor editor;
    RecentDocsList recentDocsList;

    public JTabbedPane tab;
    public JPanel panel;
    public JButton closeTab;
    public JMenuItem saveFile;
    public JMenuItem saveAsFile;
    public JMenu lastFiles;
    public JMenuItem jmi;

    public Form(){
        super();
        this.editor = new Editor(this);
        editor.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);

        JFrame frame = new JFrame("Редактор");
        frame.setContentPane(panel);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);

        JMenuItem newFile = new JMenuItem("Новый");
        fileMenu.add(newFile);
        newFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.newTab();
            }
        });

        JMenuItem openFile = new JMenuItem("Открыть");
        fileMenu.add(openFile);
        openFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.openDoc();
            }
        });

        saveFile = new JMenuItem("Сохранить");
        fileMenu.add(saveFile);
        saveFile.setEnabled(false);
        saveFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.save();
            }
        });

        saveAsFile = new JMenuItem("Сохранить как");
        fileMenu.add(saveAsFile);
        saveAsFile.setEnabled(false);
        saveAsFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.saveAs();
            }
        });

        lastFiles = new JMenu("Последние");
        fileMenu.add(lastFiles);
        recentDocsList = new RecentDocsList(editor);
        recentDocsList.load();

        fileMenu.addSeparator();

        JMenuItem exitFile = new JMenuItem("Выход");
        fileMenu.add(exitFile);
        exitFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });

        closeTab.setEnabled(false);
        closeTab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.close();
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,500);
        frame.setVisible(true);
    }

    public static void main(String[] args){
        new Form();
    }
}
