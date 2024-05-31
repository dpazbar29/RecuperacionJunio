import sql.utils.DataSourceFactory

fun main(args: Array<String>) {
    val appGestorCTS = AppGestorCTFS()
    val conexion = Conexion()
    val dataSource = DataSourceFactory.getDS(DataSourceFactory.DataSourceType.HIKARI)

    conexion.conectar(dataSource)
    appGestorCTS.menu(dataSource, args)
}
