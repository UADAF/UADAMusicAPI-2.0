import java.nio.file.Paths


fun main() {
    val context = MusicContext(Paths.get("Music"))
    println(context)
}