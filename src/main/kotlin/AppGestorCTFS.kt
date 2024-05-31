import dao.CtfDAOH2
import dao.GrupoDAOH2
import dao.entity.CtfEntity
import dao.entity.GrupoEntity
import services.CtfService
import services.CtfServiceImpl
import services.GrupoService
import services.GrupoServiceImpl
import java.lang.Exception
import javax.sql.DataSource

class AppGestorCTFS {
    private fun mensajeError() {
        println("ERROR: El programa no ha recibido parámetros o estos no son adecuados.")
    }

    private fun mensajeErrorNumeroParametros() {
        println("ERROR: El número de parámetros no es adecuado.")
    }

    private fun mensajeErrorTipo() {
        println("ERROR: El parámetro <grupoid> debe ser un valor numérico de tipo entero.")
    }

    private fun mensajeCreacionGrupo(grupo: String) {
        println("Procesado: Añadido el grupo '$grupo'.")
    }

    private fun mensajeCreacionParticipacion(
        grupo: String,
        idCtf: Int,
        puntuacion: Int,
    ) {
        println("Procesado: Añadida participación del grupo '$grupo' en el CTF $idCtf con una puntuación de $puntuacion puntos.")
    }

    private fun mensajeEliminacionParticipacion(
        grupo: String,
        idCtf: Int,
    ) {
        println("Procesado: Eliminada participación del grupo '$grupo' en el CTF $idCtf.")
    }

    private fun mensajeEliminacionGrupo(grupo: String) {
        println("Procesado: Eliminada el grupo '$grupo' y su participación en los CTFs")
    }

    private fun mostrarTodosLosGrupos(
        grupoDAO: GrupoDAOH2,
        ctfDAO: CtfDAOH2,
    ) {
        val grupos = grupoDAO.obtenerTodo()
        for (grupo in grupos) {
            val ctfs: List<CtfEntity> = ctfDAO.obtenerPorIDGrupo(grupo.grupoID)
            println("Procesando: Listado participación del grupo '${grupo.grupoDesc}'")
            println("GRUPO: ${grupo.grupoID}   ${grupo.grupoDesc}    CTF CON MEJOR POSICIÓN: ${grupo.mejorPosCtfID}")
            println()
            println("  CTF  | PUNTUACIÓN | POSICIÓN ")
            println("-------------------------------")
            for (ctf in ctfs) {
                val posicion: Int? = ctfDAO.obtenerPosicionGrupoEnCtf(grupo.grupoID, ctf.ctfID)
                println("   ${ctf.ctfID}   |    ${ctf.puntuacion}     |    $posicion      ")
            }
            println()
            println()
        }
    }

    private fun mostrarDatosGruposConsola(
        args: Array<String>,
        grupoDAO: GrupoDAOH2,
        ctfDAO: CtfDAOH2,
    ) {
        val grupo: GrupoEntity? = grupoDAO.obtenerPorID(args[1].toInt())
        if (grupo == null) {
            mostrarTodosLosGrupos(grupoDAO, ctfDAO)
        } else {
            val ctfs: List<CtfEntity> = ctfDAO.obtenerPorIDGrupo(args[1].toInt())
            println("Procesando: Listado participación del grupo '${grupo?.grupoDesc}'")
            println("GRUPO: ${grupo?.grupoID}   ${grupo?.grupoDesc}    CTF CON MEJOR POSICIÓN: ${grupo?.mejorPosCtfID}")
            println()
            println("  CTF  | PUNTUACIÓN | POSICIÓN ")
            println("-------------------------------")
            for (ctf in ctfs) {
                val posicion: Int? = ctfDAO.obtenerPosicionGrupoEnCtf(args[1].toInt(), ctf.ctfID)
                println("   ${ctf.ctfID}   |    ${ctf.puntuacion}     |    $posicion      ")
            }
        }
    }

    private fun mostrarDatosCTFConsola(
        args: Array<String>,
        grupoDAO: GrupoDAOH2,
        ctfDAO: CtfDAOH2,
    ) {
        val grupos: List<GrupoEntity> = grupoDAO.obtenerTodo()
        val ctf: CtfEntity? = ctfDAO.obtenerPorIDCtf(args[1].toInt())

        println("Procesando: Listado participación en el CTF '${ctf?.ctfID}' ")
        println()
        println("GRUPO    | PUNTUACIÓN ")
        println("----------------------")

        val puntuaciones = mutableListOf<Pair<String, Int>>()

        for (grupo in grupos) {
            var puntuacion = 0
            if (ctf != null) {
                puntuacion = ctfDAO.obtenerPuntuacionPorIDGrupoIDCtf(grupo.grupoID, ctf.ctfID)
            }
            puntuaciones.add(Pair(grupo.grupoDesc, puntuacion))
        }

        val puntuacionesOrdenadas = puntuaciones.sortedByDescending { it.second }

        for ((grupoDesc, puntuacion) in puntuacionesOrdenadas) {
            println("$grupoDesc  | $puntuacion")
        }
    }

    private fun funcionG(
        grupoService: GrupoService,
        args: Array<String>,
        ctfDao: CtfDAOH2,
    ) {
        try {
            val posicion = null
            val grupo = GrupoEntity(grupoID = args[1].toInt(), grupoDesc = args[2], mejorPosCtfID = posicion)
            grupoService.crear(grupo)
            mensajeCreacionGrupo(args[2])
        } catch (e: Exception) {
            if (args.size < 3 || args.size > 3) {
                mensajeErrorNumeroParametros()
            } else if (args[1].toInt() is Int) {
                mensajeError()
            } else {
                mensajeErrorTipo()
            }
        }
    }

    private fun funcionP(
        args: Array<String>,
        ctfService: CtfService,
        grupoService: GrupoService,
        ctfDAO: CtfDAOH2,
    ) {
        try {
            val participacionGrupo = CtfEntity(ctfID = args[1].toInt(), grupoID = args[2].toInt(), puntuacion = args[3].toInt())
            val grupo = ctfService.obtenerPorIDGrupoIDCtf(args[2].toInt(), args[1].toInt())
            /*
            if (grupo != null) {
                val grupoActualizado = CtfEntity(grupo.ctfID, grupo.grupoID, args[3].toInt())
                ctfService.actualizar(grupoActualizado)
            } else {
                ctfService.crear(participacionGrupo)
            }
             */
            ctfService.crear(participacionGrupo)
            val posicion = grupoService.obtenerMejorPosCTFIdParaGrupo(args[2].toInt(), ctfDAO)
            val datosGrupo = grupoService.obtenerPorID(args[2].toInt())
            if (datosGrupo != null) {
                val grupoActualizacion = GrupoEntity(args[2].toInt(), datosGrupo.grupoDesc, posicion)
                grupoService.actualizar(grupoActualizacion)
            }

            mensajeCreacionParticipacion(datosGrupo?.grupoDesc ?: "Desconocido", args[2].toInt(), args[3].toInt())
        } catch (e: Exception) {
            if (args.size != 4) {
                mensajeErrorNumeroParametros()
            } else {
                mensajeError()
            }
        }
    }

    private fun funcionT(
        args: Array<String>,
        grupoService: GrupoService,
    ) {
        try {
            val grupo = grupoService.obtenerPorID(args[1].toInt())
            if (grupo != null) {
                mensajeEliminacionGrupo(grupo.grupoDesc)
            }
            grupoService.borrar(args[1].toInt())
        } catch (e: Exception) {
            mensajeError()
        }
    }

    private fun funcionE(
        args: Array<String>,
        ctfService: CtfService,
        grupoService: GrupoService,
        ctfDao: CtfDAOH2,
    ) {
        try {
            ctfService.borrar(args[1].toInt(), args[2].toInt())
            val grupo = grupoService.obtenerPorID(args[2].toInt())
            val posicion = grupoService.obtenerMejorPosCTFIdParaGrupo(args[2].toInt(), ctfDao)
            val datosGrupo = grupoService.obtenerPorID(args[2].toInt())
            val grupoActualizacion = GrupoEntity(args[2].toInt(), datosGrupo?.grupoDesc.toString(), posicion)
            grupoService.actualizar(grupoActualizacion)
            if (grupo != null) {
                mensajeEliminacionParticipacion(grupo.grupoDesc, args[2].toInt())
            }
        } catch (e: Exception) {
            mensajeError()
        }
    }

    private fun funcionL(
        args: Array<String>,
        grupoDao: GrupoDAOH2,
        ctfDao: CtfDAOH2,
    ) {
        try {
            mostrarDatosGruposConsola(args, grupoDao, ctfDao)
        } catch (e: Exception) {
            mensajeError()
        }
    }

    private fun funcionC(
        args: Array<String>,
        grupoDao: GrupoDAOH2,
        ctfDao: CtfDAOH2,
    ) {
        try {
            mostrarDatosCTFConsola(args, grupoDao, ctfDao)
        } catch (e: Exception) {
            mensajeError()
        }
    }

    private fun funcionF(
        args: Array<String>,
        dataSource: DataSource,
    ) {
        try {
            val procesadorFicheros = ProcesadorFicheros(dataSource)
            procesadorFicheros.procesarFichero(dataSource, args[1])
        } catch (e: Exception) {
            println("Ocurrió un error: ${e.message}")
            mensajeError()
        }
    }

    private fun funcionI(
        dataSource: DataSource,
        args: Array<String>,
    ) {
        try {
            val interfarGrafica = InterfazGrafica()
            interfarGrafica.start(dataSource)
        } catch (e: Exception) {
            mensajeError()
        }
    }

    fun menu(
        dataSource: DataSource,
        args: Array<String>,
    ) {
        val grupoDao = GrupoDAOH2(dataSource)
        val ctfDao = CtfDAOH2(dataSource)

        val grupoService = GrupoServiceImpl(grupoDao)
        val ctfService = CtfServiceImpl(ctfDao)

        if (args.isEmpty()) {
            mensajeError()
        }

        val comando = args[0]

        when (comando) {
            "-g" -> funcionG(grupoService, args, ctfDao)
            "-p" -> funcionP(args, ctfService, grupoService, ctfDao)
            "-t" -> funcionT(args, grupoService)
            "-e" -> funcionE(args, ctfService, grupoService, ctfDao)
            "-l" -> funcionL(args, grupoDao, ctfDao)
            "-c" -> funcionC(args, grupoDao, ctfDao)
            "-f" -> funcionF(args, dataSource)
            "-i" -> funcionI(dataSource, args)
        }
    }
}
