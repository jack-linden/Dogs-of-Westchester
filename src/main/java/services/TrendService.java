package services;

import java.util.List;

import model.Trend;

public interface TrendService {
	
	/**
	 * 	Gets and stores the Trend information for each type of Trend
	 */
	public void updateTrends( );
	
	/**
	 * 	Gets all Trends from datastore
	 */
	public List<Trend> getAllTrends( );
}
