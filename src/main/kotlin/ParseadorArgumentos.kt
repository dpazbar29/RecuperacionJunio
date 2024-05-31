@Suppress("ktlint:standard:max-line-length")
class ParseadorArgumentos {
    fun parsearArgumentos(args: Array<String>): Array<String> {
        var resultado: Array<String> = arrayOf()
        when (args[0]) {
            "g" -> {
                if (args.size != 3) {
                    throw IllegalArgumentException(
                        "No hay suficientes argumentos para el comando 'g'. Se esperaban 2 argumentos (grupoID, descripción).",
                    )
                }
                resultado = arrayOf(args[1], args[2])
            }
            "p" -> {
                if (args.size != 4) {
                    throw IllegalArgumentException(
                        "No hay suficientes argumentos para el comando 'p'. Se esperaban 3 argumentos (ctfID, grupoID, puntuación).",
                    )
                }
                resultado = arrayOf(args[1], args[2], args[3])
            }
            "t" -> {
                if (args.size != 2) {
                    throw IllegalArgumentException("No hay suficientes argumentos para el comando 't'. Se esperaba 1 argumento (grupoID).")
                }
                resultado = arrayOf(args[1])
            }
            "e" -> {
                if (args.size != 3) {
                    throw IllegalArgumentException(
                        "No hay suficientes argumentos para el comando 'e'. Se esperaban 2 argumentos (ctfID, grupoID).",
                    )
                }
                resultado = arrayOf(args[1], args[2])
            }
            "p" -> {
                if (args.size != 4) {
                    throw IllegalArgumentException(
                        "No hay suficientes argumentos para el comando 'p'. Se esperaban 3 argumentos (ctfID, grupoID, puntuación).",
                    )
                }
                resultado = arrayOf(args[1], args[2], args[3])
            }
            else -> {
                throw IllegalArgumentException("Comando desconocido: ${args[0]}")
            }
        }
        return resultado
    }
}
