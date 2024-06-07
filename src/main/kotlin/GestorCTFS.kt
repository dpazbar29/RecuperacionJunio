import dao.CtfDAOH2
import dao.GrupoDAOH2
import services.CtfServiceImpl
import services.GrupoServiceImpl
import javax.sql.DataSource

/**
 * Clase que Gestiona las órdenes del usuario
 */
open class GestorCTFS(private val appCTFS: AppCTFS) {
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

        val grupoService = GrupoServiceImpl(grupoDao)
        val ctfService = CtfServiceImpl(ctfDao)

        when (comandos[0]) {
            "-g" -> appCTFS.anadirGrupo(comandos, grupoService)
            "-p" -> appCTFS.anadirParticipacion(comandos, grupoService, ctfService)
            "-t" -> appCTFS.eliminarGrupo(comandos, grupoService)
            "-e" -> appCTFS.eliminarParticipacion(comandos, grupoService, ctfService)
            "-l" -> appCTFS.mostrarInformacionGrupos(comandos, grupoService, ctfService)
            "-c" -> appCTFS.mostrarParticipacionGrupo(comandos, grupoService, ctfService)
            "-f" -> appCTFS.procesamientoPorLotes(comandos, dataSource)
            "-i" -> appCTFS.interfazGrafica(dataSource)
        }
    }
}
