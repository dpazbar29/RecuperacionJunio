import sql.utils.DataSourceFactory

fun main(args: Array<String>) {
    val dataSource = DataSourceFactory.getDS(DataSourceFactory.DataSourceType.HIKARI)

    val conexion = Conexion()
    conexion.conectar(dataSource)

    val salida = Salida()
    val argumentos = ParseadorArgumentos(salida)
    val comandos: Array<String> = argumentos.parsearArgumentos(args)

    val appCTFS = AppCTFS(salida)
    val gestorCTFS = GestorCTFS(appCTFS)
    gestorCTFS.menu(dataSource, comandos)
}
