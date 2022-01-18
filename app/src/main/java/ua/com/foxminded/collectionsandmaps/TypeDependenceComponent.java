package ua.com.foxminded.collectionsandmaps;

import dagger.Component;

@Component()
public interface TypeDependenceComponent {

    CollectionsTypeDependence getCollectionsTypeDependence();

    MapsTypeDependence getMapsTypeDependence();

}