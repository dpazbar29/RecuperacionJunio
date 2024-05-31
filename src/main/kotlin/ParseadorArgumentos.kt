@Suppress("ktlint:standard:max-line-length")
class ParseadorArgumentos {
    fun parsearArgumentos(args: Array<String>): Array<String> {
        var resultado: Array<String> = arrayOf()
        when (args[0]) {
            "-g" -> resultado = comandoG(args)
            "-p" -> resultado = comandoP(args)
            "-t" -> resultado = comandoT(args)
            "-e" -> resultado = comandoE(args)
            "-l" -> resultado = comandoL(args)
            "-c" -> resultado = comandoC(args)
            "-f" -> resultado = comandoF(args)
            "-i" -> resultado = comandoI(args)
            else -> {
                throw IllegalArgumentException("Comando desconocido: ${args[0]}")
            }
        }
        return resultado
    }

    private fun comandoI(args: Array<String>): Array<String> {
        if (args.size != 1) {
            throw IllegalArgumentException("No hay suficientes argumentos para el comando 'i'. No se esperaban más argumentos.")
        }
        return arrayOf(args[0])
    }

    private fun comandoF(args: Array<String>): Array<String> {
        if (args.size != 2) {
            throw IllegalArgumentException(
                "No hay suficientes argumentos para el comando 'f'. Se esperaba 1 argumento (ruta del fichero[./ficheros/fichero.txt]).",
            )
        }
        return arrayOf(args[0], args[1])
    }

    private fun comandoC(args: Array<String>): Array<String> {
        if (args.size != 2) {
            throw IllegalArgumentException(
                "No hay suficientes argumentos para el comando 'c'. Se esperaba 1 argumento (ctfID).",
            )
        }
        return arrayOf(args[0], args[1])
    }

    private fun comandoL(args: Array<String>): Array<String> {
        if (args.size != 2) {
            throw IllegalArgumentException(
                "No hay suficientes argumentos para el comando 'l'. Se esperaba 1 argumento (grupoID).",
            )
        }
        return arrayOf(args[0], args[1])
    }

    private fun comandoE(args: Array<String>): Array<String> {
        if (args.size != 3) {
            throw IllegalArgumentException(
                "No hay suficientes argumentos para el comando 'e'. Se esperaban 2 argumentos (ctfID, grupoID).",
            )
        }
        return arrayOf(args[0], args[1], args[2])
    }

    private fun comandoG(args: Array<String>): Array<String> {
        if (args.size != 3) {
            throw IllegalArgumentException(
                "No hay suficientes argumentos para el comando 'g'. Se esperaban 2 argumentos (grupoID, descripción).",
            )
        }
        return arrayOf(args[0], args[1], args[2])
    }

    private fun comandoP(args: Array<String>): Array<String> {
        if (args.size != 4) {
            throw IllegalArgumentException(
                "No hay suficientes argumentos para el comando 'p'. Se esperaban 3 argumentos (ctfID, grupoID, puntuación).",
            )
        }
        return arrayOf(args[0], args[1], args[2], args[3])
    }

    private fun comandoT(args: Array<String>): Array<String> {
        if (args.size != 2) {
            throw IllegalArgumentException("No hay suficientes argumentos para el comando 't'. Se esperaba 1 argumento (grupoID).")
        }
        return arrayOf(args[0], args[1])
    }
}
