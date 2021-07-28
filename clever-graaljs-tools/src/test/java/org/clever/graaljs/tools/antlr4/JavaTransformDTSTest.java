package org.clever.graaljs.tools.antlr4;

import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.io.FileUtils;
import org.clever.graaljs.tools.antlr4.java.JavaLexer;
import org.clever.graaljs.tools.antlr4.java.JavaParser;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/26 14:03 <br/>
 */
@Slf4j
public class JavaTransformDTSTest {
    public void generateDTS(String javaPath, String tsPath) throws IOException {
        final String code = FileUtils.readFileToString(new File(javaPath), StandardCharsets.UTF_8);
        final long startTime = System.currentTimeMillis();
        CharStream charStream = CharStreams.fromString(code);
        JavaLexer lexer = new JavaLexer(charStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        JavaParser parser = new JavaParser(tokenStream);
        ParseTree tree = parser.compilationUnit();
        ParseTreeWalker walker = new ParseTreeWalker();
        JavaTransformDTS transform = new JavaTransformDTS(lexer, tokenStream, parser);
        walker.walk(transform, tree);
        final long endTime = System.currentTimeMillis();
        log.info("耗时：{}ms\n\n\n{}\n\n", endTime - startTime, transform.getDTSCode());
        FileUtils.writeStringToFile(new File(tsPath), transform.getDTSCode(), StandardCharsets.UTF_8);
    }

    @Test
    public void t01() throws IOException {
        String javaPath = "../clever-graaljs-data-jdbc/src/main/java/org/clever/graaljs/data/jdbc/builtin/wrap/SQLBuilder.java";
        String tsPath = "../clever-graaljs-types/src/fast-api/data-jdbc/SQLBuilder.d.ts";
        // TypeMappingUtils.addMapping("Map<String, Object>", "JMap<JString, any>");
        TypeMappingUtils.addMapping("Map<String, Object>", "SqlParamMap");
        generateDTS(javaPath, tsPath);
    }
}
