import java.nio.file.Path

class MusicContext(val dir: Path) : MusicData(dir.toAbsolutePath().toString(), CONTEXT, null, null, null, ".mp3") {



    fun load(data: MusicData) {

    }

}