package fr.alamary.weatherapi.data.mappers

/**
 * Interface that provides abstract method to be implemented for every
 * Aach DataMapper class should implement this Interface
 */
interface BaseDataMapper<E,M> {

    //method to map to data entity object from an  data model object
    fun mapToEntity(type: E): M

    //method to map to data object from an  domain entity object
     //fun mapFromEntity(type: M): E


}