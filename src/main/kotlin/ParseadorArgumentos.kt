@Suppress("ktlint:standard:max-line-length")
class ParseadorArgumentos(private val salida: Salida) {
    fun parsearArgumentos(args: Array<String>): Array<String> {
        require(args.isNotEmpty()) { "No se han proporcionado argumentos" }

        val comando = args[0]
        val parametros = args.drop(1).toTypedArray()

        val comandos: Array<String> =
            when (comando) {
                "-g" -> verificarNumeroParametros(comando, parametros, 1)
                "-p" ->
                    verificarNumeroParametros(comando, parametros, 3) {
                        verificarEsEntero(parametros[0], "grupoID")
                        verificarEsEntero(parametros[1], "ctfID")
                    }
                "-t", "-l", "-c" -> verificarNumeroParametros(comando, parametros, 1) { verificarEsEntero(parametros[0], "grupoID") }
                "-e" ->
                    verificarNumeroParametros(comando, parametros, 2) {
                        verificarEsEntero(parametros[0], "grupoID")
                        verificarEsEntero(parametros[1], "ctfID")
                    }
                "-f" -> verificarNumeroParametros(comando, parametros, 1)
                "-i" -> verificarNumeroParametros(comando, parametros, 0)
                else -> throw IllegalArgumentException("Comando desconocido: $comando")
            }

        return arrayOf(comando, *parametros)
    }

    private fun verificarNumeroParametros(
        comando: String,
        parametros: Array<String>,
        numEsperado: Int,
        validacionAdicional: (() -> Unit)? = null,
    ): Array<String> {
        if (parametros.size != numEsperado) {
            val salida = salida.mensajeErrorNumeroParametros()
            throw IllegalArgumentException(salida)
        }
        validacionAdicional?.invoke()
        return arrayOf(comando, *parametros)
    }

    private fun verificarEsEntero(
        valor: String,
        id: String,
    ) {
        try {
            valor.toInt()
        } catch (e: NumberFormatException) {
            val salida = salida.mensajeErrorTipo(id)
            throw NumberFormatException(salida)
        }
    }
}
