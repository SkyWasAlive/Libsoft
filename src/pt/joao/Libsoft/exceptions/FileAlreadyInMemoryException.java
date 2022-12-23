package pt.joao.Libsoft.exceptions;

import pt.joao.Libsoft.utils.LanguageManager;

/**
 * TODO
 */
public class FileAlreadyInMemoryException extends RuntimeException{

    public FileAlreadyInMemoryException(String message) {
        super(message);
    }

    public FileAlreadyInMemoryException() {
        super(LanguageManager.getInstance().getString("blank"));
    }
}