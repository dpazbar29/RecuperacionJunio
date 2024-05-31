package services

import dao.CtfDAO
import dao.GrupoDAO
import dao.entity.GrupoEntity

/**
 * Clase que emplea los métodos del Servicio del Grupo
 *
 * @property grupoDAO: objeto del DAO del grupo donde se lleva a cabo el código de estas funciones
 */
class GrupoServiceImpl(private val grupoDAO: GrupoDAO) : GrupoService {
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
        return grupoDAO.obtenerPorID(id)
    }

    /**
     * Método que obtiene cuál ha sido la mejor posición de un grupo
     *
     * @param grupoId: ID del grupo en cuestión
     * @param ctfDAO: DAO del ctf del cuál se sacarán los datos
     * @return Int: devuelve el número de la posición mejor del grupo
     */
    override fun obtenerMejorPosCTFIdParaGrupo(
        grupoId: Int,
        ctfDAO: CtfDAO,
    ): Int {
        return grupoDAO.obtenerMejorPosCTFIdParaGrupo(grupoId, ctfDAO)
    }
}
