import java.io.File
import javax.sql.DataSource

class ProcesadorFicheros(dataSource: DataSource) {
    fun procesarFichero(
        dataSource: DataSource,
        rutaFichero: String,
    ) {
        val fichero = File(rutaFichero)
        val lines = fichero.readLines()

        var comandoActual: String? = null

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
                val argumentos: List<String> = lineaRecortada.split(";").toMutableList()
                argumentos.addFirst(comandoActual)
                val consola = AppGestorCTFS()
                consola.menu(dataSource, argumentos.toTypedArray())
            }
        }
    }
}
