import java.io.File
import java.io.FileNotFoundException

class ProcesadorFicheros(private val salida: Salida) {
    fun procesarFichero(rutaFichero: String): MutableList<List<String>> {
        val argumentos: MutableList<List<String>> = mutableListOf()
        val fichero = File(rutaFichero)

        if (!fichero.exists()) {
            val salida = salida.mensajeFicheroInexistente(rutaFichero)
            throw FileNotFoundException(salida)
        }

        val lines = fichero.readLines()
        var comandoActual: String = ""

        lines.forEach { line ->
            val lineaRecortada = line.trim()

            if (lineaRecortada.startsWith("#") || lineaRecortada.isEmpty()) {
                return@forEach
            }

            if (lineaRecortada.startsWith("-")) {
                comandoActual = lineaRecortada
                return@forEach
            }

            if (comandoActual != null) {
                val argumentosLinea = mutableListOf(comandoActual)
                argumentosLinea.addAll(lineaRecortada.split(";"))
                argumentos.add(argumentosLinea)
            }
        }

        return argumentos
    }
}
