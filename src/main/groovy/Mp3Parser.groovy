import com.mpatric.mp3agic.Mp3File

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class Mp3Parser {

    private static final String RESULT_DIR = 'result'

    String pathString

    List<Path> parseAllSuitableMp3() {
        Path dir
        if (pathString != null) {
            dir = Paths.get(pathString)
        }
        if (dir == null || Files.notExists(dir)) {
            dir = Paths.get(getClass().protectionDomain.codeSource.location.toURI()).getParent()
            println "Run in '${dir}'"
        }
        Path resDir = dir.resolve(RESULT_DIR)
        if (Files.notExists(resDir)) {
            Files.createDirectory(resDir)
            println "Created new result dir '${resDir}'"
        }
        dir.toFile().eachFile {
            def name = it.name.take(it.name.lastIndexOf("."))
            def extension = it.name[it.name.lastIndexOf(".") + 1..it.name.length() - 1]
            if (extension == "mp3") {
                println "Find new mp3 '${it}'"
                def mp3 = new Mp3File(it)
                def tags = mp3.getId3v2Tag()
                if (!tags.getTitle().startsWith(name)) {
                    tags.setTitle(name + '_' + tags.getTitle())
                    tags.setAlbum("Brother")
                    tags.setArtist("Maximus")
                    tags.setPublisher("Maximus")
                    tags.setCopyright("Maximus")
                    tags.setYear('2018')
                    mp3.save(resDir.resolve(it.name).toString())
                    println "Created new '${it}'"
                } else {
                    println "Mp3 '${it}' were skiped"
                }
            }
        }
    }
}
