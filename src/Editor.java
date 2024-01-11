import javax.swing.*;
import java.io.File;

public class Editor extends JTabbedPane {
    Form form;
    Document document;
    RecentDocsList recentDocsList;

    File file;

    public Editor(Form parent){
        super();
        this.form = parent;
        this.recentDocsList = new RecentDocsList(this);
    }

    private Document selected(){
        this.form.tab.setSelectedComponent(document);
        return document;
    }

    //открывается новая вкладка
    public void newTab(){
        this.document = new Document(this);
        this.form.saveFile.setEnabled(true);
        this.form.saveAsFile.setEnabled(true);
        this.form.closeTab.setEnabled(true);
        document.titleTab = "[Untitle]";
        this.form.tab.addTab(document.titleTab, document);
        this.document = this.selected();
    }

    //открывается директива для выбора документа который хотим открыть
    public void openDoc(){
        this.document = new Document(this);
        this.form.saveFile.setEnabled(true);
        this.form.saveAsFile.setEnabled(true);
        this.form.closeTab.setEnabled(true);

        String fileName = "";
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int ret = fileChooser.showDialog(null, "Выбрать файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            fileName = file.getName();
            open(fileName);
            recentDocsList.add(fileName);
            document.filePath = fileName;
        }
    }

    public void open(String fileName){
        document.titleTab = fileName;
        this.form.tab.add(document, document.titleTab);
        this.document = this.selected();
        document.open(fileName);
    }

    //сохранить, если сохраняет первый раз -> saveAs()
    public void save(){
        file = new File(document.titleTab);
        if(file.isFile()){
            document.save(file.getName());
            document.titleTab = file.getName();
            document.modified = true;
        }else
            saveAs();
    }

    //сохраняем первый раз, открывается директория для сохранения документа
    public void saveAs(){
        document.modified = true;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int ret = fileChooser.showSaveDialog(this);
        if (ret == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            document.save(file.getName()+".txt");
        }
        //переименуем вкладку
        this.form.tab.setTitleAt(this.form.tab.getSelectedIndex(), file.getName()+".txt");
        recentDocsList.add(file.getName()+".txt");
    }

    //сохранить?
    public void closeDoc(){
        if(!document.textArea.getText().equals(document.text)){
            int result = JOptionPane.showConfirmDialog(
                    null,
                    "Сохранить изменения в документе "+document.titleTab+"?",
                    "Окно подтверждения",
                    JOptionPane.YES_NO_CANCEL_OPTION);
            if(result == JOptionPane.YES_OPTION){
                save();
                close();
            }else
            if(result == JOptionPane.NO_OPTION){
                document.modified = true;
                close();
            }
        }else {
            document.modified = true;
            close();
        }
    }

    //закрытие текущей вкладки
    public void close(){
        if(!document.modified){
            closeDoc();
        }else {
            int i = this.form.tab.getSelectedIndex();
            if (i != -1) {
                this.form.tab.remove(i);
            }
            recentDocsList.load();
        }
        if(this.form.tab.getTabCount() == 0){
            this.form.saveFile.setEnabled(false);
            this.form.saveAsFile.setEnabled(false);
            this.form.closeTab.setEnabled(false);
        }
    }
}
