package ru.spbstu.kotlintask


import org.junit.Assert.*
import java.io.File
import java.nio.file.Paths

private fun assertFileContent(name: String, expectedContent: String) {
    val file = File(name)
    val content = file.readLines().joinToString("\n")
    assertEquals(expectedContent, content)
}

class Ls_mainKtTest {
    val resourseDir = Paths.get("src", "test", "resources", "InputFiles")
    @org.junit.Test
    fun main() {
        assertEquals(
            mutableListOf("1.txt rwx 2019-06-02 21:12:21 905.0 байт",
                "10.txt rwx 2019-04-12 00:14:41 0.0 байт",
                "12.txt rwx 2019-04-12 00:14:56 0.0 байт",
                "2.txt rwx 2019-06-02 21:12:26 4.0 байт",
                "20.txt rwx 2019-06-02 21:09:41 3.0 байт",
                "21.txt rwx 2019-06-02 21:09:35 53.0 байт",
                "3.txt rwx 2019-04-12 00:14:33 0.0 байт",
                "4.txt rwx 2019-04-12 00:14:36 0.0 байт",
                "videoplayback.mp4 rwx 2019-04-12 00:44:40 727.93164 Кбайт")
            , main("-ls -l -h $resourseDir"))
        assertEquals(
            mutableListOf("1.txt 905.0 байт",
                "10.txt 0.0 байт",
                "12.txt 0.0 байт",
                "2.txt 4.0 байт",
                "20.txt 3.0 байт",
                "21.txt 53.0 байт",
                "3.txt 0.0 байт",
                "4.txt 0.0 байт",
                "videoplayback.mp4 727.93164 Кбайт")
            , main("-ls -h $resourseDir"))
        assertEquals(
            mutableListOf("1.txt 111 2019-06-02T21:12:21.101309Z 905",
                "10.txt 111 2019-04-12T00:14:41.193967Z 0",
                "12.txt 111 2019-04-12T00:14:56.901865Z 0",
                "2.txt 111 2019-06-02T21:12:26.465616Z 4",
                "20.txt 111 2019-06-02T21:09:41.072156Z 3",
                "21.txt 111 2019-06-02T21:09:35.406832Z 53",
                "3.txt 111 2019-04-12T00:14:33.525528Z 0",
                "4.txt 111 2019-04-12T00:14:36.769714Z 0",
                "videoplayback.mp4 111 2019-04-12T00:44:40.681892Z 745402")
            , main("-ls -l $resourseDir"))
        assertEquals(
            mutableListOf("1.txt 905",
                "10.txt 0",
                "12.txt 0",
                "2.txt 4",
                "20.txt 3",
                "21.txt 53",
                "3.txt 0",
                "4.txt 0",
                "videoplayback.mp4 745402")
            , main("-ls $resourseDir"))
        main("-ls -l -h -o temp.txt D:\\\\kotlinTask\\InputFiles")
        assertFileContent("temp.txt",
            "1.txt rwx 2019-06-02 21:12:21 905.0 байт\n" +
                    "10.txt rwx 2019-04-12 00:14:41 0.0 байт\n" +
                    "12.txt rwx 2019-04-12 00:14:56 0.0 байт\n" +
                    "2.txt rwx 2019-06-02 21:12:26 4.0 байт\n" +
                    "20.txt rwx 2019-06-02 21:09:41 3.0 байт\n" +
                    "21.txt rwx 2019-06-02 21:09:35 53.0 байт\n" +
                    "3.txt rwx 2019-04-12 00:14:33 0.0 байт\n" +
                    "4.txt rwx 2019-04-12 00:14:36 0.0 байт\n" +
                    "videoplayback.mp4 rwx 2019-04-12 00:44:40 727.93164 Кбайт")
        main("-ls -l -o temp.txt D:\\\\kotlinTask\\InputFiles")
        assertFileContent("temp.txt",
            "1.txt 111 2019-06-02T21:12:21.101309Z 905\n" +
                    "10.txt 111 2019-04-12T00:14:41.193967Z 0\n" +
                    "12.txt 111 2019-04-12T00:14:56.901865Z 0\n" +
                    "2.txt 111 2019-06-02T21:12:26.465616Z 4\n" +
                    "20.txt 111 2019-06-02T21:09:41.072156Z 3\n" +
                    "21.txt 111 2019-06-02T21:09:35.406832Z 53\n" +
                    "3.txt 111 2019-04-12T00:14:33.525528Z 0\n" +
                    "4.txt 111 2019-04-12T00:14:36.769714Z 0\n" +
                    "videoplayback.mp4 111 2019-04-12T00:44:40.681892Z 745402")


    }
}