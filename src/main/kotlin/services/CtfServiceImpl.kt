package services

import dao.CtfDAO
import dao.entity.CtfEntity
import javax.sql.DataSource

/**
 * Clase que emplea los métodos del Servicio del CTF
 *
 * @property ctfDAO: objeto del DAO del CTF donde se lleva a cabo el código de estas funciones
 */
class CtfServiceImpl(private val ctfDAO: CtfDAO, private val dataSource: DataSource) : CtfService {
    /**
     * Método que crea un nuevo CTF con sus datos correspondientes
     *
     * @param ctf: Este contiene los datos del CTF a crear
     * @return CtfEntity: Devuelve una entidad de un CTF creado con los datos anteriores
     */
    override fun crear(ctf: CtfEntity): CtfEntity {
        return ctfDAO.crear(ctf)
    }

    /**
     * Método que actualiza datos de un CTF
     *
     * @param ctf: Este contiene los datos del CTF a actualizar
     * @return CtfEntity: Devuelve una entidad de un CTF actualizada con los datos anteriores
     */
    override fun actualizar(ctf: CtfEntity): CtfEntity {
        return ctfDAO.actualizar(ctf)
    }

    /**
     * Método que elimina un CTF
     *
     * @param idCtf: Este contiene el ID del CTF a eliminar
     * @param idGrupo: Este contiene el ID del grupo a eliminar
     */
    override fun borrar(
        idCtf: Int,
        idGrupo: Int,
    ) {
        return ctfDAO.borrar(idCtf, idGrupo)
    }

    /**
     * Método que obtiene los datos de todos los CTF y los almacena en una lista
     *
     * @return List<CtfEntity>: Devuelve una lista con todos los CTF anteriores
     */
    override fun obtenerTodo(): List<CtfEntity> {
        return ctfDAO.obtenerTodo()
    }

    /**
     * Método que obtiene los datos de un CTF por un ID dado
     *
     * @param id: Id del CTF que queremos obtener
     * @return CtfEntity?: Devuelve el CTF cuyo ID se ha dado
     */
    override fun obtenerPorIDCtf(id: Int): CtfEntity? {
        val sqlID = "SELECT * FROM CTFS WHERE ctfID = ?"

        return dataSource.connection.use { connID ->
            connID.prepareStatement(sqlID).use { stmtID ->
                stmtID.setInt(1, id)

                val rs = stmtID.executeQuery()

                if (rs.next()) {
                    CtfEntity(
                        ctfID = rs.getInt("ctfid"),
                        grupoID = rs.getInt("grupoid"),
                        puntuacion = rs.getInt("puntuacion"),
                    )
                } else {
                    null
                }
            }
        }
    }

    /**
     * Método que obtiene los datos de todos los CTFs en los que ha participado un grupo
     *
     * @param id: Id del grupo que queremos obtener
     * @return List<CtfEntity>: Devuelve una lista con los CTFs en los que ha participado el grupo dado
     */
    override fun obtenerPorIDGrupo(id: Int): List<CtfEntity> {
        val sqlID = "SELECT * FROM CTFS WHERE grupoID = ?"

        return dataSource.connection.use { connID ->
            connID.prepareStatement(sqlID).use { stmtID ->
                stmtID.setInt(1, id)

                val rs = stmtID.executeQuery()
                val ctfs = mutableListOf<CtfEntity>()

                while (rs.next()) {
                    ctfs.add(
                        CtfEntity(
                            ctfID = rs.getInt("ctfid"),
                            grupoID = rs.getInt("grupoid"),
                            puntuacion = rs.getInt("puntuacion"),
                        ),
                    )
                }
                ctfs
            }
        }
    }

    /**
     * Método que obtiene los datos de un CTF dado un Id de grupo y un Id de CTF
     *
     * @param idGrupo: Id del grupo que queremos obtener
     * @param idCtf: Id del ctf del que queremos obtener
     * @return CtfEntity: Devuelve el CTF buscado
     */
    override fun obtenerPorIDGrupoIDCtf(
        idGrupo: Int,
        idCtf: Int,
    ): CtfEntity? {
        val sqlIDs = "SELECT * FROM CTFS WHERE grupoID = ? AND ctfID = ?"

        return dataSource.connection.use { connIDs ->
            connIDs.prepareStatement(sqlIDs).use { stmtIDs ->
                stmtIDs.setInt(1, idGrupo)
                stmtIDs.setInt(2, idCtf)

                val rs = stmtIDs.executeQuery()

                if (rs.next()) {
                    CtfEntity(
                        ctfID = rs.getInt("ctfid"),
                        grupoID = rs.getInt("grupoid"),
                        puntuacion = rs.getInt("puntuacion"),
                    )
                } else {
                    null
                }
            }
        }
    }

    /**
     * Método que obtiene la puntuación de un grupo en un CTF
     *
     * @param idGrupo: Id del grupo a obtener la puntuación
     * @param idCtf: Id del CTF del que queremos la puntuación
     * @return Int: Devuelve la puntuación del grupo en el CTF
     */
    override fun obtenerPuntuacionPorIDGrupoIDCtf(
        idGrupo: Int,
        idCtf: Int,
    ): Int {
        val sqlID = "SELECT puntuacion FROM CTFS WHERE ctfID = ? AND grupoID = ?"
        var puntuacion = 0
        dataSource.connection.use { connID ->
            connID.prepareStatement(sqlID).use { stmtID ->
                stmtID.setInt(1, idCtf)
                stmtID.setInt(2, idGrupo)

                val rs = stmtID.executeQuery()
                val ctfs = mutableListOf<CtfEntity>()

                if (rs.next()) {
                    puntuacion = rs.getInt("puntuacion")
                }
                ctfs
            }
        }

        return puntuacion
    }

    /**
     * Método que obtiene la posición de un grupo en un CTF
     *
     * @param idGrupo: Id del grupo a obtener la posición
     * @param idCtf: Id del CTF del que queremos la posición
     * @return Int: Devuelve la posición del grupo en el CTF
     */
    override fun obtenerPosicionGrupoEnCtf(
        idGrupo: Int,
        idCtf: Int,
    ): Int? {
        val sql = "SELECT grupoid, puntuacion, RANK() OVER (PARTITION BY CTFid ORDER BY puntuacion DESC) AS posicion FROM CTFS WHERE CTFid = ?"

        dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, idCtf)

                val rs = stmt.executeQuery()
                while (rs.next()) {
                    if (rs.getInt("grupoid") == idGrupo) {
                        return rs.getInt("posicion")
                    }
                }
            }
        }
        return null
    }

    /**
     * Método que obtiene los ID de los diferentes CTFS que existen
     *
     * @return MutableList<Int>: Devuelve los ID pedidos
     */
    override fun obtenerIDCtfs(): MutableList<Int> {
        val ctfIDsRepetidos = obtenerTodo()
        val ctfIDs: MutableList<Int> = mutableListOf()
        for (ctf in ctfIDsRepetidos) {
            if (!ctfIDs.contains(ctf.ctfID)) {
                ctfIDs.add(ctf.ctfID)
            }
        }

        return ctfIDs
    }
}
