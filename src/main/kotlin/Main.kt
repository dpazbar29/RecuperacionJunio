import sql.utils.DataSourceFactory

fun main(args: Array<String>) {
    val dataSource = DataSourceFactory.getDS(DataSourceFactory.DataSourceType.HIKARI)

    val conexion = Conexion()
    conexion.conectar(dataSource)

    val argumentos = ParseadorArgumentos()
    val comandos: Array<String> = argumentos.parsearArgumentos(args)

    val app = AppCTFS()
    app.menu(dataSource, comandos)
}
