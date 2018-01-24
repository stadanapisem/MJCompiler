package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.Test;
import rs.ac.bg.etf.pp1.util.Log4JUtils;

import java.io.*;

public class TestMJ {

    static {
        DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
        Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
    }

    @Test
    public void main() throws IOException {
        Logger log = Logger.getLogger(TestMJ.class);
        Reader br = null;
        try {

            File sourceCode = new File("test/program.mj");
            log.info("Compiling source file: " + sourceCode.getAbsolutePath());

            br = new BufferedReader(new FileReader(sourceCode));

            Yylex lexer = new Yylex(br);
            Symbol currToken = null;
            while ((currToken = lexer.next_token()).sym != sym.EOF) {
                if (currToken != null && currToken.value != null)
                    log.info(currToken.toString() + " " + currToken.value.toString());
            }
        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e1) {
                    log.error(e1.getMessage(), e1);
                }
        }
    }

}