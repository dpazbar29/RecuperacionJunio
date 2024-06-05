package services

import dao.GrupoDAO
import dao.entity.CtfEntity
import dao.entity.GrupoEntity
import javax.sql.DataSource

/**
 * Clase que emplea los métodos del Servicio del Grupo
 *
 * @property grupoDAO: objeto del DAO del grupo donde se lleva a cabo el código de estas funciones
 */
class GrupoServiceImpl(private val grupoDAO: GrupoDAO, private val dataSource: DataSource) : GrupoService {
    /**
     * Método que crea un nuevo grupo con sus datos correspondientes
     *
     * @param grupo: Este contiene los datos del grupo a crear
     * @return GrupoEntity: Devuelve una entidad de un grupo creado con los datos anteriores
     */
    override fun crear(grupo: GrupoEntity): GrupoEntity {
        return grupoDAO.crear(grupo)
    }

    /**
     * Método que actualiza datos de un grupo
     *
     * @param grupo: Este contiene los datos del grupo a actualizar
     * @return CtfEntity: Devuelve una entidad de un grupo actualizada con los datos anteriores
     */
    override fun actualizar(grupo: GrupoEntity): GrupoEntity {
        return grupoDAO.actualizar(grupo)
    }

    /**
     * Método que elimina un grupo
     *
     * @param id: Este contiene el ID del grupo a eliminar
     */
    override fun borrar(id: Int) {
        return grupoDAO.borrar(id)
    }

    /**
     * Método que obtiene los datos de todos los grupos y los almacena en una lista
     *
     * @return List<GrupoEntity>: Devuelve una lista con todos los grupos anteriores
     */
    override fun obtenerTodo(): List<GrupoEntity> {
        return grupoDAO.obtenerTodo()
    }

    /**
     * Método que obtiene los datos de un grupo por un ID dado
     *
     * @param id: ID del grupo que queremos obtener
     * @return GrupoEntity?: Devuelve el grupo cuyo ID se ha dado
     */
    override fun obtenerPorID(id: Int): GrupoEntity? {
        return obtenerTodo().find { it.grupoID == id }
    }

    /**
     * Método que obtiene cuál ha sido la mejor posición de un grupo
     *
     * @param grupoId: Id del grupo en cuestión
     * @param ctfService: Service del ctf del cuál se sacarán los datos
     * @return Int: devuelve el número de la posición mejor del grupo
     */

    override fun obtenerMejorPosCTFIdParaGrupo(
        grupoId: Int,
        ctfService: CtfService,
    ): Int {
        var id: Int = 0
        var posicion = Int.MAX_VALUE // Valor máximo de un entero para que siempre sea mayor que la primera posición que se analice
        var puntuacion = 0
        val participaciones: List<CtfEntity> = ctfService.obtenerPorIDGrupo(grupoId)

        for (participacion in participaciones) {
            val posicionVariable = ctfService.obtenerPosicionGrupoEnCtf(participacion.grupoID, participacion.ctfID)
            if (posicionVariable != null) {
                if (posicionVariable < posicion) {
                    posicion = posicionVariable
                    id = participacion.ctfID
                } else if (posicionVariable == posicion) {
                    val puntuacionVariable = ctfService.obtenerPuntuacionPorIDGrupoIDCtf(participacion.grupoID, participacion.ctfID)
                    if (puntuacionVariable > puntuacion) {
                        puntuacion = puntuacionVariable
                        id = participacion.ctfID
                    }
                }
            }
        }

        return id
    }
}
