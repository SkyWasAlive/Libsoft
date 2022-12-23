package pt.joao.Libsoft.utils;

import javafx.scene.control.Alert;
import pt.joao.Libsoft.exceptions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 *  This class is responsable for the loading of multiple language data files, allowing to change the language in runtime without restarting the application,
 *  it uses Id's and languages codes for detecting the correct string value
 * @author Jo&atilde;o Gouveia
 */
public class LanguageManager {


    private static LanguageManager instance = new LanguageManager();
    private String languageCode;
    private FileHandler fileHandler = FileHandler.getInstance();
    private HashMap<String, HashMap<String,String>> strings;


    public static LanguageManager getInstance(){
        return instance;
    }

    private LanguageManager() {
        this.strings = new HashMap<>();
        FileHandler.getAllFilesInDirectory(Paths.get("Languages").toUri()).forEach(file ->{
            if (file.equals("pref.txt")) return;
            ArrayList<String> filters = new ArrayList<>();
            filters.add("#*");
            filters.add("string_id*");
            File fileReference = fileHandler.openFile(Paths.get("Languages/" + file).toUri());
            try {
                fileHandler.getFileData(fileReference,filters).forEach(string ->{
                    if (strings.get(file.replace(".txt","")) == null){
                        HashMap<String,String> stringMap = new HashMap<>();
                        String[] splitData = string.split("\t");
                        stringMap.put(splitData[0],splitData[1]);
                        strings.put(file.replace(".txt",""),stringMap);
                    }else{
                        String[] splitData = string.split("\t");
                        strings.get(file.replace(".txt","")).put(splitData[0],splitData[1]);
                    }
                });
            } catch (IOException e) {
                ExceptionDialog exceptionDialog = new ExceptionDialog(Alert.AlertType.ERROR,e);
                exceptionDialog.show();
            }
        });

    }

    /**
     * sets the language codee to be used, this can be changed during runtime
     * @param languageCode the language code to be used
     */
    public void setLanguage(String languageCode){
        if (this.strings.get(languageCode) == null){
            this.languageCode = "en";
            ExceptionDialog exceptionDialog = new ExceptionDialog(Alert.AlertType.ERROR,new LanguageCodeNotExistent());
            exceptionDialog.show();
        }
        this.languageCode = languageCode;
    }

    /**
     * Obtains a string of the selected language value.
     * @param id of the string to obtain
     * @return string obtained from the list of strings
     */
    public String getString(String id){
        String returnValue = null;
        try{
            returnValue  = this.strings.get(this.languageCode).get(id);
        }catch(NullPointerException exception){
            ExceptionDialog exceptionDialog = new ExceptionDialog(Alert.AlertType.ERROR,new LanguageStringNotFound());
            exceptionDialog.show();
        }
        if (returnValue == null){
            ExceptionDialog exceptionDialog = new ExceptionDialog(Alert.AlertType.ERROR,new LanguageStringNotFound());
        exceptionDialog.show();}
        return returnValue;
    }

    public List<String> getAllStrings(){
        return this.strings.get(this.languageCode).values().stream().toList();
    }
}
