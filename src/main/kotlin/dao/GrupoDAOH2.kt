package dao

import dao.entity.CtfEntity
import dao.entity.GrupoEntity
import javax.sql.DataSource

/**
 * Clase que emplea los métodos del CRUD del grupo
 *
 * @property dataSource
 */
class GrupoDAOH2(private val dataSource: DataSource) : GrupoDAO {
    /**
     * Método que crea un nuevo grupo con sus datos correspondientes
     *
     * @param grupo: Este contiene los datos del grupo a crear
     * @return GrupoEntity: Devuelve una entidad de un grupo creado con los datos anteriores
     */
    override fun crear(grupo: GrupoEntity): GrupoEntity {
        val sqlCr = "INSERT INTO GRUPOS (grupoID, grupoDesc, mejorPosCtfID) VALUES (?, ?, ?)"

        return dataSource.connection.use { connCr ->
            connCr.prepareStatement(sqlCr).use { stmtCr ->
                stmtCr.setInt(1, grupo.grupoID)
                stmtCr.setString(2, grupo.grupoDesc)
                // Establece mejorPosCtfID como nulo si es nulo en el objeto grupo
                if (grupo.mejorPosCtfID != null) {
                    stmtCr.setInt(3, grupo.mejorPosCtfID)
                } else {
                    stmtCr.setNull(3, java.sql.Types.INTEGER)
                }
                stmtCr.executeUpdate()
                grupo
            }
        }
    }

    /**
     * Método que actualiza datos de un grupo
     *
     * @param grupo: Este contiene los datos del grupo a actualizar
     * @return CtfEntity: Devuelve una entidad de un grupo actualizada con los datos anteriores
     */
    override fun actualizar(grupo: GrupoEntity): GrupoEntity {
        val sqlAct = "UPDATE GRUPOS SET grupoDesc = ?, mejorPosCtfID = ? WHERE grupoID = ?"

        return dataSource.connection.use { connAct ->
            connAct.prepareStatement(sqlAct).use { stmtAct ->
                stmtAct.setString(1, grupo.grupoDesc)
                grupo.mejorPosCtfID?.let { stmtAct.setInt(2, it) }
                stmtAct.setInt(3, grupo.grupoID)
                stmtAct.executeUpdate()
                grupo
            }
        }
    }

    /**
     * Método que elimina un grupo
     *
     * @param id: Este contiene el ID del grupo a eliminar
     */
    override fun borrar(id: Int) {
        val sqlDel = "DELETE FROM GRUPOS WHERE grupoID = ?"
        val sqlDelCtf = "DELETE FROM CTFS WHERE grupoID = ?"

        dataSource.connection.use { connDel ->
            connDel.prepareStatement(sqlDel).use { stmtDel ->
                stmtDel.setInt(1, id)
                stmtDel.executeUpdate()
            }
        }

        dataSource.connection.use { connDelCtf ->
            connDelCtf.prepareStatement(sqlDelCtf).use { stmtDelCtf ->
                stmtDelCtf.setInt(1, id)
                stmtDelCtf.executeUpdate()
            }
        }
    }

    /**
     * Método que obtiene los datos de todos los grupos y los almacena en una lista
     *
     * @return List<GrupoEntity>: Devuelve una lista con todos los grupos anteriores
     */
    override fun obtenerTodo(): List<GrupoEntity> {
        val sqlAll = "SELECT * FROM GRUPOS"

        return dataSource.connection.use { connAll ->
            connAll.prepareStatement(sqlAll).use { stmtAll ->
                val rs = stmtAll.executeQuery()
                val grupos = mutableListOf<GrupoEntity>()

                while (rs.next()) {
                    grupos.add(
                        GrupoEntity(
                            grupoID = rs.getInt("grupoid"),
                            grupoDesc = rs.getString("grupodesc"),
                            mejorPosCtfID = rs.getInt("mejorposctfid"),
                        ),
                    )
                }
                stmtAll.executeQuery()
                grupos
            }
        }
    }

    /**
     * Método que obtiene los datos de un grupo por un ID dado
     *
     * @param id: ID del grupo que queremos obtener
     * @return GrupoEntity?: Devuelve el grupo cuyo ID se ha dado
     */
    override fun obtenerPorID(id: Int): GrupoEntity? {
        val sqlID = "SELECT * FROM GRUPOS WHERE grupoID = ?;"

        return dataSource.connection.use { connID ->
            connID.prepareStatement(sqlID).use { stmtID ->
                stmtID.setInt(1, id)

                val rs = stmtID.executeQuery()
                if (rs.next()) {
                    GrupoEntity(
                        grupoID = rs.getInt("grupoID"),
                        grupoDesc = rs.getString("grupodesc"),
                        mejorPosCtfID = rs.getInt("mejorposCTFid"),
                    )
                } else {
                    null
                }
            }
        }
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
        var id: Int = 0
        var posicion = Int.MAX_VALUE // Valor máximo de un entero para que siempre sea mayor que la primera posición que se analice
        var puntuacion = 0
        val participaciones: List<CtfEntity> = ctfDAO.obtenerPorIDGrupo(grupoId)

        for (participacion in participaciones) {
            val posicionVariable = ctfDAO.obtenerPosicionGrupoEnCtf(participacion.grupoID, participacion.ctfID)
            if (posicionVariable != null) {
                if (posicionVariable < posicion) {
                    posicion = posicionVariable
                    id = participacion.ctfID
                } else if (posicionVariable == posicion) {
                    val puntuacionVariable = ctfDAO.obtenerPuntuacionPorIDGrupoIDCtf(participacion.grupoID, participacion.ctfID)
                    if (puntuacionVariable > puntuacion)
                        {
                            puntuacion = puntuacionVariable
                            id = participacion.ctfID
                        }
                }
            }
        }

        return id
    }
}
