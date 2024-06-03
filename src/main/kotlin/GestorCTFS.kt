import services.CtfService
import services.GrupoService
import javax.sql.DataSource

open class GestorCTFS {
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
