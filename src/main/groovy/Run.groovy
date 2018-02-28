println 'Maximus MP3 parser'
println 'Filter only mp3 files. Take file name without extension and prepend to mp3 Title tag. Skip if mp3 Title tag already have been starting with filename'
println "Arguments : ${args}"

def app
if (args.length > 0) {
    app = new Mp3Parser(pathString: args[0])
} else {
    app = new Mp3Parser()
}
app.parseAllSuitableMp3()
println "The End"
