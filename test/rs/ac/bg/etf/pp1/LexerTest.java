package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;

public class LexerTest {

    @Test public void test1() {
        Reader br = null;
        try {
            File sourceCode = new File("test/first.mj");
            System.out.println("Compiling source file: " + sourceCode.getAbsolutePath());

            br = new BufferedReader(new FileReader(sourceCode));

            Yylex lexer = new Yylex(br);
            Symbol currToken = null;
            while ((currToken = lexer.next_token()).sym != sym.EOF) {
                if (currToken != null)
                    System.out.println(currToken.toString() + " " + currToken.value.toString());
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

}
