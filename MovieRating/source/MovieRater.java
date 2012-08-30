package com.google.gwt.sample.movierater.client;

import java.util.ArrayList;
import java.util.Collections;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MovieRater implements EntryPoint {
		
	  //private HorizontalPanel mainPanel = new HorizontalPanel();
	  
	  private VerticalPanel moviePanel = new VerticalPanel();	  
	  private FlexTable moviesFlexTable = new FlexTable();
	  
	  private VerticalPanel topFivePanel = new VerticalPanel();
	  private FlexTable topFiveFlexTable = new FlexTable();
	  
	  private VerticalPanel categoryPanel = new VerticalPanel();
	  private FlexTable categoryAvgFlexTable = new FlexTable();
	  
	  private FlexTable addPanel = new FlexTable();
	  private TextBox movieTitle = new TextBox();	  
	  private MultiWordSuggestOracle oracle = new MultiWordSuggestOracle();	 
	  private SuggestBox movieCategory;
	  private ListBox rating = new ListBox(false);
	  private Button addMovieButton = new Button("Add");
	  
	  private HorizontalPanel searchPanel = new HorizontalPanel();
	  private TextBox searchMovie = new TextBox();
	  private Button searchButton = new Button("Search");
	  
	  private ArrayList<Movie> movies = new ArrayList<Movie>();	  
	  private ArrayList<String> categories = new ArrayList<String>();
	  

	  /**
	   * Entry point method.
	   */
	  public void onModuleLoad() {		
		
		// Set the list of suggested categories
		  addCategory("ACTION");
		  addCategory("ROMANCE");
		  addCategory("COMEDY");
		  addCategory("HORROR");
		  addCategory("DRAMA");
		  addCategory("ANIMATION");
		  addCategory("FANTASY");
		  addCategory("CRIME");
		  addCategory("THRILLER");
		  addCategory("SCI-FI");		 
		  
		// Create initial list of movies
		  movies.add(new Movie("THE DARK KNIGHT", "ACTION", 8));
		  movies.add(new Movie("THE GODFATHER", "DRAMA", 9));
		  movies.add(new Movie("KNOCKED UP", "COMEDY", 7));
		  movies.add(new Movie("WARRIORS WAY", "ACTION", 3));	
		  movies.add(new Movie("THE VOW","", 4));
		
		// Display all panel information movies stored in Array List movies
		  favMovies();
		  topMovies();
		  avgCategory();
		  
	    // Create table for movie data.
		  moviesFlexTable.setText(0, 0, "Title");
		  moviesFlexTable.setText(0, 1, "Category");
		  moviesFlexTable.setText(0, 2, "Rating");
		  moviesFlexTable.setText(0, 3, "Remove");
		  
		// Add styles to elements in the stock list table
		  moviesFlexTable.getRowFormatter().addStyleName(0, "watchListHeader");
		  moviesFlexTable.addStyleName("watchList");
		  moviesFlexTable.getCellFormatter().addStyleName(0, 0, "watchListNumericColumn");
		  moviesFlexTable.getCellFormatter().addStyleName(0, 1, "watchListNumericColumn");
		  moviesFlexTable.getCellFormatter().addStyleName(0, 2, "watchListNumericColumn");
		  moviesFlexTable.getCellFormatter().addStyleName(0, 3, "watchListRemoveColumn");
		  moviesFlexTable.setCellPadding(6); 		  
		  
		  // Set the ratings from 1 to 10
		  for (int i = 0; i < 11; i++) {
			  String s = new Integer(i).toString();
			  rating.addItem(s);
		  }
	    
		  // Assemble Add Movie panel.		  
		  //addPanel.add(movieTitle);	
		  addPanel.setText(0, 0, "Title");
		  addPanel.setText(0, 1, "Category");
		  addPanel.setText(0, 2, "Rating");
		  addPanel.setWidget(1, 0, movieTitle);
		  movieCategory = new SuggestBox(oracle);
		  addPanel.setWidget(1, 1, movieCategory);
		  addPanel.setWidget(1, 2, rating);
		  addPanel.setWidget(1, 3, addMovieButton);		  		  
		  addPanel.addStyleName("addPanel");
		  
	    // Assemble Movie panel.
		  //moviePanel.add(addPanel);
		  moviePanel.add(moviesFlexTable);
		  
		// Create table for Top Five Movies		  
		  topFiveFlexTable.setText(0, 0, "Title");
		  topFiveFlexTable.setText(0, 1, "Category");
		  topFiveFlexTable.setText(0, 2, "Rating");
		  
		// Add styles to elements in the stock list table
		  topFiveFlexTable.getRowFormatter().addStyleName(0, "topListHeader");
		  topFiveFlexTable.addStyleName("watchList");
		  topFiveFlexTable.getCellFormatter().addStyleName(0, 0, "watchListNumericColumn");
		  topFiveFlexTable.getCellFormatter().addStyleName(0, 1, "watchListNumericColumn");
		  topFiveFlexTable.getCellFormatter().addStyleName(0, 2, "watchListNumericColumn");		  
		  topFiveFlexTable.setCellPadding(6); 		  
		  
		// Assemble Top Five panel.
		  topFivePanel.add(topFiveFlexTable);
		  
		// Create table for Average Category 
		  categoryAvgFlexTable.setText(0, 0, "Category");
		  categoryAvgFlexTable.setText(0, 1, "Top Movie");
		  categoryAvgFlexTable.setText(0, 2, "Average");
		  
		// Add styles to elements in the stock list table
		  categoryAvgFlexTable.getRowFormatter().addStyleName(0, "catListHeader");
		  categoryAvgFlexTable.addStyleName("watchList");
		  categoryAvgFlexTable.getCellFormatter().addStyleName(0, 0, "watchListNumericColumn");
		  categoryAvgFlexTable.getCellFormatter().addStyleName(0, 1, "watchListNumericColumn");
		  categoryAvgFlexTable.getCellFormatter().addStyleName(0, 2, "watchListNumericColumn");		  
		  categoryAvgFlexTable.setCellPadding(6); 		  
		  
		// Assemble Average Category panel.
		  categoryPanel.add(categoryAvgFlexTable);
		
		//Assemble Search Panel
		  searchPanel.add(searchMovie);
		  searchPanel.add(searchButton);
		  
		  
	    // Associate the Movie, Top Five, and Category panels with the HTML host page.
		  RootPanel.get("addMovie").add(addPanel);
		  RootPanel.get("search").add(searchPanel);
		  RootPanel.get("movieList").add(moviePanel);
		  RootPanel.get("topFiveList").add(topFivePanel);
		  RootPanel.get("categoryAvgList").add(categoryPanel);
		  
	    // Move cursor focus to the title input box.
		  movieTitle.setFocus(true);		
		  
		// Listen for mouse events on the Add button.
		  addMovieButton.addClickHandler(new ClickHandler() {
			  public void onClick(ClickEvent event) {
				  addMovie();
				  topMovies();
				  avgCategory();
			  }
		  });	
		  
		// Listen for mouse events on the Search button.
		  searchButton.addClickHandler(new ClickHandler() {
			  public void onClick(ClickEvent event) {
				  search();
			  }
		  });
		  
		// Listen for keyboard events in the input box.  
		  searchMovie.addKeyPressHandler(new KeyPressHandler() {
			  public void onKeyPress(KeyPressEvent event) {
				  if (event.getCharCode() == KeyCodes.KEY_ENTER) {
					  search();
				  }
			  }
		  });
	  }
	  
	  /*
	   * Search for movie within the movie list then highlight a match
	   */
	  private void search() {
		  String search = searchMovie.getText().toUpperCase().trim();
		  boolean success = false;
		  for (int i = 0; i < movies.size(); i++) {
			  if (search.equals(movies.get(i).getTitle())) {
				  success = true;
				  moviesFlexTable.getCellFormatter().setStyleName(i + 1, 0, "highlightChange");
				  moviesFlexTable.getCellFormatter().setStyleName(i + 1, 1, "highlightChange");
				  moviesFlexTable.getCellFormatter().setStyleName(i + 1, 2, "highlightChange");
				  moviesFlexTable.getCellFormatter().setStyleName(i + 1, 3, "highlightChange");
			  } else {
				  moviesFlexTable.getCellFormatter().setStyleName(i + 1, 0, "watchListNumericColumn");
				  moviesFlexTable.getCellFormatter().setStyleName(i + 1, 1, "watchListNumericColumn");
				  moviesFlexTable.getCellFormatter().setStyleName(i + 1, 2, "watchListNumericColumn");
				  moviesFlexTable.getCellFormatter().setStyleName(i + 1, 3, "watchListRemoveColumn");
			  }			 
		  }
		  
		  if (!success) {
			  Window.alert("'" + search + "'is not listed in your movies.");			  
			  return;
		  }
		  
		  searchMovie.setFocus(true);
		  searchMovie.setText("");
	  }
	  
	  /**
	   * Add movie to FlexTable. Executed when the user clicks the addMovieButton 
	   */
	  private void addMovie() {		  
		  
		  final String title = movieTitle.getText().toUpperCase().trim();
		  movieTitle.setFocus(true);
		  
		  final String category = movieCategory.getText().toUpperCase().trim();
		  
		  final int r = rating.getSelectedIndex();
		  final String rate = new Integer(r).toString();	
		  
		  final Movie current = new Movie(title,category,r);
		  		  
		//Don't add the movie if no category has been chosen
		  if(category.equals("")) {
			  Window.alert("Please choose a category for this movie.");			  
			  return;
		  }		  
		  
		  //Don't add the movie if its in the table
		  for (int i = 0; i < movies.size(); i++) {
			  if(movies.get(i).getTitle().equals(title)){
				 Window.alert("'" + title + "' is already one of your favorite movies.");
				 movieTitle.selectAll();
				 return;
			  }
		  }	
		  
		  movieTitle.setText("");
		  movieCategory.setText("");
		  rating.setSelectedIndex(0);
		  
		  //Add the movie to the table
		  int row = moviesFlexTable.getRowCount();
		  movies.add(current);
		  moviesFlexTable.setText(row, 0, current.getTitle());
		  moviesFlexTable.setText(row, 1, current.getCategory());
		  moviesFlexTable.setText(row, 2, rate);	
		  moviesFlexTable.getCellFormatter().addStyleName(row, 0, "watchListNumericColumn");
		  moviesFlexTable.getCellFormatter().addStyleName(row, 1, "watchListNumericColumn");
		  moviesFlexTable.getCellFormatter().addStyleName(row, 2, "watchListNumericColumn");
		  moviesFlexTable.getCellFormatter().addStyleName(row, 3, "watchListRemoveColumn");
		  
		  //Add a button to remove this movie from the table
		  Button removeStockButton = new Button("x");
		  removeStockButton.addStyleDependentName("remove");
		  removeStockButton.addClickHandler(new ClickHandler() {
			  public void onClick(ClickEvent event) {
				  int removedIndex = movies.indexOf(current);
				  movies.remove(removedIndex);
				  moviesFlexTable.removeRow(removedIndex + 1);
				  //Update Top Five and Category Average
				  topMovies();
				  avgCategory();				  
			  }
		  });
		  moviesFlexTable.setWidget(row, 3, removeStockButton); 
		  
	  }	  
	  
	  /*
	   * Displays the top 5 movies on the movie list based on rating if there are less than 5 movies 
	   */
	  private void topMovies() {		  
		  for (int j= 1; j < topFiveFlexTable.getRowCount(); j++) {
			  topFiveFlexTable.removeRow(j);
		  }		  
		  
		  ArrayList<Movie> sorted = new ArrayList<Movie>();
		  for (int i = 0; i < movies.size(); i++) {
			  sorted.add(movies.get(i));
		  }
		  
		  //Sort the movies by rating
		  Collections.sort(sorted); 		  
		  for (int i = 0; i < sorted.size(); i++) {				  
			  topFiveFlexTable.setText(i + 1, 0, sorted.get(i).getTitle());
			  topFiveFlexTable.setText(i + 1, 1, sorted.get(i).getCategory());
			  String rate = new Integer(sorted.get(i).getRating()).toString();
			  topFiveFlexTable.setText(i + 1, 2, rate);
			  
			//Display N/A for missing fields and highlight red
			  if(sorted.get(i).getCategory().equals("")) {
				  topFiveFlexTable.getCellFormatter().addStyleName(i + 1, 0, "negativeChange");
				  topFiveFlexTable.getCellFormatter().addStyleName(i + 1, 1, "negativeChange");
				  topFiveFlexTable.getCellFormatter().addStyleName(i + 1, 2, "negativeChange");
				  topFiveFlexTable.getCellFormatter().addStyleName(i + 1, 3, "negativeChange");
				  topFiveFlexTable.setText(i + 1, 1, "N/A");
			  } 
			  if(sorted.get(i).getTitle().equals("")) {
				  topFiveFlexTable.getCellFormatter().addStyleName(i + 1, 0, "negativeChange");
				  topFiveFlexTable.getCellFormatter().addStyleName(i + 1, 1, "negativeChange");
				  topFiveFlexTable.getCellFormatter().addStyleName(i + 1, 2, "negativeChange");
				  topFiveFlexTable.getCellFormatter().addStyleName(i + 1, 3, "negativeChange");
				  topFiveFlexTable.setText(i + 1, 0, "N/A");
			  } 
			  
			  topFiveFlexTable.getCellFormatter().addStyleName(i + 1, 0, "watchListNumericColumn");
			  topFiveFlexTable.getCellFormatter().addStyleName(i + 1, 1, "watchListNumericColumn");
			  topFiveFlexTable.getCellFormatter().addStyleName(i + 1, 2, "watchListNumericColumn");
			  topFiveFlexTable.getCellFormatter().addStyleName(i + 1, 3, "watchListRemoveColumn");
		  }
		  
		  if (sorted.size() < 5) {
			  topFiveFlexTable.setText(5, 1, "Add More Movies");
		  }
	  }	 
	  
	  /*
	   * Displays the average rating of each category
	   */
	  private void avgCategory() {
		  for (int k = 1; k < categoryAvgFlexTable.getRowCount(); k++) {
			  categoryAvgFlexTable.removeRow(k);
		  }
		  
		  ArrayList<String> listedCategories = new ArrayList<String>();
		  for (int t = 0; t < movies.size(); t++) {
			  if (!(listedCategories.contains(movies.get(t).getCategory())) && !(movies.get(t).getCategory().equals(""))) {
				  listedCategories.add(movies.get(t).getCategory());
			  }
		  }
		  
		  for (int i = 0; i < listedCategories.size(); i++) {
			  Movie max = new Movie("","",0);
			  String cat = listedCategories.get(i);
			  double total = 0.0;
			  double count = 0.0;
			  double average = 0.0;
			  for (int j = 0; j < movies.size(); j++) {
				  Movie mov = movies.get(j);				  
				  if(mov.getCategory().equals(cat)) {
					  total = total + mov.getRating();
					  count++;
				  }
				  if(mov.compareTo(max) <= 0 && mov.getCategory().equals(cat)) {
					  max = mov;
				  }				  
			  }
			  average = total / count;
			  categoryAvgFlexTable.setText(i + 1, 0, cat);
			  categoryAvgFlexTable.setText(i + 1, 1, max.getTitle());
			  categoryAvgFlexTable.setText(i + 1, 2, Double.toString(average));
			  
			  categoryAvgFlexTable.getCellFormatter().addStyleName(i + 1, 0, "watchListNumericColumn");
			  categoryAvgFlexTable.getCellFormatter().addStyleName(i + 1, 1, "watchListNumericColumn");
			  categoryAvgFlexTable.getCellFormatter().addStyleName(i + 1, 2, "watchListNumericColumn");
			  categoryAvgFlexTable.getCellFormatter().addStyleName(i + 1, 3, "watchListRemoveColumn");
		  }
	  }
	  
	  /*
	   * Adds a category to the list of categories if not already in the list
	   */
	  private void addCategory(String c) {
		  if(!(categories.contains(c))) {
			  categories.add(c);
			  oracle.add(c);
		  }
	  } 
	  
	  
	  /*
	   * Displays the favorite movies stored in the Array List movies
	   */
	  private void favMovies() {		 
		  for (int i = 0; i < movies.size(); i++) {
			  Movie m = movies.get(i);			  
			  final Movie j = m;  
			  
			  moviesFlexTable.setText(i + 1, 0, m.getTitle());
			  moviesFlexTable.setText(i + 1, 1, m.getCategory());
			  String rate = new Integer(m.getRating()).toString();
			  moviesFlexTable.setText(i + 1, 2, rate);			  
			  
			  //Display N/A for missing fields and highlight red
			  if(m.getCategory().equals("")) {
				  moviesFlexTable.getCellFormatter().addStyleName(i + 1, 0, "negativeChange");
				  moviesFlexTable.getCellFormatter().addStyleName(i + 1, 1, "negativeChange");
				  moviesFlexTable.getCellFormatter().addStyleName(i + 1, 2, "negativeChange");
				  moviesFlexTable.getCellFormatter().addStyleName(i + 1, 3, "negativeChange");
				  moviesFlexTable.setText(i + 1, 1, "N/A");
			  } 
			  if(m.getTitle().equals("")) {
				  moviesFlexTable.getCellFormatter().addStyleName(i + 1, 0, "negativeChange");
				  moviesFlexTable.getCellFormatter().addStyleName(i + 1, 1, "negativeChange");
				  moviesFlexTable.getCellFormatter().addStyleName(i + 1, 2, "negativeChange");
				  moviesFlexTable.getCellFormatter().addStyleName(i + 1, 3, "negativeChange");
				  moviesFlexTable.setText(i + 1, 0, "N/A");
			  } 
			  
			  moviesFlexTable.getCellFormatter().addStyleName(i + 1, 0, "watchListNumericColumn");
			  moviesFlexTable.getCellFormatter().addStyleName(i + 1, 1, "watchListNumericColumn");
			  moviesFlexTable.getCellFormatter().addStyleName(i + 1, 2, "watchListNumericColumn");
			  moviesFlexTable.getCellFormatter().addStyleName(i + 1, 3, "watchListRemoveColumn");
			  
			  //Add a button to remove this movie from the table
			  Button removeStockButton = new Button("x");
			  removeStockButton.addStyleDependentName("remove");
			  removeStockButton.addClickHandler(new ClickHandler() {
				  public void onClick(ClickEvent event) {					  
					  int removedIndex = movies.indexOf(j);
					  movies.remove(removedIndex);
					  moviesFlexTable.removeRow(removedIndex + 1);
					  //Update Top Five and Category Average
					  topMovies();
					  avgCategory();					  
				  }
			  });
			  moviesFlexTable.setWidget(i + 1, 3, removeStockButton); 
		  }
	  }
	}