package fr.alamary.weatherapi.data.mappers

import fr.alamary.weatherapi.data.models.GlobalWeatherInformations
import fr.alamary.weatherapi.domain.entities.WeatherEntity

class WeatherMapper : BaseDataMapper<GlobalWeatherInformations, WeatherEntity> {

    override fun mapToEntity(type: GlobalWeatherInformations): WeatherEntity {
        return with(type) {
            WeatherEntity(
                dt = current!!.dt,
                sunrise = current.sunrise,
                sunset = current.sunset,
                temp = current.temp,
                feelsLike = current.feelsLike,
                pressure = current.pressure,
                humidity = current.humidity,
                dewPoint = current.dewPoint,
                uvi = current.uvi,
                clouds = current.clouds,
                visibility = current.visibility,
                windSpeed = current.windSpeed,
                windDeg = current.windDeg,
                id = current.weather[0].id,
                main = current.weather[0].main,
                description = current.weather[0].description,
                icon = current.weather[0].icon,
                )
        }
    }
}