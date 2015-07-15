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
		trends.add(getMostPopularNames(dogs));
		trends.add(getMostPopularBreeds(dogs));

		trendDao.updateTrends(trends);

	}

	public List<Trend> getAllTrends() {
		return trendDao.getAllTrends();
	}

	private Trend getMostPopularNames(Set<Dog> dogs) {
		Trend nameTrends = new Trend(TrendType.MOST_POPULAR_NAME);
		nameTrends.createTrendData(dogs);
		return nameTrends;
	}

	private Trend getMostPopularBreeds(Set<Dog> dogs) {
		Trend breedTrends = new Trend(TrendType.MOST_POPULAR_BREED);
		breedTrends.createTrendData(dogs);
		return breedTrends;
	}

}
