package pt.joao.Libsoft.exceptions;

import pt.pa.utils.LanguageManager;

/**
 * TODO
 */
public class FileAlreadyInMemoryException extends RuntimeException{

    public FileAlreadyInMemoryException(String message) {
        super(message);
    }

    public FileAlreadyInMemoryException() {
        super(LanguageManager.getInstance().getString("exception_file_already_in_memory"));
    }
}