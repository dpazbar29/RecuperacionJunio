import java.sql.PreparedStatement
import javax.sql.DataSource

/**
 * Clase que lleva a cabo la conexión y la comprobación de las tablas que se usan en la base de datos
 */
class Conexion {
    /**
     * Método que realiza la conexión de la base de datos
     */
    fun conectar(dataSource: DataSource) {
        val connection = dataSource.connection
        comprobarTablaGrupos(connection)
        comprobarTablaCtfs(connection)
    }

    /**
     * Método que comprueba que la tabla Grupos existe
     *
     * @param connection: Conexión a la base de datos
     */
    private fun comprobarTablaGrupos(connection: java.sql.Connection) {
        val checkGrupos: PreparedStatement =
            connection.prepareStatement(
                "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'GRUPOS';",
            )
        val gruposExisten =
            checkGrupos.executeQuery().use { rs ->
                rs.next()
                rs.getInt(1) > 0
            }
        if (!gruposExisten) {
            val queryGrupo =
                connection.prepareStatement(
                    "CREATE TABLE GRUPOS (grupoID NUMBER(10,0) CONSTRAINT PK_ID_GRUPO PRIMARY KEY, grupoDesc VARCHAR2(40), mejorPosCtfID NUMBER(10,0));",
                )
            queryGrupo.executeUpdate()
        }
    }

    /**
     * Método que comprueba que la tabla CTFS existe
     *
     * @param connection: Conexión a la base de datos
     */
    private fun comprobarTablaCtfs(connection: java.sql.Connection) {
        val checkCtf: PreparedStatement =
            connection.prepareStatement(
                "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'CTFS';",
            )
        val ctfExisten =
            checkCtf.executeQuery().use { rs ->
                rs.next()
                rs.getInt(1) > 0
            }
        if (!ctfExisten) {
            val queryCtf =
                connection.prepareStatement(
                    "CREATE TABLE CTFS (ctfID NUMBER(10,0), grupoID NUMBER(10,0), puntuacion NUMBER(100,0),CONSTRAINT PK_CTFS PRIMARY KEY (ctfID, grupoID),CONSTRAINT FK_GRID_GRID FOREIGN KEY (grupoID) REFERENCES GRUPOS(grupoID) ON DELETE CASCADE);",
                )

            val queryClave =
                connection.prepareStatement(
                    "ALTER TABLE GRUPOS ADD CONSTRAINT fk_pos_grid FOREIGN KEY (mejorPosCtfID, grupoID) REFERENCES CTFS(ctfID, grupoID) ON DELETE CASCADE;",
                )

            queryCtf.executeUpdate()
            queryClave.executeUpdate()
        }
    }
}
