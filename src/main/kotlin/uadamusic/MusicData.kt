package uadamusic

open class MusicData(
    val name: String, val type: MusicDataType, val parent: MusicData?,
    private val _title: String?, private val _img: String?, private val _format: String?
) {

    val path: String
        get() = "${parent?.path ?: ""}/$name${if (type == SONG) "$format" else ""}"

    val title: String
        get() = _title ?: name

    val img: String?
        get() = _img ?: parent?.img

    val format: String?
        get() = _format ?: parent?.format

    var children: List<MusicData>? = null
        private set

    val searchIndex by lazy(::indexChildren)

    fun initChildren(children: List<MusicData>) {
        if (this.children != null) {
            throw IllegalStateException("Children was already initialized")
        }
        this.children = children
    }

    override fun toString() = name

    private fun indexChildren(): Map<String, MusicData> {
        val m = mutableMapOf<String, MusicData>()
        if (children != null) {
            for (c in children!!) {
                m[c.name] = c
            }
            children!!.forEach { m.putAll(it.searchIndex) }
        }
        return m
    }

    fun search(s: String): List<MusicData>? {
        if (s.isEmpty()) {
            return children
        }
        if ('/' in s) {
            return search(*s.split('/').toTypedArray())
        }
        val lower = s.toLowerCase()
        val ret = mutableListOf<MusicData>()
        searchIndex.forEach { (name, data) ->
            if (lower in name.toLowerCase()) {
                ret.add(data)
            }
        }
        return ret
    }

    open fun search(vararg s: String): List<MusicData> {
        var cur = listOf(this)
        var next = mutableListOf<MusicData>()
        s.forEach { name ->
            cur.forEach { data ->
                data.search(name)?.let(next::addAll)
            }
            cur = next
            next = mutableListOf()
        }
        return cur
    }

}