package fr.alamary.weatherapi.data.mappers

import fr.alamary.weatherapi.data.models.GetWeatherByCityNameResponse
import fr.alamary.weatherapi.domain.entities.CityEntity

class CityMapper : BaseDataMapper<GetWeatherByCityNameResponse, CityEntity> {

    override fun mapToEntity(type: GetWeatherByCityNameResponse): CityEntity {
        return with(type){
            CityEntity(lat = coord.lat,
            lon= coord.lon,
            name= name,
            timezone = timezone.toString(),
            countryCode = sys.country)
        }
    }
}