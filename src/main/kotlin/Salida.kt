interface Salida {
    fun mensajeError() {
        println("ERROR: El programa no ha recibido parámetros o estos no son adecuados.")
    }

    fun mensajeErrorNumeroParametros(): String {
        return ("ERROR: El número de parámetros no es adecuado.")
    }

    fun mensajeErrorTipo(id: String) {
        println("ERROR: El parámetro <$id> debe ser un valor numérico de tipo entero.")
    }

    fun mensajeCreacionGrupo(grupo: String) {
        println("Procesado: Añadido el grupo '$grupo'.")
    }
}