package pt.joao.Libsoft.exceptions;

import pt.joao.Libsoft.utils.LanguageManager;

/**
 * TODO
 */
public class FileNotInMemory extends RuntimeException{

    public FileNotInMemory(String message) {
        super(message);
    }

    public FileNotInMemory() {
        super(LanguageManager.getInstance().getString("blank"));
    }
}
