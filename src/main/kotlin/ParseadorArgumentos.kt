@Suppress("ktlint:standard:max-line-length")

/**
 * Clase que realiza el parseo de argumentos
 */
class ParseadorArgumentos : Salida {

    /**
     * Método que aplica las funciones pertinentes según el comando dado
     *
     * @param args: Argumentos que recibe el programa
     * @return Array<String>: Array con los comandos parseados
     */
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
                "-t", "-l", "-c" -> verificarNumeroParametros(comando, parametros, 1) {
                    verificarEsEntero(parametros[0], "grupoID")
                }
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

    /**
     * Método privado que verifica que el número de parámetros obtenidos es el correcto
     *
     * @param comando: Comando a verificar
     * @param parametros: Valores que acompañan a dicho comando
     * @param numEsperado: Número de parámetros esperados
     * @param validacionAdicional: Una función opcional para validación adicional(en este caso, la verificación de enteros)
     */
    private fun verificarNumeroParametros(
        comando: String,
        parametros: Array<String>,
        numEsperado: Int,
        validacionAdicional: (() -> Unit)? = null,
    ): Array<String> {
        if (parametros.size != numEsperado) {
            val salida = mensajeErrorNumeroParametros()
            throw IllegalArgumentException(salida)
        }
        validacionAdicional?.invoke()
        return arrayOf(comando, *parametros)
    }

    /**
     * Método privado que comprueba si un valor es un entero
     *
     * @param valor: Valor a verificar si es entero
     * @param id: Id del comando a verificar
     */
    private fun verificarEsEntero(
        valor: String,
        id: String,
    ) {
        try {
            valor.toInt()
        } catch (e: NumberFormatException) {
            val salida = mensajeErrorTipo(id)
            throw NumberFormatException(salida)
        }
    }
}
