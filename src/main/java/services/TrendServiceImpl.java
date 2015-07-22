package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dataaccess.DogDao;
import dataaccess.DogDaoImpl;
import dataaccess.TrendDao;
import dataaccess.TrendDaoImpl;
import model.Dog;
import model.Trend;
import model.TrendType;

public class TrendServiceImpl implements TrendService {

	private DogDao dogDao;
	private TrendDao trendDao;

	/*
	 *  Class constructor
	 */
	public TrendServiceImpl() {
		setDogDao(new DogDaoImpl());
		setTrendDao(new TrendDaoImpl());
	}
	//Sets dogDao (Dog data-access object)
	public void setDogDao(DogDaoImpl dogDao) {
		this.dogDao = dogDao;
	}
	//Sets trendDao (Trend data-access object)
	public void setTrendDao(TrendDaoImpl trendDao) {
		this.trendDao = trendDao;
	}

	/**
	 * 	Gets and stores the Trend information for each type of Trend
	 */
	public void updateTrends() {
		Set<Dog> dogs = dogDao.getAllDogs();
		List<Trend> trends = new ArrayList<Trend>();
		trends.add(getTrend(dogs, TrendType.MOST_POPULAR_NAME));
		trends.add(getTrend(dogs, TrendType.MOST_POPULAR_BREED));
		trends.add(getTrend(dogs, TrendType.MOST_POPULATED_CITY));
		trendDao.updateTrends(trends);//Store calculated Trends
	}

	/**
	 * 	Gets all Trends from datastore
	 */
	public List<Trend> getAllTrends() {
		return trendDao.getAllTrends();
	}

	/**
	 * Gets Trend information based on TrendType specified
	 * @param dogs
	 * 		The dataset of Dogs
	 * @param trendType
	 * 		The type of trend to calculate
	 * @return
	 * 		Trend object containing trend information
	 */
	private Trend getTrend(Set<Dog> dogs, TrendType trendType) {
		Trend trend = new Trend(trendType);//Create trend object of type trendType
		trend.createTrendData(dogs);//Calculate trends
		return trend;
	}

}
