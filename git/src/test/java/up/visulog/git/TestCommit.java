/*
	22015094 - Idil Saglam
*/
package up.visulog.git;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;

public class TestCommit {
    @Test
    public void testParseCommit() throws IOException, URISyntaxException {
        var expected =
                "Extractor{id='6304c1acdc1cbdeb8315528781896abc72a021b8', date='Tue Sep 1 12:30:53"
                    + " 2020 +0200', author='Aldric Degorre <adegorre@irif.fr>', description='More"
                    + " gradle configuration (with subprojects)'}";
        var uri = getClass().getClassLoader().getResource("git.log").toURI();
        try (var reader = Files.newBufferedReader(Paths.get(uri))) {
            var commit = Extractor.parseCommit(reader);
            assertTrue(commit.isPresent());
            assertEquals(expected, commit.get().toString());
        }
    }

    @Test
    public void testParseLog() throws IOException, URISyntaxException {
        var expected =
                "[Extractor{id='6304c1acdc1cbdeb8315528781896abc72a021b8', date='Tue Sep 1 12:30:53"
                    + " 2020 +0200', author='Aldric Degorre <adegorre@irif.fr>', description='More"
                    + " gradle configuration (with subprojects)'},"
                    + " Extractor{id='c0cf37d6b32897677e4b8f04be012e5379a7ab80', date='Thu Aug 27"
                    + " 23:49:03 2020 +0200', author='Aldric Degorre <adegorre@irif.fr>',"
                    + " description='first setup of project modules and gradle configuration'},"
                    + " Extractor{id='9e74f1581f23aaad21e2b936091d3ce371336e22', date='Mon Aug 31"
                    + " 11:28:28 2020 +0200', author='Aldric Degorre <adegorre@irif.fr>',"
                    + " description='Update README.md - more modules'},"
                    + " Extractor{id='7484b0cb7b4e69e09c82ed38549750fa2a77f50c', date='Thu Aug 27"
                    + " 00:35:19 2020 +0200', author='Aldric Degorre <adegorre@irif.fr>',"
                    + " description='Update README.md - translation...'},"
                    + " Extractor{id='9aaf6e09cc30909b32c68b4d5bf4ac50f95ecb93', date='Thu Aug 27"
                    + " 00:33:46 2020 +0200', author='Aldric Degorre <adegorre@irif.fr>',"
                    + " description='Update README.md - some title left untranslated'},"
                    + " Extractor{id='969e2247156f27f27fec57b13faf6097bf4e2757', date='Thu Aug 27"
                    + " 00:32:47 2020 +0200', author='Aldric Degorre <adegorre@irif.fr>',"
                    + " description='Update README.md -> in English, with some more details'},"
                    + " Extractor{id='486d76dbfd24ac65eeeeb16e57ae4fd68c8ecb1c', date='Thu Aug 27"
                    + " 00:02:55 2020 +0200', author='Aldric Degorre <adegorre@irif.fr>',"
                    + " description='Ajout de README.md avec définition des grandes lignes du"
                    + " sujet.'}]";
        var uri = getClass().getClassLoader().getResource("git.log").toURI();
        try (var reader = Files.newBufferedReader(Paths.get(uri))) {
            var log = Extractor.parseLog(reader);
            //            System.out.println(log);
            assertEquals(expected, log.toString());
        }
    }
}
