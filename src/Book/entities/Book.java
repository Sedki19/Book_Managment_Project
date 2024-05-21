package Book.entities;

import java.util.Date;



public class Book implements Comparable<Book>{
	private int id;
	private String title;
	private String author;
	private Date addedDate;
	private int rating;
	private String status;
	private int bUser;
	
	public Book(int id, String title, String author, Date addedDate, int rating, String status, int bUser) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.addedDate = addedDate;
		this.rating = rating;
		this.status = status;
		this.bUser = bUser;
	}
	
	public Book( String title, String author, Date addedDate, int rating, String status /*,int bUser*/) {
		this.title = title;
		this.author = author;
		this.addedDate = addedDate;
		this.rating = rating;
		this.status = status;
		/*this.bUser = bUser;*/
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getbUser() {
		return bUser;
	}

	public void setbUser(int bUser) {
		this.bUser = bUser;
	}

	@Override
	public int compareTo(Book o) {
		
		if(this.addedDate.before(o.addedDate)) {
			return 1;
		}
		else if (this.addedDate.after(o.addedDate)) {
			return -1;
		}
		else {
			return 0;
		}
	}
	
	
	
	
}
