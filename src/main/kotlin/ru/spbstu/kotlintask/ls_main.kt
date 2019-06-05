package ru.spbstu.kotlintask

import com.github.ajalt.clikt.core.NoRunCliktCommand
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import org.apache.commons.io.comparator.NameFileComparator
import sun.security.util.Length
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes
import java.util.*

/**
 * Вариант 1 -- ls
Вывод содержимого указанной в качестве аргумента директории в виде
отсортированного списка имен файлов.
● Флаг -l (long) переключает вывод в длинный формат, в котором, кроме имени
файла, указываются права на выполнение/чтение/запись в виде битовой маски
XXX, время последней модификации и размер в байтах.
● Флаг -h (human-readable) переключает вывод в человеко-читаемый формат
(размер в кило-, мега- или гигабайтах, права на выполнение в виде rwx).
● Флаг -r (reverse) меняет порядок вывода на противоположный.
● Флаг -o (output) указывает имя файла, в который следует вывести результат;
если этот флаг отсутствует, результат выводится в консоль.
В случае, если в качестве аргумента указан файл, а не директория, следует вывести
информацию об этом файле.
Command Line: ls [-l] [-h] [-r] [-o output.file] directory_or_file
Кроме самой программы, следует написать автоматические тесты к ней
 */
fun main(inputStr : String) : Any {
    val input = inputStr.split(" ").toMutableList()
    val inputDir = input[input.size - 1]
    input.removeAt(input.size - 1)
    val inputInfo = input.toTypedArray()
    val args = inputInfo.parse()
    val toOut : MutableList<String>
    if (!args.initFlag) throw IllegalArgumentException("Отсутствует флаг инициализации -ls")

    fun short(path : Path): MutableList<String> {
        val listOfFiles = mutableListOf<String>()
        val files = File("$path").listFiles()
        if (args.rFlag) Arrays.sort(files, NameFileComparator.NAME_REVERSE)
        else Arrays.sort(files, NameFileComparator.NAME_COMPARATOR)
        files.forEach {
            if (it.isFile){
                if(!args.hFlag) listOfFiles.add("${it.name} ${it.length()}")
                else {
                    val length = lengthToHuman(it.length())
                    listOfFiles.add("${it.name} $length")
                }
            }
            else listOfFiles.add("dir ${it.name}")
        }
        return listOfFiles
    }

    fun long(path: Path): MutableList<String> {
        val files = File("$path").listFiles()
        var attr: BasicFileAttributes
        val listOfFiles = mutableListOf<String>()
        if (args.rFlag) Arrays.sort(files, NameFileComparator.NAME_REVERSE)
        else Arrays.sort(files, NameFileComparator.NAME_COMPARATOR)
        files.forEach {
            if (it.isFile) {
                attr = Files.readAttributes<BasicFileAttributes>(it.toPath(), BasicFileAttributes::class.java)
                var read = 0
                var readc = '-'
                var write = 0
                var writec = '-'
                var execute = 0
                var executec = '-'
                if (it.canRead()) {
                    read = 1
                readc = 'r'}
                if (it.canWrite()) {
                    write = 1
                writec = 'w'}
                if (it.canExecute()) {
                    execute = 1
                executec = 'x'}
                if (!args.hFlag)
                    listOfFiles.add("${it.name} $execute$read$write ${attr.lastModifiedTime()} ${attr.size()}")
                else {
                    val length = lengthToHuman(attr.size())
                    val modTime = attr.lastModifiedTime().toString().split("T")[1].split(".")[0]
                    val modDate = attr.lastModifiedTime().toString().split("T")[0]
                    listOfFiles.add("${it.name} $readc$writec$executec $modDate $modTime $length")
                }
            } else listOfFiles.add("dir ${it.name}")
        }
        return listOfFiles
    }
    toOut = if (args.lFlag) long(Paths.get(inputDir))
    else short(Paths.get(inputDir))
    return if (args.outputDir != null) outputToFile(toOut, args.outputDir.toString())
    else toOut

}

class Args : NoRunCliktCommand() {
    val initFlag : Boolean by option("-ls").flag("-LS",default = false)
    val lFlag: Boolean by option("-l").flag("-L", default = false)
    val hFlag: Boolean by option("-h").flag("-H", default = false)
    val rFlag:Boolean by option("-r").flag("-R", default = false)
    val outputDir: String? by option("-o")
}

fun Array<String>.parse(): Args {
    val args = Args()
    args.parse(this)
    return args
}

fun lengthToHuman(input : Long) : String {
    var length = input.toFloat()
    val result: String
    if (length > 1023) {
        length /= 1024
        if (length > 1023) {
            length /= 1024
            if (length > 1023) {
                length/= 1024
                result = "$length Гбайт"
            }
            else result = "$length Мбайт"
        }
        else result = "$length Кбайт"
    }
    else result = "$length байт"
    return result
}
fun output(toout: MutableList<String>) {
    toout.forEach {
        println(it)
    }
}

fun outputToFile(toOut: MutableList<String>,name :String) {
    val writer = File(name).bufferedWriter()
        toOut.forEach {
            writer.write(it)
            writer.newLine()
        }
    writer.close()
}
