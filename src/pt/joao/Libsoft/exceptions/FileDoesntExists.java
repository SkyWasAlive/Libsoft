package pt.joao.Libsoft.exceptions;

import pt.pa.utils.LanguageManager;

/**
 * TODO
 */
public class FileDoesntExists extends RuntimeException{

    public FileDoesntExists(String message) {
        super(message);
    }

    public FileDoesntExists() {
        super(LanguageManager.getInstance().getString("exception_file_doesnt_exist"));
    }
}