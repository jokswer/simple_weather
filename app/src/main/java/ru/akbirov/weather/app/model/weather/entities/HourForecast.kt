package ru.akbirov.weather.app.model.weather.entities

data class HourForecast(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<ListItem>,
    val city: City
)

data class ListItem(
    val dt: Int,
    val main: ListItemMain,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: ListItemWind,
    val visibility: Int,
    val pop: Number,
    val sys: ListItemSys,
    val dt_txt: String,
)

data class ListItemMain(
    val temp: Number,
    val feels_like: Number,
    val temp_min: Number,
    val temp_max: Number,
    val pressure: Int,
    val sea_level: Int,
    val grnd_level: Int,
    val humidity: Int,
    val temp_kf: Float
)

data class ListItemWind(
    val speed: Number,
    val deg: Int,
    val gust: Number
)

data class ListItemSys(
    val pod: String
)

data class City(
    val id: Int,
    val name: String,
    val coord: Coord,
    val country: String,
    val population: Int,
    val timezone: Int,
    val sunrise: Int,
    val sunset: Int
)