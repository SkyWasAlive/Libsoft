package pt.joao.Libsoft.exceptions;

import pt.pa.utils.LanguageManager;

public class LanguageCodeNotExistent extends RuntimeException{
    public LanguageCodeNotExistent() {
        super(LanguageManager.getInstance().getString("exception_invalid_language_code"));
    }

    public LanguageCodeNotExistent(String message) {
        super(message);
    }
}
