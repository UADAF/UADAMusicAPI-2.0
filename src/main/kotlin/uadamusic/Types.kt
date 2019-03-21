package uadamusic

data class MusicDataType(val name: String) {

    init {
        if(typeMap.containsKey(name)) {
            throw IllegalArgumentException("Name '$name' is already occupied")
        }
        typeMap[name] = this
    }

    companion object {
        private val typeMap: MutableMap<String, MusicDataType> = mutableMapOf()

        operator fun get(type: String) = typeMap[type]

    }

}

val SONG = MusicDataType("song")
val ALBUM = MusicDataType("album")
val AUTHOR = MusicDataType("author")
val GROUP = MusicDataType("group")
val CONTEXT = MusicDataType("context")