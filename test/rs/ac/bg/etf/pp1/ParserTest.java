package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;
import org.junit.Assert;
import org.junit.Test;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.etf.pp1.symboltable.Tab;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ParserTest {

    @Test
    public void test() {
        File source = new File("test/simple.mj");

        Assert.assertTrue(source.getAbsolutePath(), source.exists());

        try (FileReader fileReader = new FileReader(source)) {
            Yylex lexer = new Yylex(fileReader);
            MJParser parser = new MJParser(lexer);

            Symbol s = parser.parse();
            SyntaxNode prog = (SyntaxNode) (s.value);
            //Tab.init();
            RuleVisitor visitor = new RuleVisitor();
            prog.traverseBottomUp(visitor);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
