package pt.joao.Libsoft.exceptions;

import pt.joao.Libsoft.utils.LanguageManager;

/**
 * TODO
 */
public class FileDoesntExists extends RuntimeException{

    public FileDoesntExists(String message) {
        super(message);
    }

    public FileDoesntExists() {
        super(LanguageManager.getInstance().getString("blank"));
    }
}