package com.google.gwt.sample.movierater.client;

public class Movie implements Comparable<Object> {
	private String title;
	private String category;
	private int rating;
	
	/*
	 * Movie Constructor creates an instance of movie with a title, category, and rating
	 */
	public Movie(String t, String c, int r) {
		title = t;
		category = c;
		rating = r;
	}	
	
	/*
	 * Returns the title of this instance of movie
	 */
	public String getTitle() {
		return title;
	}
	
	/*
	 * Returns the category of this instance of category
	 */
	public String getCategory() {
		return category;
	}
	
	/*
	 * Returns the rating of this instance of rating
	 */
	public int getRating() {
		return rating;
	}	
	
	/*
	 * Compares two movies ratings where 1 means movie is less, -1 means movie is greater, 0 means movies are equal
	 */
	@Override
	public int compareTo(Object o) {
		Movie m = (Movie) o;
		int comp = 0;
		if(this.rating < m.rating) {
			comp = 1;
		}
		if(this.rating == m.rating) {
			comp = this.title.compareTo(m.title);
		}
		if(this.rating > m.rating) {
			comp = -1;
		}
		return comp;
	}
	
}
