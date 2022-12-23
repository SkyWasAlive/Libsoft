package pt.joao.Libsoft.exceptions;

import pt.joao.Libsoft.utils.LanguageManager;

public class LanguageCodeNotExistent extends RuntimeException{
    public LanguageCodeNotExistent() {
        super(LanguageManager.getInstance().getString("blank"));
    }

    public LanguageCodeNotExistent(String message) {
        super(message);
    }
}
