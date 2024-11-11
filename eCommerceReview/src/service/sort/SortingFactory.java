package service.sort;

import model.SORTTYPE;
import service.SortingStrategy;

public class SortingFactory {

    private static SortingFactory sortingFactory;
    private SortingFactory() {

    }
    public static synchronized SortingFactory  getInstance(){
        if(sortingFactory == null){
            sortingFactory = new SortingFactory();
        }
        return sortingFactory;
    }

    public SortingStrategy getSortingStrategy(SORTTYPE sortType) {
        switch (sortType) {
            case SORTTYPE.DATE:
                return new DateSorting();
            case SORTTYPE.RATING:
                return new RateSorting();
            default:
                throw new IllegalArgumentException("Invalid sort criteria: ");
        }
    }
}
