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

	public TrendServiceImpl() {
		setDogDao(new DogDaoImpl());
		setTrendDao(new TrendDaoImpl());
	}

	public void setDogDao(DogDaoImpl dogDao) {
		this.dogDao = dogDao;
	}

	public void setTrendDao(TrendDaoImpl trendDao) {
		this.trendDao = trendDao;
	}

	public void updateTrends() {
		Set<Dog> dogs = dogDao.getAllDogs();
		List<Trend> trends = new ArrayList<Trend>();
		trends.add(getTrend(dogs, TrendType.MOST_POPULAR_NAME));
		trends.add(getTrend(dogs, TrendType.MOST_POPULAR_BREED));
		trends.add(getTrend(dogs, TrendType.MOST_POPULATED_CITY));
		trendDao.updateTrends(trends);

	}

	public List<Trend> getAllTrends() {
		return trendDao.getAllTrends();
	}

	private Trend getTrend(Set<Dog> dogs, TrendType trendType) {
		Trend trend = new Trend(trendType);
		trend.createTrendData(dogs);
		return trend;
	}

}
