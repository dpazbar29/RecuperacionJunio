package services

import dao.entity.CtfEntity

/**
 * Interfaz que tiene los métodos a emplear en el CTF
 */
interface CtfService {
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

    /**
     * Método que obtiene los datos de un CTF por un ID dado
     *
     * @param id: ID del CTF que queremos obtener
     * @return CtfEntity?: Devuelve el CTF cuyo ID se ha dado
     */
    fun obtenerPorIDCtf(id: Int): CtfEntity?

    /**
     * Método que obtiene los datos de todos los CTFs en los que ha participado un grupo
     *
     * @param id: ID del grupo que queremos obtener
     * @return List<CtfEntity>: Devuelve una lista con los CTFs en los que ha participado el grupo dado
     */
    fun obtenerPorIDGrupo(id: Int): List<CtfEntity>

    /**
     * Método que obtiene los datos de un CTF dado un Id de grupo y un Id de CTF
     *
     * @param idGrupo: Id del grupo que queremos obtener
     * @param idCtf: Id del ctf del que queremos obtener
     * @return CtfEntity: Devuelve el CTF buscado
     */
    fun obtenerPorIDGrupoIDCtf(
        idGrupo: Int,
        idCtf: Int,
    ): CtfEntity?

    /**
     * Método que obtiene la puntuación de un grupo en un CTF
     *
     * @param idGrupo: ID del grupo a obtener la puntuación
     * @param idCtf: ID del CTF del que queremos la puntuación
     * @return Int: Devuelve la puntuación del grupo en el CTF
     */
    fun obtenerPuntuacionPorIDGrupoIDCtf(
        idGrupo: Int,
        idCtf: Int,
    ): Int

    /**
     * Método que obtiene la posición de un grupo en un CTF
     *
     * @param idGrupo: ID del grupo a obtener la posición
     * @param idCtf: ID del CTF del que queremos la posición
     * @return Int: Devuelve la posición del grupo en el CTF
     */
    fun obtenerPosicionGrupoEnCtf(
        idGrupo: Int,
        idCtf: Int,
    ): Int?

    /**
     * Método que obtiene los ID de los diferentes CTFS que existen
     *
     * @return MutableList<Int>: Devuelve los ID pedidos
     */
    fun obtenerIDCtfs(): MutableList<Int>
}
