# weather-app Architecture
Weather app is based on clean architecture pattern.
The project is based two principals modules :
- app module : Contains the presentation layer using android jetpack (architecture and  navigation)
                This module use the module weather-api as library to fetch data (remote or local ) and map it to be easily used by the app.
So the app mobile is only focus on the UI management.
- weather-api module : Contains the domain and data layers
- Domain layer contains the usecases and repositories
- Data layer contain the implementation of repositories exposed by the usecase to the viewmodel of the presentation layer
- the repository implementation on data layer provides two kind of data source : remote data using Retrofit and local data using Room database

# weather-app Fonctionnalities
- Display stored cities
- Add city on local databaseopenweather api -> getweather by city name
- Display weather of a city : current, hourly and daily weather information
- The application is functional in offline mode    
