package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;
import org.junit.Assert;
import org.junit.Test;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.etf.pp1.symboltable.Tab;

import java.io.File;
import java.io.FileReader;

public class ParserTestMJ {

    @Test
    public void testSimple() {
        File source = new File("test/method/methodCorrect5.mj");

        Assert.assertTrue(source.getAbsolutePath(), source.exists());

        try (FileReader fileReader = new FileReader(source)) {
            Yylex lexer = new Yylex(fileReader);
            MJParser parser = new MJParser(lexer);

            Symbol s = parser.parse();
            SyntaxNode prog = (SyntaxNode) (s.value);

            SemanticAnalyzer visitor = new SemanticAnalyzer();
            prog.traverseBottomUp(visitor);

            Tab.dump();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
