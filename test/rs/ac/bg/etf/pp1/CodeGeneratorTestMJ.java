package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

public class CodeGeneratorTestMJ {

    @Rule public TemporaryFolder folder = new TemporaryFolder();

    @Test public void generateCode() {
        File source = new File("test/variable/variableER1.mj");

        Assert.assertTrue(source.getAbsolutePath(), source.exists());

        try (FileReader fileReader = new FileReader(source)) {
            Yylex lexer = new Yylex(fileReader);
            MJParser parser = new MJParser(lexer);

            Symbol s = parser.parse();
            SyntaxNode prog = (SyntaxNode) (s.value);

            SemanticAnalyzer semanticAnalyzerVisitor = new SemanticAnalyzer();
            prog.traverseBottomUp(semanticAnalyzerVisitor);
            //Tab.dump();
            if (!semanticAnalyzerVisitor.errorFound) {
                //System.out.println(semanticAnalyzerVisitor.getSyntaxTree());
                File objFile = new File("test.obj");

                CodeGenerator codeGenerator = new CodeGenerator();
                prog.traverseBottomUp(codeGenerator);

                Code.write(new FileOutputStream(objFile));
            } else {
                System.out.println("THERE WERE SOME ERRORS!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
