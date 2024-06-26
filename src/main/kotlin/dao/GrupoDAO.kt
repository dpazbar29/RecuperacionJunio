package dao

import dao.entity.GrupoEntity

/**
 * Interfaz que tiene el CRUD a emplear en el grupo
 */
interface GrupoDAO {
    /**
     * Método que crea un nuevo grupo con sus datos correspondientes
     *
     * @param grupo: Este contiene los datos del grupo a crear
     * @return GrupoEntity: Devuelve una entidad de un grupo creado con los datos anteriores
     */
    fun crear(grupo: GrupoEntity): GrupoEntity

    /**
     * Método que actualiza datos de un grupo
     *
     * @param grupo: Este contiene los datos del grupo a actualizar
     * @return CtfEntity: Devuelve una entidad de un grupo actualizada con los datos anteriores
     */
    fun actualizar(grupo: GrupoEntity): GrupoEntity

    /**
     * Método que elimina un grupo
     *
     * @param id: Este contiene el ID del grupo a eliminar
     */
    fun borrar(id: Int)

    /**
     * Método que obtiene los datos de todos los grupos y los almacena en una lista
     *
     * @return List<GrupoEntity>: Devuelve una lista con todos los grupos anteriores
     */
    fun obtenerTodo(): List<GrupoEntity>
}
