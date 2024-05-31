package dao.entity

/**
 * Clase que contiene los campos de CTF
 *
 * @property ctfID: ID del CTF
 * @property grupoID: ID del grupo participante
 * @property puntuacion: Puntuación del grupo en el CTF
 */
data class CtfEntity(
    val ctfID: Int,
    val grupoID: Int,
    val puntuacion: Int,
)
