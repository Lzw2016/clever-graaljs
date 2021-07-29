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

    @Test
    public void t02() throws IOException {
        String javaPath = "./src/test/resources/java/lang/StringBuilder.java";
        String tsPath = "../clever-graaljs-types/src/java/lang/JStringBuilder.d.ts";
        TypeMappingUtils.addMapping("StringBuilder", "JStringBuilder");
        generateDTS(javaPath, tsPath);

        // javaPath = "./src/test/resources/java/lang/AbstractStringBuilder.java";
        // tsPath = "../clever-graaljs-types/src/java/lang/JAbstractStringBuilder.d.ts";
        // TypeMappingUtils.addMapping("AbstractStringBuilder", "JAbstractStringBuilder");
        // generateDTS(javaPath, tsPath);
    }

    @Test
    public void t03() throws IOException {
        String javaPath = "./src/test/resources/java/time/Duration.java";
        String tsPath = "../clever-graaljs-types/src/java/time/JDuration.d.ts";
        TypeMappingUtils.addMapping("Duration", "JDuration");
        generateDTS(javaPath, tsPath);
    }

    @Test
    public void t04() throws IOException {
        String javaPath = "./src/test/resources/java/time/Instant.java";
        String tsPath = "../clever-graaljs-types/src/java/time/JInstant.d.ts";
        TypeMappingUtils.addMapping("Instant", "JInstant");
        generateDTS(javaPath, tsPath);
    }

    @Test
    public void t05() throws IOException {
        String javaPath = "./src/test/resources/java/time/LocalDate.java";
        String tsPath = "../clever-graaljs-types/src/java/time/JLocalDate.d.ts";
        TypeMappingUtils.addMapping("LocalDate", "JLocalDate");
        generateDTS(javaPath, tsPath);
    }

    @Test
    public void t06() throws IOException {
        String javaPath = "./src/test/resources/java/time/LocalTime.java";
        String tsPath = "../clever-graaljs-types/src/java/time/JLocalTime.d.ts";
        TypeMappingUtils.addMapping("LocalTime", "JLocalTime");
        generateDTS(javaPath, tsPath);
    }

    @Test
    public void t07() throws IOException {
        String javaPath = "./src/test/resources/java/time/ZoneId.java";
        String tsPath = "../clever-graaljs-types/src/java/time/JZoneId.d.ts";
        TypeMappingUtils.addMapping("ZoneId", "JZoneId");
        generateDTS(javaPath, tsPath);
    }

    @Test
    public void t08() throws IOException {
        String javaPath = "./src/test/resources/java/lang/Thread.java";
        String tsPath = "../clever-graaljs-types/src/java/lang/JThread.d.ts";
        TypeMappingUtils.addMapping("Thread", "JThread");
        generateDTS(javaPath, tsPath);
    }

    @Test
    public void t09() throws IOException {
        String javaPath = "../clever-graaljs-core/src/main/java/org/clever/graaljs/core/builtin/AssertUtils.java";
        String tsPath = "../clever-graaljs-types/src/fast-api/core/AssertUtils.d.ts";
        TypeMappingUtils.addMapping("", "");
        generateDTS(javaPath, tsPath);
    }

    @Test
    public void t10() throws IOException {
        String javaPath = "../clever-graaljs-core/src/main/java/org/clever/graaljs/core/builtin/CommonUtils.java";
        String tsPath = "../clever-graaljs-types/src/fast-api/core/CommonUtils.d.ts";
        TypeMappingUtils.addMapping("", "");
        generateDTS(javaPath, tsPath);
    }
}
