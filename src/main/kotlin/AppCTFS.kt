import dao.entity.CtfEntity
import dao.entity.GrupoEntity
import services.CtfService
import services.GrupoService
import javax.sql.DataSource

/**
 * Interfaz con la lógica de cada función del programa
 */
interface AppCTFS : Salida {
    /**
     * Método que añade un grupo a la base de datos
     *
     * @param comandos: Comandos introducidos para la creación del grupo
     * @param grupoService: Service de los grupos para llevar a cabo la creación del grupo
     */
    fun anadirGrupo(
        comandos: Array<String>,
        grupoService: GrupoService,
    ) {
        val grupoDesc = comandos[1]
        val posicion = null

        val gruposExistentes = grupoService.obtenerTodo()
        val grupoID =
            if (gruposExistentes.isEmpty()) {
                1
            } else {
                gruposExistentes.maxOf { it.grupoID } + 1
            }

        val grupo = GrupoEntity(grupoID, grupoDesc, posicion)
        grupoService.crear(grupo)
        mensajeCreacionGrupo(grupoDesc)
    }

    /**
     * Método que añade una participación a la base de datos
     *
     * @param comandos: Comandos introducidos para la creación de la participación
     * @param grupoService: Service de los grupos para llevar a cabo la eliminación de la participación del CTF
     * @param ctfService: Service de los CTFS para llevar a cabo la eliminación de la participación del CTF
     */
    fun anadirParticipacion(
        comandos: Array<String>,
        grupoService: GrupoService,
        ctfService: CtfService,
    ) {
        val ctfID = comandos[1].toInt()
        val grupoID = comandos[2].toInt()
        val puntuacion = comandos[3].toInt()

        val participacionesActuales = ctfService.obtenerPorIDGrupoIDCtf(grupoID, ctfID)
        if (participacionesActuales == null) {
            val participacionGrupo = CtfEntity(ctfID, grupoID, puntuacion)
            val grupo = ctfService.obtenerPorIDGrupoIDCtf(grupoID, ctfID)

            if (grupo != null) {
                val grupoActualizado = CtfEntity(grupo.ctfID, grupo.grupoID, puntuacion)
                ctfService.actualizar(grupoActualizado)
            } else {
                ctfService.crear(participacionGrupo)
            }

            val posicion = grupoService.obtenerMejorPosCTFIdParaGrupo(grupoID, ctfService)
            val datosGrupo = grupoService.obtenerPorID(grupoID)

            if (datosGrupo != null) {
                val grupoActualizacion = GrupoEntity(grupoID, datosGrupo.grupoDesc, posicion)
                grupoService.actualizar(grupoActualizacion)
            }

            mensajeCreacionParticipacion(datosGrupo?.grupoDesc ?: "Desconocido", ctfID, puntuacion)
        } else {
            mensajeParticipacionInexistente()
        }
    }

    /**
     * Método que elimina un grupo de la base de datos
     *
     * @param comandos: Comandos necesarios para la eliminación del grupo
     * @param grupoService: Service de los grupos para llevar a cabo la eliminación del grupo
     */
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

    /**
     * Método que elimina una participación de la base de datos
     *
     * @param comandos: Comandos introducidos para la eliminación de la participación
     * @param grupoService: Service de los grupos para llevar a cabo la creación del CTF
     * @param ctfService: Service de los CTFS para llevar a cabo la creación del CTF
     */
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

    /**
     * Método que muestra la participación de los grupos en los CTFS
     *
     * @param comandos: Comandos introducidos para mostrar las participaciones de los grupos
     * @param grupoService: Service de los grupos para llevar a cabo la muestra de información
     * @param ctfService: Service de los CTFS para llevar a cabo la muestra de información
     */
    fun mostrarInformacionGrupos(
        comandos: Array<String>,
        grupoService: GrupoService,
        ctfService: CtfService,
    ) {
        mostrarDatosGruposConsola(comandos, grupoService, ctfService) // DUDA
    }

    /**
     * Método que muestra la información de todos los grupos
     *
     * @param grupoService: Service de los grupos para llevar a cabo la muestra de información
     * @param ctfService: Service de los CTFS para llevar a cabo la muestra de información
     */
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

    /**
     * Método que muestra la información de un grupo o todos los grupos
     *
     * @param comandos: Comandos con la información
     * @param grupoService: Service de los grupos para llevar a cabo la muestra de información
     * @param ctfService: Service de los CTFS para llevar a cabo la muestra de información
     */
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
            println("Procesando: Listado participación del grupo '${grupo.grupoDesc}'")
            println("GRUPO: ${grupo.grupoID}   ${grupo.grupoDesc}    CTF CON MEJOR POSICIÓN: ${grupo.mejorPosCtfID}")
            println()
            println("  CTF  | PUNTUACIÓN | POSICIÓN ")
            println("-------------------------------")
            for (ctf in ctfs) {
                val posicion: Int? = ctfService.obtenerPosicionGrupoEnCtf(grupoID, ctf.ctfID)
                println("   ${ctf.ctfID}   |    ${ctf.puntuacion}     |    $posicion      ")
            }
        }
    }

    /**
     * Método que muestra la información de la participación de un grupo
     *
     * @param comandos: Comandos con la información para mostrar la participación de un grupo
     * @param grupoService: Service de los grupos para llevar a cabo la muestra de información
     * @param ctfService: Service de los CTFS para llevar a cabo la muestra de información
     */
    fun mostrarParticipacionGrupo(
        comandos: Array<String>,
        grupoService: GrupoService,
        ctfService: CtfService,
    ) {
        mostrarDatosCTFConsola(comandos, grupoService, ctfService) // DUDA
    }

    /**
     * Método que lleva a cabo la muestra de la información de todos los grupos
     *
     * @param comandos: Comandos con la información para mostrar la participación de un grupo
     * @param grupoService: Service de los grupos para llevar a cabo la muestra de información
     * @param ctfService: Service de los CTFS para llevar a cabo la muestra de información
     */
    private fun mostrarDatosCTFConsola(
        comandos: Array<String>,
        grupoService: GrupoService,
        ctfService: CtfService,
    ) {
        val ctfID = comandos[1].toInt()

        val grupos: List<GrupoEntity> = grupoService.obtenerTodo()
        val ctf: CtfEntity? = ctfService.obtenerPorIDCtf(ctfID)

        if (ctf != null && ctf.ctfID != 0) {
            println("Procesando: Listado participación en el CTF '${ctf.ctfID}' ")
            println()
            println("GRUPO    | PUNTUACIÓN ")
            println("----------------------")

            val puntuaciones = mutableListOf<Pair<String, Int>>()
            var puntuacion: Int
            for (grupo in grupos) {
                puntuacion = ctfService.obtenerPuntuacionPorIDGrupoIDCtf(grupo.grupoID, ctf.ctfID)

                puntuaciones.add(Pair(grupo.grupoDesc, puntuacion))
            }

            val puntuacionesOrdenadas = puntuaciones.sortedByDescending { it.second }

            for ((grupoDesc, puntuacionEnOrden) in puntuacionesOrdenadas) {
                if (puntuacionEnOrden != 0) {
                    println("$grupoDesc  | $puntuacionEnOrden")
                }
            }
        } else {
            mensajeCtfInexistente(ctfID)
        }
    }

    /**
     * Método que realiza el procesamiento por lotes
     *
     * @param comandos: Comandos para llevar a cabo el procesamiento por lotes
     * @param dataSource:
     */
    fun procesamientoPorLotes(
        comandos: Array<String>,
        dataSource: DataSource,
    ) {
        val procesador = ProcesadorFicheros()
        val rutaFichero = comandos[1]
        val nuevosComandos = procesador.procesarFichero(rutaFichero)
        val app = GestorCTFS()
        for (comandoExtraido in nuevosComandos) {
            app.menu(dataSource, comandoExtraido.toTypedArray())
        }
    }

    /**
     * Método que lanza la interfaz gráfica
     *
     * @param dataSource:
     */
    fun interfazGrafica(dataSource: DataSource) {
        val interfazGrafica = InterfazGrafica()
        interfazGrafica.start(dataSource)
    }
}
