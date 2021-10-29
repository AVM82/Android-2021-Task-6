package rs.school.rs.core.model

data class Song(
    val id: Int,
    val title: String = "no name",
    val artist: String = "no author",
    val bitmapUri: String = "",
    val trackUri: String = ""
)
