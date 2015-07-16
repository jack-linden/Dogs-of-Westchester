package dataaccess;

import java.util.List;

import model.Trend;

public interface TrendDao {

	/**
	 * Inserts List of trends into datastore
	 * 
	 * @param trends
	 *            The trends to insert
	 */
	public void updateTrends(List<Trend> trends);

	/**
	 * Queries the data store and returns all Trends stored
	 * 
	 * @return List of trends
	 */
	public List<Trend> getAllTrends();
}
