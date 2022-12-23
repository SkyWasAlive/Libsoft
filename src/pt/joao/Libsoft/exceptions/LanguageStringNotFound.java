package pt.joao.Libsoft.exceptions;

import pt.pa.utils.LanguageManager;

public class LanguageStringNotFound extends RuntimeException{
    public LanguageStringNotFound() {
        super(LanguageManager.getInstance().getString("exception_invalid_language_string"));
    }

    public LanguageStringNotFound(String message) {
        super(message);
    }
}
