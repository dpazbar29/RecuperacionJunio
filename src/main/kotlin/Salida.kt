class Salida()
{
    fun mensajeError(): String {
        return ("ERROR: El programa no ha recibido parámetros o estos no son adecuados.")
    }

    fun mensajeErrorNumeroParametros(): String {
        return ("ERROR: El número de parámetros no es adecuado.")
    }

    fun mensajeErrorTipo(id: String): String {
        return ("ERROR: El parámetro <$id> debe ser un valor numérico de tipo entero.")
    }

    fun mensajeCreacionGrupo(grupo: String) {
        println("Procesado: Añadido el grupo '$grupo'.")
    }

    fun mensajeGrupoExiste(grupo: String) {
        println("ERROR: Ya existe un grupo con el nombre $grupo")
    }

    fun mensajeCreacionParticipacion(
        grupo: String,
        idCtf: Int,
        puntuacion: Int,
    ) {
        println("Procesado: Añadida participación del grupo '$grupo' en el CTF $idCtf con una puntuación de $puntuacion puntos.")
    }

    fun mensajeEliminacionParticipacion(
        grupo: String,
        idCtf: Int,
    ) {
        println("Procesado: Eliminada participación del grupo '$grupo' en el CTF $idCtf.")
    }

    fun mensajeEliminacionGrupo(grupo: String) {
        println("Procesado: Eliminada el grupo '$grupo' y su participación en los CTFs")
    }

    fun mensajeFicheroInexistente(fichero: String): String {
        return ("El fichero no existe: $fichero")
    }

    fun mensajeCtfInexistente(id: Int) {
        println("ERROR: El CTF con id $id no existe.")
    }

    fun mensajeParticipacionInexistente() {
        println("ERROR: El grupo ya tiene una participación en el CTF.")
    }
}
