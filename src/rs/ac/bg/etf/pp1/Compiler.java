package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

public class Compiler {

    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Program needs 2 arguments");
        }

        String source = args[0];
        String dest = args[1];

        try (FileReader fileReader = new FileReader(source)) {
            Yylex lexer = new Yylex(fileReader);
            MJParser parser = new MJParser(lexer);

            Symbol s = parser.parse();
            SyntaxNode prog = (SyntaxNode) (s.value);

            SemanticAnalyzer semanticAnalyzerVisitor = new SemanticAnalyzer();
            prog.traverseBottomUp(semanticAnalyzerVisitor);
            Tab.dump();
            if (!semanticAnalyzerVisitor.errorFound) {
                System.out.println(semanticAnalyzerVisitor.getSyntaxTree());
                File objFile = new File(dest);

                CodeGenerator codeGenerator = new CodeGenerator();
                prog.traverseBottomUp(codeGenerator);

                Code.write(new FileOutputStream(objFile));
                System.out.println("Compilation successful!");
            } else {
                System.out.println("THERE WERE SOME ERRORS!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
