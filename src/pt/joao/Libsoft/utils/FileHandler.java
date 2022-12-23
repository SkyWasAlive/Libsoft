package pt.joao.Libsoft.utils;

import javafx.scene.control.Alert;
import pt.joao.Libsoft.exceptions.*;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * This class allows the handling files, and extract only the usefull data from a file, it allows the use of deny filters, to deny the extraction of said lines,4
 * for exemple a line started with #(comment) can be ignored if it is set on the filters.
 * the filters work with wildcards following the following syntax:
 *
 * example* -> it will match any line started with example
 * *example -> it will match anu line finishing with example
 * *example* -> it will match any line with example in any position of the line, for exemple:
 *          "lol example" -> this would get matched
 *           "lexample"   -> gets matched
 *           "examTple    -> doesn't get matched
 * @version 12/12/2022
 * @author João Gouveia
 */
public class FileHandler {

    private static final FileHandler instance = new FileHandler();
    private final List<File> files;


    public static FileHandler getInstance(){
         return instance;
    }
    private FileHandler(){
        this.files = new ArrayList<>();
    }

    private List<String> getAllFileContents(File fileReference) throws IOException {
        return readFile(new ArrayList<>(),fileReference);
    }


    /**
     * Gets data from a file given a deny list serving as filter, allowing to skip comments, headers, blank line etc,or if we want it all, sending a empty
     * ArrayList will return all data from file
     * @param fileReference checked file to read
     * @param filters the filters for the deny list
     * @return List of data read from file,  not including data from denied lines.
     * @throws IOException if theres an error reading fule
     */
    public List<String> getFileData(File fileReference,ArrayList<String> filters) throws IOException {
        if (filters.size() == 0){
            return getAllFileContents(fileReference);
        }
        return readFile(filters,fileReference);
    }


    /**
     * opens a file using given uri(universal resource identifier), to identify a file,and makes sure given file exists on the given uri, and if so returns the file
     * @param fileURI uri to check file
     * @return file checked
     */
    public File openFile(URI fileURI){
        File fileToOpen = new File(fileURI);
        if (fileToOpen.exists()) this.files.add(fileToOpen);
        else{
            new ExceptionDialog(Alert.AlertType.ERROR,new FileDoesntExists(),"An Unexpected error occurred","Failed to open file","Couldn't find file " + fileToOpen.getName());
        }
        return fileToOpen;
    }

    /**
     * gets all directories on given uri
     * @param folderReference given uri to list
     * @return list of directories
     */
    public static List<String> getAllDirectories(URI folderReference){
        File folderSearch = new File(folderReference);
        List<String> items = new ArrayList<>();
        if (folderSearch.exists()){
            Arrays.stream(folderSearch.listFiles()).filter(folderSearchItem ->(folderSearchItem.isDirectory())).forEach(folder -> {
                     items.add(folder.getName());
            }
            );
        }
        return items;
    }

    public static List<String> getAllFilesInDirectory(URI folderReference){
        File folderSearch = new File(folderReference);
        List<String> items = new ArrayList<>();
        if (folderSearch.exists()){
            Arrays.stream(folderSearch.listFiles()).filter(folderSearchItem ->(folderSearchItem.isFile())).forEach(folder -> {
                        items.add(folder.getName());
                    }
            );
        }
        return items;
    }


    private List<String> readFile(List<String> filters,File fileReference) throws IOException {
        FileInputStream fis = new FileInputStream(fileReference);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        List<String> fileContents = new ArrayList<>();
        reader.lines().forEach(line -> {
            AtomicBoolean filterHit = new AtomicBoolean(false);
            if (filters.isEmpty()) {
                fileContents.add(line);
            } else {
                filters.forEach(filter -> {
                    if (filter.endsWith("*")) {
                        if (filterMatch(line.trim(), filter)) {
                            filterHit.set(true);
                        }
                    }
                });
                if (!filterHit.get()){
                    fileContents.add(line);
                }
            }
        });
        reader.close();
        fis.close();
        return fileContents;
    }

    private boolean filterMatch(String target,String filter){
        if (target.equalsIgnoreCase("")) return true;
        if (filter.endsWith("*")){
            return target.toLowerCase().startsWith(filter.toLowerCase().replace("*",""));
        }else if (filter.startsWith("*")){
            return target.toLowerCase().endsWith(filter.toLowerCase().replace("*",""));
        }else if (filter.startsWith("*") && filter.endsWith("*")){
            return target.toLowerCase().contains(filter.toLowerCase().replace("*",""));
        }else{
            return target.equalsIgnoreCase(filter);
        }
    }
}
