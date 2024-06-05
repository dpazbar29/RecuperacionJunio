import dao.CtfDAOH2
import dao.GrupoDAOH2
import services.CtfServiceImpl
import services.GrupoServiceImpl
import javax.sql.DataSource

/**
 * Clase que Gestiona las órdenes del usuario
 */
open class GestorCTFS : AppCTFS {
    /**
     * Método que gestiona los comandos y llama a la aplicación
     *
     * @param dataSource:
     * @param comandos: Comandos que se derivarán a las opciones
     */
    fun menu(
        dataSource: DataSource,
        comandos: Array<String>,
    ) {
        val grupoDao = GrupoDAOH2(dataSource)
        val ctfDao = CtfDAOH2(dataSource)

        val grupoService = GrupoServiceImpl(grupoDao, dataSource)
        val ctfService = CtfServiceImpl(ctfDao, dataSource)

        when (comandos[0]) {
            "-g" -> anadirGrupo(comandos, grupoService)
            "-p" -> anadirParticipacion(comandos, grupoService, ctfService)
            "-t" -> eliminarGrupo(comandos, grupoService)
            "-e" -> eliminarParticipacion(comandos, grupoService, ctfService)
            "-l" -> mostrarInformacionGrupos(comandos, grupoService, ctfService)
            "-c" -> mostrarParticipacionGrupo(comandos, grupoService, ctfService)
            "-f" -> procesamientoPorLotes(comandos, dataSource)
            "-i" -> interfazGrafica(dataSource)
        }
    }
}
