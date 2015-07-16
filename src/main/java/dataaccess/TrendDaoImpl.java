package dataaccess;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

import model.Trend;
import model.TrendData;
import model.TrendType;

public class TrendDaoImpl implements TrendDao {
	private DatastoreService dataStoreService = DatastoreServiceFactory.getDatastoreService();

	/**
	 * Inserts List of trends into datastore
	 * 
	 * @param trends
	 *            The trends to insert
	 */
	public void updateTrends(List<Trend> trends) {
		deleteAllTrends();
		for (Trend trend : trends) {
			dataStoreService.put(createTrendEntity(trend));
		}
	}

	/**
	 * Queries the data store and returns all Trends stored
	 * 
	 * @return List of trends
	 */
	public List<Trend> getAllTrends() {
		Query q = new Query("Trend");
		PreparedQuery pq = dataStoreService.prepare(q);

		return getTrendsFromPreparedQuery(pq);

	}

	/**
	 * Returns the list of trends from the query
	 * 
	 * @param pq
	 * @return List of trends
	 */
	private List<Trend> getTrendsFromPreparedQuery(PreparedQuery pq) {
		List<Trend> trends = new ArrayList<Trend>();
		for (Entity trendEntity : pq.asIterable()) {
			Trend trend = new Trend(TrendType.valueOf((String)trendEntity.getProperty("TrendType")));
			List<TrendData> trendData = getTrendDataListFromProperty((List<String>) trendEntity.getProperty("TrendData"));
			trend.setTrendData(trendData);
			trends.add(trend);
		}
		return trends;
	}

	/**
	 * Converts List<String> stored in database to List<TrendData>
	 * 
	 * @param stringTrendDataList
	 * @return List<TrendData>
	 */
	private List<TrendData> getTrendDataListFromProperty(List<String> stringTrendDataList) {
		List<TrendData> trendDataList = new ArrayList<TrendData>();
		for (String dataStr : stringTrendDataList) {
			String[] split = dataStr.split(",");
			TrendData data = new TrendData(split[0], Integer.parseInt(split[1]));
			trendDataList.add(data);
		}
		return trendDataList;
	}

	/**
	 * Creates trend Entity and returns it
	 * 
	 * @param trend
	 * @return Entity
	 */
	private Entity createTrendEntity(Trend trend) {

		Entity trendEntity = new Entity("Trend");
		List<String> trendDataStringList = getTrendDataStringList(trend);
		trendEntity.setProperty("TrendType", trend.getTrendType().name());
		trendEntity.setProperty("TrendData", trendDataStringList);

		return trendEntity;
	}

	/**
	 * We can't store a List<Object> as a property so we have to convert our
	 * List<TrendData> to a List<String> where each string is in the format
	 * "VALUE,COUNT"
	 * 
	 * @param trend
	 *            Trend to convert to List<TrendData> to List<String>
	 * @return List of strings
	 */
	private List<String> getTrendDataStringList(Trend trend) {
		List<String> trendDataStringList = new ArrayList<String>();
		for (TrendData trendData : trend.getTrendData()) {
			trendDataStringList.add(trendData.toString());
		}
		return trendDataStringList;
	}

	/**
	 * Deletes all trends from datastore
	 */
	private void deleteAllTrends() {
		Query q = new Query("Trend");
		PreparedQuery pq = dataStoreService.prepare(q);

		for (Entity trendEntity : pq.asIterable()) {
			dataStoreService.delete(trendEntity.getKey());
		}
	}
}
