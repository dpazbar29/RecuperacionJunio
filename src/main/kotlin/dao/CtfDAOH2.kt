package dao

import dao.entity.CtfEntity
import javax.sql.DataSource

/**
 * Clase que emplea los métodos del CRUD del CTF
 *
 * @property dataSource
 */
class CtfDAOH2(private val dataSource: DataSource) : CtfDAO {
    /**
     * Método que crea un nuevo CTF con sus datos correspondientes
     *
     * @param ctf: Este contiene los datos del CTF a crear
     * @return CtfEntity: Devuelve una entidad de un CTF creado con los datos anteriores
     */
    override fun crear(ctf: CtfEntity): CtfEntity {
        val sqlCr = "INSERT INTO CTFS (ctfID, grupoID, puntuacion) VALUES (?, ?, ?)"

        return dataSource.connection.use { connCr ->
            connCr.prepareStatement(sqlCr).use { stmtCr ->
                stmtCr.setInt(1, ctf.ctfID)
                stmtCr.setInt(2, ctf.grupoID)
                stmtCr.setInt(3, ctf.puntuacion)
                stmtCr.executeUpdate()
                ctf
            }
        }
    }

    /**
     * Método que actualiza datos de un CTF
     *
     * @param ctf: Este contiene los datos del CTF a actualizar
     * @return CtfEntity: Devuelve una entidad de un CTF actualizada con los datos anteriores
     */
    override fun actualizar(ctf: CtfEntity): CtfEntity {
        val sqlAct = "UPDATE CTFS SET grupoID = ?, puntuacion = ? WHERE ctfID = ?"

        return dataSource.connection.use { connAct ->
            connAct.prepareStatement(sqlAct).use { stmtAct ->
                stmtAct.setInt(1, ctf.grupoID)
                stmtAct.setInt(2, ctf.puntuacion)
                stmtAct.setInt(3, ctf.ctfID)
                stmtAct.executeUpdate()
                ctf
            }
        }
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
        val sqlDel = "DELETE FROM CTFS WHERE ctfID = ? AND grupoID = ?"

        dataSource.connection.use { connDel ->
            connDel.prepareStatement(sqlDel).use { stmtDel ->
                stmtDel.setInt(1, idCtf)
                stmtDel.setInt(2, idGrupo)
                stmtDel.executeUpdate()
            }
        }
    }

    /**
     * Método que obtiene los datos de todos los CTF y los almacena en una lista
     *
     * @return List<CtfEntity>: Devuelve una lista con todos los CTF anteriores
     */
    override fun obtenerTodo(): List<CtfEntity> {
        val sqlAll = "SELECT * FROM CTFS"

        return dataSource.connection.use { connAll ->
            connAll.prepareStatement(sqlAll).use { stmtAll ->
                val rs = stmtAll.executeQuery()
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
}
