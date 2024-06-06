package dao

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
}
