package uadamusic

import com.google.gson.JsonParseException
import uadamusic.utils.JSON_PARSER
import uadamusic.utils.obj
import uadamusic.utils.parse
import uadamusic.utils.str
import utils.*
import java.lang.IllegalStateException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.streams.asSequence

class MusicContext(dir: Path) : MusicData(dir.toAbsolutePath().toString(),
    CONTEXT, null, null, null, ".mp3") {


    init {
        load(this)
    }


    fun load(data: MusicData) {
        val p = Paths.get(data.path)
        data.initChildren(Files.list(p).asSequence().map {
            if(Files.isRegularFile(it)) {
                MusicData(it.fileName.toString(), SONG, data, null, null, null)
            } else {
                val d = load(it, data)
                if(d != null) {
                    load(d)
                }
                d
            }
        }.filterNotNull().toList())
    }

    fun load(p: Path, parent: MusicData?): MusicData? {
        val jsonFile = p.resolve("music.info.json")
        if(!Files.exists(jsonFile)) {
            println("music.info.json not found in $p. Ignoring") //TODO: Replace with logger
            return null
        }
        val json = try {
            JSON_PARSER.parse(jsonFile).obj
        } catch (e: JsonParseException) {
            println("Unable to parse music.info.json in $p. Ignoring") //TODO: Replace with logger
            return null
        } catch (e: IllegalStateException) {
            println("Unable to parse music.info.json in $p. Ignoring") //TODO: Replace with logger
            return null
        }
        if("type" !in json) {
            println("No type specified in music.info.json in $p. Ignoring") //TODO: Replace with logger
            return null
        }
        val type = json["type"].str
        val title = json["title"]?.str
        val img = json["img"]?.str
        val format = json["format"]?.str
        return MusicData(p.fileName.toString(), MusicDataType[type]!!, parent, title, img, format)
    }

}