import dao.CtfDAOH2
import dao.GrupoDAOH2
import services.CtfServiceImpl
import services.GrupoServiceImpl
import javax.sql.DataSource

class AppCTFS(comando: ParseadorArgumentos) : GestorCTFS() {
    fun menu(
        dataSource: DataSource,
        args: Array<String>,
    ) {
        val grupoDao = GrupoDAOH2(dataSource)
        val ctfDao = CtfDAOH2(dataSource)

        val grupoService = GrupoServiceImpl(grupoDao)
        val ctfService = CtfServiceImpl(ctfDao)

        val comando = args[0]

        when (comando) {
            "-g" -> funcionG(args, grupoService)
            "-p" -> funcionP(args, grupoService, ctfService)
            "-t" -> funcionT(args, grupoService)
            "-e" -> funcionE(args, grupoService, ctfService)
            "-l" -> funcionL(args, grupoService, ctfService)
            "-c" -> funcionC(args, grupoService, ctfService)
            "-f" -> funcionF(args, dataSource)
            "-i" -> funcionI(dataSource)
        }
    }
}
