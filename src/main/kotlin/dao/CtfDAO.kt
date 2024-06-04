package dao

import dao.entity.CtfEntity

/**
 * Interfaz que tiene el CRUD del CTF
 */
interface CtfDAO {
    /**
     * Método que crea un nuevo CTF con sus datos correspondientes
     *
     * @param ctf: Este contiene los datos del CTF a crear
     * @return CtfEntity: Devuelve una entidad de un CTF creado con los datos anteriores
     */
    fun crear(ctf: CtfEntity): CtfEntity

    /**
     * Método que actualiza datos de un CTF
     *
     * @param ctf: Este contiene los datos del CTF a actualizar
     * @return CtfEntity: Devuelve una entidad de un CTF actualizada con los datos anteriores
     */
    fun actualizar(ctf: CtfEntity): CtfEntity

    /**
     * Método que elimina un CTF
     *
     * @param idCtf: Este contiene el ID del CTF a eliminar
     * @param idGrupo: Este contiene el ID del grupo a eliminar
     */
    fun borrar(
        idCtf: Int,
        idGrupo: Int,
    )

    /**
     * Método que obtiene los datos de todos los CTF y los almacena en una lista
     *
     * @return List<CtfEntity>: Devuelve una lista con todos los CTF anteriores
     */
    fun obtenerTodo(): List<CtfEntity>
}
