

open class MusicData(val name: String, val type: MusicDataType, val parent: MusicData?,
                     private val _title: String?, private val _img: String?, private val _format: String?) {

    val path: String
        get() = "${parent?.path ?: ""}/$name${if (type == SONG) ".$format" else ""}"

    val title: String
        get() = _title ?: name

    val img: String?
        get() = _img ?: parent?.img

    val format: String?
        get() = _format ?: parent?.format

    lateinit var children: List<MusicData>
        private set

    fun initChildren(children: List<MusicData>) {
        if(this::children.isInitialized) {
            throw IllegalStateException("Children was already initialized")
        }
        this.children = children
    }

    override fun toString() = name

}