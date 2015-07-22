package services;

import java.util.List;

import model.Trend;

public interface TrendService {
	
	/**
	 * 	This method gets and stores the Trend information for each type of Trend
	 */
	public void updateTrends( );
	
	/**
	 * 	This method gets all Trends from Google App Engine's datastore
	 */
	public List<Trend> getAllTrends( );
}
