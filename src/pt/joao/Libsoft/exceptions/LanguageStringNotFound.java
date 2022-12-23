package pt.joao.Libsoft.exceptions;

import pt.joao.Libsoft.utils.LanguageManager;

public class LanguageStringNotFound extends RuntimeException{
    public LanguageStringNotFound() {
        super(LanguageManager.getInstance().getString("blank"));
    }

    public LanguageStringNotFound(String message) {
        super(message);
    }
}
