import pt.joao.Libsoft.model.Book;
import pt.joao.Libsoft.model.BookLibrary;

public class Main {
    public static void main(String[] args) {
        BookLibrary model = new BookLibrary();
        model.addAuthor("Autor 1");
        model.addAuthor("Autor 2");
        model.addAuthor("Autor 3");
        model.addTagEntry("Romances","Autor 1");
        model.addTagEntry("Policiais","Autor 1");
        model.addTagEntry("Policiais","Autor 2");
        model.addBook("Romances","Autor 1",new Book("Livro 1"));
        model.addBook("Romances","Autor 1",new Book("Livro 2"));
        model.addBook("Policiais","Autor 1",new Book("Livro 3"));
        model.addBook("Policiais","Autor 2",new Book("Livro 4"));
        System.out.println(model);
    }
}