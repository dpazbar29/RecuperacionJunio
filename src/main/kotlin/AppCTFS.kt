import dao.CtfDAOH2
import dao.GrupoDAOH2
import services.CtfService
import services.CtfServiceImpl
import services.GrupoService
import services.GrupoServiceImpl
import javax.sql.DataSource

interface AppCTFS {
    fun anadirGrupo(
        comandos: Array<String>,
        grupoService: GrupoService,
    ) {
    }

    fun anadirParticipacion(
        comandos: Array<String>,
        grupoService: GrupoService,
        ctfService: CtfService,
    ) {
    }

    fun eliminarGrupo(
        comandos: Array<String>,
        grupoService: GrupoService,
    ) {
    }

    fun eliminarParticipacion(
        comandos: Array<String>,
        grupoService: GrupoService,
        ctfService: CtfService,
    ) {
    }

    fun mostrarInformacionGrupos(
        comandos: Array<String>,
        grupoService: GrupoService,
        ctfService: CtfService,
    ) {
    }

    fun mostrarParticipacionGrupo(
        comandos: Array<String>,
        grupoService: GrupoService,
        ctfService: CtfService,
    ) {
    }

    fun procesamientoPorLotes(
        comandos: Array<String>,
        dataSource: DataSource,
    ) {
    }

    fun interfazGrafica(dataSource: DataSource) {
    }
}
