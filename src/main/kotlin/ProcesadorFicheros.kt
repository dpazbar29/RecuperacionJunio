import java.io.File
import java.io.FileNotFoundException

/**
 * Clase que procesa los ficheros con datos para la ejecución del programa
 */
class ProcesadorFicheros : Salida {

    /**
     * Método que procesa el fichero dado para extraer los comandos dados en este
     *
     * @param rutaFichero: Ruta del fichero a procesar
     * @return MutableList<List<String>>: Comandos con sus valores detectados en el fichero
     */
    fun procesarFichero(rutaFichero: String): MutableList<List<String>> {
        val argumentos: MutableList<List<String>> = mutableListOf()
        val fichero = File(rutaFichero)

        comprobadorArchivo(fichero, rutaFichero)

        val lineas = fichero.readLines()
        var comandoActual = ""

        lineas.forEach { linea ->
            val lineaRecortada = linea.trim()

            if (lineaRecortada.startsWith("#") || lineaRecortada.isEmpty()) {
                return@forEach
            }

            if (lineaRecortada.startsWith("-")) {
                comandoActual = lineaRecortada
                return@forEach
            }
            selectorComandos(comandoActual, lineaRecortada, argumentos)
        }
        return argumentos
    }

    /**
     * Método privado que comprueba que el fichero existe
     * @param fichero: Variable de tipo Fichero
     * @param rutaFichero: Ruta del fichero a comprobar
     */
    private fun comprobadorArchivo(fichero: File, rutaFichero: String) {
        if (!fichero.exists()) {
            val salida = mensajeFicheroInexistente(rutaFichero)
            throw FileNotFoundException(salida)
        }
    }

    /**
     * Método privado que empaqueta los comandos detectados en el fichero
     *
     * @param comandoActual: Comando que se quiere ejecutar
     * @param lineaRecortada: Línea que contiene valores para un comando
     * @param argumentos: Lista mutable donde se introducirán los comandos y sus valores
     */
    private fun selectorComandos(comandoActual: String, lineaRecortada: String, argumentos: MutableList<List<String>>) {
        val argumentosLinea = mutableListOf(comandoActual)
        argumentosLinea.addAll(lineaRecortada.split(";"))
        argumentos.add(argumentosLinea)
    }
}
