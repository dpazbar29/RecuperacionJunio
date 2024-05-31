package dao.entity

/**
 * Clase que contiene los campos de Grupo
 *
 * @property grupoID: ID del grupo
 * @property grupoDesc: Descripción del grupo participante
 * @property mejorPosCtfID: Mejor posición de un grupo en un CTF
 */
data class GrupoEntity(
    val grupoID: Int,
    val grupoDesc: String,
    val mejorPosCtfID: Int?,
)
