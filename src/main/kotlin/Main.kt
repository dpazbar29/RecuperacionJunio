fun main(args: Array<String>) {
    val parse = ParseadorArgumentos()
    parse.parsearArgumentos(args)
    /*
    val appGestorCTS = AppGestorCTFS()
    val conexion = Conexion()
    val dataSource = DataSourceFactory.getDS(DataSourceFactory.DataSourceType.HIKARI)

    conexion.conectar(dataSource)
    appGestorCTS.menu(dataSource, args)
     */
}
