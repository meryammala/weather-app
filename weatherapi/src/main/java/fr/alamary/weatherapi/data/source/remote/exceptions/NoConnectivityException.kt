package fr.alamary.weatherapi.data.source.remote.exceptions

import java.io.IOException

class NoConnectivityException : IOException() {

    override val message: String?
        get() = "No connectivity exception"
}