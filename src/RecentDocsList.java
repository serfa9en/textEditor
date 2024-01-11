import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class RecentDocsList {
    Editor editor;

    String dataName = "data";
    ArrayList<String> list = new ArrayList<>();

    RecentDocsList(Editor parent){
        super();
        this.editor = parent;
    }

    //создет список
    public void add(String fileName){
        boolean flag = false;
        int index = 0;
        if(list.size() == 5){
            list.remove(4);
        }
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).equals(fileName)) {
                flag = true;
                index = i;
                break;
            }
        }
        if(flag)
            list.remove(index);
        list.add(0, fileName);
        save();
    }

    //сохраняет пути в файл
    public void save(){
        try {
            FileWriter writer = new FileWriter(dataName);
            for (String s : list)
                writer.append(s).append(String.valueOf('\n'));
            writer.close();

        }catch (Exception e){
            System.out.println();
        }
    }

    //выгружает из файла пути в редактор и заполняет список уже существующими путями из файла
    public void load(){
        try {
            File file = new File(dataName);
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            while (bufferedReader.ready())
                add(bufferedReader.readLine());
        }catch (Exception e){
            System.out.println("Error");
        }

        this.editor.form.lastFiles.removeAll();
        int ind = 0;
        for (String name : list) {
            if(ind == 5)
                break;
            this.editor.form.jmi = new JMenuItem(name);
            this.editor.form.lastFiles.add(this.editor.form.jmi);
            this.editor.form.jmi.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    editor.document = new Document(editor);
                    editor.form.saveFile.setEnabled(true);
                    editor.form.saveAsFile.setEnabled(true);
                    editor.form.closeTab.setEnabled(true);
                    editor.open(name);
                }
            });
            ind++;
        }

    }
}
