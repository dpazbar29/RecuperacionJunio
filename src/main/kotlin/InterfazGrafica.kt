import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dao.CtfDAOH2
import dao.GrupoDAOH2
import dao.entity.GrupoEntity
import java.io.File
import javax.sql.DataSource

class InterfazGrafica() {
    @Suppress("ktlint:standard:function-naming")
    @Composable
    @Preview
    fun App(dataSource: DataSource) {
        var text by remember { mutableStateOf("") }
        var grupos by remember { mutableStateOf(listOf<GrupoEntity>()) }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Introduce el ID del grupo") },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
            )

            Button(
                onClick = {
                    val grupoDAO = GrupoDAOH2(dataSource)
                    grupos =
                        if (text.isBlank()) {
                            grupoDAO.obtenerTodo()
                        } else {
                            val grupo = grupoDAO.obtenerPorID(text.toInt())
                            if (grupo != null) listOf(grupo) else emptyList()
                        }
                    text = ""
                },
                modifier = Modifier.padding(8.dp).width(150.dp),
            ) {
                Text("Mostrar")
            }

            Button(
                onClick = {
                    val ctfDAO = CtfDAOH2(dataSource)
                    exportarClasificacionFinal(ctfDAO, "clasificacion_final.txt")
                },
                modifier = Modifier.padding(8.dp).width(150.dp),
            ) {
                Text("Exportar")
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(8.dp),
            ) {
                items(grupos) { grupo ->
                    Text("Grupo ID: ${grupo.grupoID}, Desc: ${grupo.grupoDesc}, Mejor Pos CTF ID: ${grupo.mejorPosCtfID}")
                }
            }
        }
    }

    private fun exportarClasificacionFinal(
        ctfDAO: CtfDAOH2,
        nombreArchivo: String,
    ) {
        val ctfs = ctfDAO.obtenerTodo()
        val exportContent = StringBuilder()

        ctfs.forEach { ctf ->
            exportContent.append("CTF: ${ctf.ctfID}\n")
            val participaciones =
                ctfDAO.obtenerTodo()

            participaciones.forEachIndexed { index, participacion ->
                val grupos = ctfDAO.obtenerPorIDGrupo(participacion.grupoID)
                for (grupo in grupos) {
                    exportContent.append("${index + 1}. ${grupo.grupoID} (${participacion.puntuacion})\n")
                }
            }
            exportContent.append("\n")
        }

        File(nombreArchivo).writeText(exportContent.toString())
    }

    fun start(dataSource: DataSource) {
        application {
            Window(onCloseRequest = ::exitApplication) {
                App(dataSource)
            }
        }
    }
}
