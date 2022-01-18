package ua.com.foxminded.collectionsandmaps;

import dagger.Component;

@Component()
public interface CalcUtilsComponent {

    CalcUtilsImpl.CollectionsCalcUtils getCollectionsCalcUtils();

    CalcUtilsImpl.MapsCalcUtils getMapsCalcUtils();

}