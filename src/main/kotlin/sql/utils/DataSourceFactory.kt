package sql.utils

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource

/**
 * Objeto que lleva a cabo la conexión de la base de datos
 */

object DataSourceFactory {
    /**
     * Enum Class que contiene los tipos de fuente de datos de la base de datos
     */
    enum class DataSourceType {
        HIKARI,
        JDBC,
    }

    /**
     * Método que realiza la conexión a la base de datos
     *
     * @param dataSourceType: Tipo de fuente de datos
     *
     * @return DataSource
     */
    fun getDS(dataSourceType: DataSourceType): DataSource {
        return when (dataSourceType) {
            DataSourceType.HIKARI -> {
                val config = HikariConfig()
                config.jdbcUrl = "jdbc:h2:./default"
                config.username = ""
                config.password = ""
                config.driverClassName = "org.h2.Driver"
                config.maximumPoolSize = 10
                config.isAutoCommit = true
                config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
                HikariDataSource(config)
            }

            DataSourceType.JDBC -> TODO()
        }
    }
}
