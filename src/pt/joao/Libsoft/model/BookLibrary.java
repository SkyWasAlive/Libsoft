package pt.joao.Libsoft.model;

import pt.joao.Libsoft.exceptions.AuthorAlreadyInTree;
import pt.joao.Libsoft.exceptions.InvalidPositionException;
import pt.joao.Libsoft.graph.Position;
import pt.joao.Libsoft.graph.TreeLinked;

import java.util.concurrent.atomic.AtomicReference;

public class BookLibrary {

    private TreeLinked<BookLibraryEntry> BookEntries;


    public BookLibrary(){
        this.BookEntries = new TreeLinked<>(new BookLibraryEntry("root"));
    }

    public BookLibraryEntry addAuthor(String authorName){
        Position<BookLibraryEntry> root = findAuthor(authorName);
        if(root != null) throw new InvalidPositionException();
        else{
            root = findRoot();
        }
        BookLibraryEntry newEntry = new BookLibraryEntry(authorName);
        this.BookEntries.insert(root,newEntry);
        return newEntry;
    }



    public BookLibraryEntry addTagEntry(String tagName,String authorName){
        Position<BookLibraryEntry> author = findAuthor(authorName);
        if(author == null) throw new InvalidPositionException();

        Position<BookLibraryEntry> tag = findTag(tagName,authorName);
        if (tag != null) throw new InvalidPositionException();
        BookLibraryEntry newEntry = new BookLibraryEntry(tagName);
        this.BookEntries.insert(author,newEntry);
        return newEntry;
    }

    public BookLibraryEntry addBook(String tagName,String authorName,Book book){
        Position<BookLibraryEntry> author = findAuthor(authorName);
        if(author == null) throw new InvalidPositionException();

        Position<BookLibraryEntry> tag = findTag(tagName,authorName);
        if (tag == null) throw new InvalidPositionException();


        BookLibraryEntry newEntry = new BookLibraryEntry(book.getTitle());
        newEntry.setBookValue(book);
        this.BookEntries.insert(tag,newEntry);
        return newEntry;
    }

    private Position<BookLibraryEntry> findRoot(){
        AtomicReference<Position<BookLibraryEntry>> found = new AtomicReference<>();
        this.BookEntries.positions().forEach(bookLibraryEntryPosition -> {
            if (BookEntries.isRoot(bookLibraryEntryPosition)) {
                found.set(bookLibraryEntryPosition);
            }
        });
        return found.get();
    }

    private Position<BookLibraryEntry> findAuthor(String authorName){
        AtomicReference<Position<BookLibraryEntry>> found = new AtomicReference<>();
        this.BookEntries.positions().forEach(bookLibraryEntryPosition -> {
            if (!BookEntries.isRoot(bookLibraryEntryPosition)){
                if (bookLibraryEntryPosition.element().getKey().equalsIgnoreCase(authorName)){
                    found.set(bookLibraryEntryPosition);
                }
            }
        });
        return found.get();
    }
    private Position<BookLibraryEntry> findTag(String tagName,String authorName){
        Position<BookLibraryEntry> authorEntry = findAuthor(authorName);
        AtomicReference<Position<BookLibraryEntry>> found = new AtomicReference<>();
        this.BookEntries.children(authorEntry).forEach(bookLibraryEntryPosition -> {
            if (bookLibraryEntryPosition.element().getKey().equalsIgnoreCase(tagName)){
                found.set(bookLibraryEntryPosition);
            }
        });
        return found.get();
    }

    public String toString() {
        return "BookmarkManager " +
                "size= " + this.BookEntries.size() + " " +
                "{\n" + this.BookEntries + "\n}";
    }
}
