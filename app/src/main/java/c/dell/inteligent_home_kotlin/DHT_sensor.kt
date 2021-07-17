package c.dell.inteligent_home_kotlin

/**
 * Dht class for create objects contains temp and humidity
 * @property humi humidity in float
 * @property temp temperature indoor
 * @property date actual date in string
 * @constructor default temp humidity and date state
 */
class DHT_sensor (var humi: Float, var temp: Float, var date: String){
    constructor() : this(0.0F,0.0F,"")
}

