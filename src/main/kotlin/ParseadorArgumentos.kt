@Suppress("ktlint:standard:max-line-length")
class ParseadorArgumentos: Salida {
    fun parsearArgumentos(args: Array<String>): Array<String> {
        val comandos: Array<String> = when (args[0]) {
            "-g" -> comandoG(args)
            "-p" -> comandoP(args)
            "-t" -> comandoT(args)
            "-e" -> comandoE(args)
            "-l" -> comandoL(args)
            "-c" -> comandoC(args)
            "-f" -> comandoF(args)
            "-i" -> comandoI(args)
            else -> {
                throw IllegalArgumentException("Comando desconocido: ${args[0]}")
            }
        }
        return comandos
    }

    private fun comandoI(args: Array<String>): Array<String> {
        if (args.size != 1) {
            val salida = mensajeErrorNumeroParametros()
            throw IllegalArgumentException(salida)
        }
        return arrayOf(args[0])
    }

    private fun comandoF(args: Array<String>): Array<String> {
        if (args.size != 2) {
            val salida = mensajeErrorNumeroParametros()
            throw IllegalArgumentException(salida)
        }
        return arrayOf(args[0], args[1])
    }

    private fun comandoC(args: Array<String>): Array<String> {
        if (args.size != 2) {
            val salida = mensajeErrorNumeroParametros()
            throw IllegalArgumentException(salida)
        }
        try {
            args[1].toInt()
        }catch (e:NumberFormatException) {
            val id = "grupoID"
            val salida = mensajeErrorTipo(id)
            throw NumberFormatException(salida)
        }
        return arrayOf(args[0], args[1])
    }

    private fun comandoL(args: Array<String>): Array<String> {
        if (args.size != 2) {
            val salida = mensajeErrorNumeroParametros()
            throw IllegalArgumentException(salida)
        }
        try {
            args[1].toInt()
        }catch (e:NumberFormatException) {
            val id = "grupoID"
            val salida = mensajeErrorTipo(id)
            throw NumberFormatException(salida)
        }
        return arrayOf(args[0], args[1])
    }

    private fun comandoE(args: Array<String>): Array<String> {
        if (args.size != 3) {
            val salida = mensajeErrorNumeroParametros()
            throw IllegalArgumentException(salida)
        }
        try {
            args[1].toInt()
            args[2].toInt()
        }catch (e: NumberFormatException) {
            val id = "grupoID y ctfID"
            val salida = mensajeErrorTipo(id)
            throw NumberFormatException(salida)
        }
        return arrayOf(args[0], args[1], args[2])
    }

    private fun comandoG(args: Array<String>): Array<String> {
        if (args.size != 3) {
            val salida = mensajeErrorNumeroParametros()
            throw IllegalArgumentException(salida)
        }
        try {
            args[1].toInt()
        }catch (e: NumberFormatException) {
            val id = "grupoID"
            val salida = mensajeErrorTipo(id)
            throw NumberFormatException(salida)
        }
        return arrayOf(args[0], args[1], args[2])
    }

    private fun comandoP(args: Array<String>): Array<String> {
        if (args.size != 4) {
            val salida = mensajeErrorNumeroParametros()
            throw IllegalArgumentException(salida)
        }
        try {
            args[1].toInt()
            args[2].toInt()
        }catch (e: NumberFormatException) {
            val id = "grupoID y ctfID"
            val salida = mensajeErrorTipo(id)
            throw NumberFormatException(salida)
        }
        return arrayOf(args[0], args[1], args[2], args[3])
    }

    private fun comandoT(args: Array<String>): Array<String> {
        if (args.size != 2) {
            val salida = mensajeErrorNumeroParametros()
            throw IllegalArgumentException(salida)
        }
        try {
            args[1].toInt()
        }catch (e: NumberFormatException){
            val id = "grupoID"
            val salida = mensajeErrorTipo(id)
            throw NumberFormatException(salida)
        }
        return arrayOf(args[0], args[1])
    }
}
