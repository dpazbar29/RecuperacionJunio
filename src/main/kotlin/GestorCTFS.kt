import dao.CtfDAOH2
import dao.GrupoDAOH2
import services.CtfService
import services.CtfServiceImpl
import services.GrupoService
import services.GrupoServiceImpl
import javax.sql.DataSource

open class GestorCTFS: AppCTFS {
    fun menu(
        dataSource: DataSource,
        comandos: Array<String>,
    ) {
        val grupoDao = GrupoDAOH2(dataSource)
        val ctfDao = CtfDAOH2(dataSource)

        val grupoService = GrupoServiceImpl(grupoDao)
        val ctfService = CtfServiceImpl(ctfDao)

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