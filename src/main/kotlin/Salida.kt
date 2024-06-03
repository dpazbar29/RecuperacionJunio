interface Salida {
    fun mensajeError(): String {
        return ("ERROR: El programa no ha recibido parámetros o estos no son adecuados.")
    }

    fun mensajeErrorNumeroParametros(): String {
        return ("ERROR: El número de parámetros no es adecuado.")
    }

    fun mensajeErrorTipo(id: String): String {
        return ("ERROR: El parámetro <$id> debe ser un valor numérico de tipo entero.")
    }

    fun mensajeCreacionGrupo(grupo: String): String {
        return ("Procesado: Añadido el grupo '$grupo'.")
    }

    private fun mensajeCreacionParticipacion(
        grupo: String,
        idCtf: Int,
        puntuacion: Int,
    ): String {
        return ("Procesado: Añadida participación del grupo '$grupo' en el CTF $idCtf con una puntuación de $puntuacion puntos.")
    }

    private fun mensajeEliminacionParticipacion(
        grupo: String,
        idCtf: Int,
    ): String {
        return ("Procesado: Eliminada participación del grupo '$grupo' en el CTF $idCtf.")
    }

    fun mensajeEliminacionGrupo(grupo: String): String {
        return ("Procesado: Eliminada el grupo '$grupo' y su participación en los CTFs")
    }
}
