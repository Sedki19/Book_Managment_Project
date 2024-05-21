package Book.entities;

import java.util.Comparator;



public class CompareRate implements Comparator<Book> {

	@Override
	public int compare(Book o1, Book o2) {
		return (o2.getRating() - o1.getRating());
		
	}

}
