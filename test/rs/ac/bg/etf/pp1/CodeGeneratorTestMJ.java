package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.etf.pp1.mj.runtime.Code;

import java.io.*;

public class CodeGeneratorTestMJ {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void generateCode() {
        File source = new File("test/method/methodSE8.mj");

        Assert.assertTrue(source.getAbsolutePath(), source.exists());

        try (FileReader fileReader = new FileReader(source)) {
            Yylex lexer = new Yylex(fileReader);
            MJParser parser = new MJParser(lexer);

            Symbol s = parser.parse();
            SyntaxNode prog = (SyntaxNode) (s.value);

            SemanticAnalyzer semanticAnalyzerVisitor = new SemanticAnalyzer();
            prog.traverseBottomUp(semanticAnalyzerVisitor);

            File objFile = new File("test.obj");

            CodeGenerator codeGenerator = new CodeGenerator();
            prog.traverseBottomUp(codeGenerator);

            Code.write(new FileOutputStream(objFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
