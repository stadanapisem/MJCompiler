package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.etf.pp1.symboltable.Tab;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(Parameterized.class) public class SemanticErrorTest {

    @Parameterized.Parameter(0) public String filePath;

    @Parameterized.Parameters public static Object[] data() throws Exception {
        List constants =
            Files.walk(Paths.get("test", "constant")).filter(tmp -> tmp.toString().endsWith(".mj"))
                .filter(tmp -> tmp.toString().contains("SE")).map(tmp -> tmp.toString())
                .collect(Collectors.toList());

        constants.addAll(
            Files.walk(Paths.get("test", "variable")).filter(tmp -> tmp.toString().endsWith(".mj"))
                .filter(tmp -> tmp.toString().contains("SE")).map(tmp -> tmp.toString())
                .collect(Collectors.toList()));

        constants.addAll(
            Files.walk(Paths.get("test", "method")).filter(tmp -> tmp.toString().endsWith(".mj"))
                .filter(tmp -> tmp.toString().contains("SE")).map(tmp -> tmp.toString())
                .collect(Collectors.toList()));

        return constants.toArray();
    }


    @Test(expected = Error.class) public void testSimple() throws Exception {
        File source = new File(filePath);

        Assert.assertTrue(source.getAbsolutePath(), source.exists());

        try (FileReader fileReader = new FileReader(source)) {
            Yylex lexer = new Yylex(fileReader);
            MJParser parser = new MJParser(lexer);

            Symbol s = parser.parse();
            SyntaxNode prog = (SyntaxNode) (s.value);

            SemanticAnalyzer visitor = new SemanticAnalyzer();
            prog.traverseBottomUp(visitor);

            Tab.dump();
        }

    }
}
