package pt.joao.Libsoft.model;

public class BookLibraryEntry {
    private String key;
    private Book element;

    public BookLibraryEntry(String key) {
        this.key = key;
    }

    public String getKey(){
        return this.key;
    }

    public Book setBookValue(Book book){
        this.element = book;
        return this.element;
    }

    @Override
    public String toString() {
        if (element == null) return this.key;
        else{
            return  this.element.getTitle();
        }
    }
}
