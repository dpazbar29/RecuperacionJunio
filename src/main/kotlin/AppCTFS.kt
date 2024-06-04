import dao.entity.CtfEntity
import dao.entity.GrupoEntity
import services.CtfService
import services.GrupoService
import javax.sql.DataSource

interface AppCTFS : Salida {
    fun anadirGrupo(
        comandos: Array<String>,
        grupoService: GrupoService,
    ) {
        val grupoID = comandos[1].toInt()
        val grupoDesc = comandos[2]
        val posicion = null

        val grupo = GrupoEntity(grupoID, grupoDesc, posicion)
        grupoService.crear(grupo)
        mensajeCreacionGrupo(comandos[2])
    }

    fun anadirParticipacion(
        comandos: Array<String>,
        grupoService: GrupoService,
        ctfService: CtfService,
    ) {
        val ctfID = comandos[1].toInt()
        val grupoID = comandos[2].toInt()
        val puntuacion = comandos[3].toInt()

        val participacionGrupo = CtfEntity(ctfID, grupoID, puntuacion)
        val grupo = ctfService.obtenerPorIDGrupoIDCtf(grupoID, ctfID)

        // Parte posiblemente eliminable
        if (grupo != null) {
            val grupoActualizado = CtfEntity(grupo.ctfID, grupo.grupoID, puntuacion)
            ctfService.actualizar(grupoActualizado)
        } else {
            ctfService.crear(participacionGrupo)
        }
        // Hasta aquí

        // ctfService.crear(participacionGrupo)
        val posicion = grupoService.obtenerMejorPosCTFIdParaGrupo(grupoID, ctfService)
        val datosGrupo = grupoService.obtenerPorID(grupoID)

        if (datosGrupo != null) {
            val grupoActualizacion = GrupoEntity(grupoID, datosGrupo.grupoDesc, posicion)
            grupoService.actualizar(grupoActualizacion)
        }

        mensajeCreacionParticipacion(datosGrupo?.grupoDesc ?: "Desconocido", ctfID, puntuacion)
    }

    fun eliminarGrupo(
        comandos: Array<String>,
        grupoService: GrupoService,
    ) {
        val grupoID = comandos[1].toInt()

        val grupo = grupoService.obtenerPorID(grupoID)
        if (grupo != null) {
            mensajeEliminacionGrupo(grupo.grupoDesc)
        }
        grupoService.borrar(grupoID)
    }

    fun eliminarParticipacion(
        comandos: Array<String>,
        grupoService: GrupoService,
        ctfService: CtfService,
    ) {
        val ctfID = comandos[1].toInt()
        val grupoID = comandos[2].toInt()

        ctfService.borrar(ctfID, grupoID)
        val grupo = grupoService.obtenerPorID(grupoID)

        val posicion = grupoService.obtenerMejorPosCTFIdParaGrupo(grupoID, ctfService)
        val datosGrupo = grupoService.obtenerPorID(grupoID)

        val grupoActualizacion = GrupoEntity(grupoID, datosGrupo?.grupoDesc.toString(), posicion)
        grupoService.actualizar(grupoActualizacion)

        if (grupo != null) {
            mensajeEliminacionParticipacion(grupo.grupoDesc, ctfID)
        }
    }

    fun mostrarInformacionGrupos(
        comandos: Array<String>,
        grupoService: GrupoService,
        ctfService: CtfService,
    ) {
        mostrarDatosGruposConsola(comandos, grupoService, ctfService) // DUDA
    }

    private fun mostrarTodosLosGrupos(
        grupoService: GrupoService,
        ctfService: CtfService,
    ) {
        val grupos = grupoService.obtenerTodo()
        for (grupo in grupos) {
            val ctfs: List<CtfEntity> = ctfService.obtenerPorIDGrupo(grupo.grupoID)
            println("Procesando: Listado participación del grupo '${grupo.grupoDesc}'")
            println("GRUPO: ${grupo.grupoID}   ${grupo.grupoDesc}    CTF CON MEJOR POSICIÓN: ${grupo.mejorPosCtfID}")
            println()
            println("  CTF  | PUNTUACIÓN | POSICIÓN ")
            println("-------------------------------")
            for (ctf in ctfs) {
                val posicion: Int? = ctfService.obtenerPosicionGrupoEnCtf(grupo.grupoID, ctf.ctfID)
                println("   ${ctf.ctfID}   |    ${ctf.puntuacion}     |    $posicion      ")
            }
            println()
            println()
        }
    }

    private fun mostrarDatosGruposConsola(
        comandos: Array<String>,
        grupoService: GrupoService,
        ctfService: CtfService,
    ) {
        val grupoID = comandos[1].toInt()
        val grupo: GrupoEntity? = grupoService.obtenerPorID(grupoID)
        if (grupo == null) {
            mostrarTodosLosGrupos(grupoService, ctfService)
        } else {
            val ctfs: List<CtfEntity> = ctfService.obtenerPorIDGrupo(grupoID)
            println("Procesando: Listado participación del grupo '${grupo?.grupoDesc}'")
            println("GRUPO: ${grupo?.grupoID}   ${grupo?.grupoDesc}    CTF CON MEJOR POSICIÓN: ${grupo?.mejorPosCtfID}")
            println()
            println("  CTF  | PUNTUACIÓN | POSICIÓN ")
            println("-------------------------------")
            for (ctf in ctfs) {
                val posicion: Int? = ctfService.obtenerPosicionGrupoEnCtf(grupoID, ctf.ctfID)
                println("   ${ctf.ctfID}   |    ${ctf.puntuacion}     |    $posicion      ")
            }
        }
    }

    fun mostrarParticipacionGrupo(
        comandos: Array<String>,
        grupoService: GrupoService,
        ctfService: CtfService,
    ) {
        mostrarDatosCTFConsola(comandos, grupoService, ctfService) // DUDA
    }

    private fun mostrarDatosCTFConsola(
        comandos: Array<String>,
        grupoService: GrupoService,
        ctfService: CtfService,
    ) {
        val ctfID = comandos[1].toInt()

        val grupos: List<GrupoEntity> = grupoService.obtenerTodo()
        val ctf: CtfEntity? = ctfService.obtenerPorIDCtf(ctfID)

        println("Procesando: Listado participación en el CTF '${ctf?.ctfID}' ")
        println()
        println("GRUPO    | PUNTUACIÓN ")
        println("----------------------")

        val puntuaciones = mutableListOf<Pair<String, Int>>()

        for (grupo in grupos) {
            var puntuacion = 0
            if (ctf != null) {
                puntuacion = ctfService.obtenerPuntuacionPorIDGrupoIDCtf(grupo.grupoID, ctf.ctfID)
            }
            puntuaciones.add(Pair(grupo.grupoDesc, puntuacion))
        }

        val puntuacionesOrdenadas = puntuaciones.sortedByDescending { it.second }

        for ((grupoDesc, puntuacion) in puntuacionesOrdenadas) {
            if (puntuacion != 0) {
                println("$grupoDesc  | $puntuacion")
            }
        }
    }

    fun procesamientoPorLotes(
        comandos: Array<String>,
        dataSource: DataSource,
    ) {
        val procesador = ProcesadorFicheros()
        val rutaFichero = comandos[1]
        val nuevosComandos = procesador.procesarFichero(rutaFichero)
        println(nuevosComandos)
    }

    fun interfazGrafica(dataSource: DataSource) {
        val interfazGrafica = InterfazGrafica()
        interfazGrafica.start(dataSource)
    }
}
