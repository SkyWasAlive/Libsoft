package pt.joao.Libsoft.exceptions;

import pt.pa.utils.LanguageManager;

/**
 * TODO
 */
public class FileNotInMemory extends RuntimeException{

    public FileNotInMemory(String message) {
        super(message);
    }

    public FileNotInMemory() {
        super(LanguageManager.getInstance().getString("exception_file_not_in_memory"));
    }
}
