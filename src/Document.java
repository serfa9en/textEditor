import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.FileReader;

public class Document extends Panel {
    Editor editor;

    String filePath;
    String titleTab; //имя вкладки
    boolean modified = false;
    String text = "";

    JTextArea textArea;
    FileOutputStream fileOutput;

    public Document(Editor parent){
        super();
        this.editor = parent;
        textArea = new JTextArea(25,50);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        this.add(textArea, BorderLayout.CENTER);
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                modified = false;
            }
        });
    }

    //загружает в открытое поле для текста текст из выбранного файла
    public void open(String fileName){
        StringBuilder buffer = new StringBuilder();

        try{
            FileReader fileReader = new FileReader(fileName);
            int c;
            while ((c = fileReader.read()) != -1){
                buffer.append((char) c);
            }
        }catch (Exception e){
            System.out.println();
        }
        textArea.setText(buffer.toString());
        text = textArea.getText();
    }

    public void save(String filename){
        try {
            fileOutput = new FileOutputStream(filename);
            String str = textArea.getText();
            byte[] buffer = str.getBytes();
            fileOutput.write(buffer);
        }catch (Exception e){
            System.out.println();
        }
    }
}
