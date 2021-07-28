package org.clever.graaljs.tools.antlr4;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.apache.commons.lang3.StringUtils;
import org.clever.graaljs.tools.antlr4.java.JavaLexer;
import org.clever.graaljs.tools.antlr4.java.JavaParser;
import org.clever.graaljs.tools.antlr4.java.JavaParserBaseListener;

import java.util.*;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/07/26 13:55 <br/>
 */
public class JavaTransformDTS extends JavaParserBaseListener {
    private final JavaLexer lexer;
    private final CommonTokenStream tokenStream;
    private final JavaParser parser;
    // private ParseTreeProperty<String>
    /**
     * 代码注释
     */
    private final List<String> comments = new ArrayList<>();
    /**
     * 代码注释，最后一个Token位置
     */
    private int commentLastTokenIndex = -1;
    /**
     * class生成的代码
     */
    private final Map<String, StringBuilder> classCodeMap = new LinkedHashMap<>();
    /**
     * 生成的代码
     */
    private StringBuilder code;
    /**
     * 缩进层级
     */
    private int indentLevel = 0;
    /**
     * 是否是构造函数
     */
    private boolean isConstructorMethod = false;

    public JavaTransformDTS(JavaLexer lexer, CommonTokenStream tokenStream, JavaParser parser) {
        this.lexer = lexer;
        this.tokenStream = tokenStream;
        this.parser = parser;
    }

    public String getDTSCode() {
        final StringBuilder code = new StringBuilder();
        classCodeMap.values().forEach(sb -> code.append(sb).append("\n"));
        return code.toString();
    }

    private void appendIndent() {
        for (int i = 0; i < indentLevel; i++) {
            code.append("    ");
        }
    }

    public void collectComments(ParserRuleContext ctx) {
        if (Objects.equals(ctx.getStart().getTokenIndex(), commentLastTokenIndex)) {
            return;
        }
        commentLastTokenIndex = ctx.getStart().getTokenIndex();
        final StringBuilder comment = new StringBuilder();
        final List<Token> tokens = tokenStream.getHiddenTokensToLeft(commentLastTokenIndex, JavaLexer.HIDDEN);
        tokens.forEach(token -> comment.append(token.getText()));
        comments.add(comment.toString());
    }

    public void appendComments() {
        final StringBuilder allComment = new StringBuilder();
        comments.forEach(allComment::append);
        final String[] commentArr = StringUtils.trim(allComment.toString()).split("\n");
        for (String comment : commentArr) {
            if (StringUtils.isBlank(comment)) {
                continue;
            }
            appendIndent();
            comment = StringUtils.trim(comment);
            if (comment.startsWith("*")) {
                code.append(" ");
            }
            code.append(comment).append("\n");
        }
        comments.clear();
    }

    @Override
    public void enterClassOrInterfaceModifier(JavaParser.ClassOrInterfaceModifierContext ctx) {
        collectComments(ctx);
    }

    @Override
    public void exitTypeDeclaration(JavaParser.TypeDeclarationContext ctx) {
        comments.clear();
    }

    @Override
    public void enterClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
        final String className = ctx.IDENTIFIER().getSymbol().getText();
        code = classCodeMap.computeIfAbsent(className, clsName -> new StringBuilder());
        appendComments();
        appendIndent();
        // indentLevel++;
        code.append("interface ").append(className).append(" extends JObject {\n");
    }

    @Override
    public void exitClassDeclaration(JavaParser.ClassDeclarationContext ctx) {
        final String className = ctx.IDENTIFIER().getSymbol().getText();
        code = classCodeMap.get(className);
        // indentLevel--;
        appendIndent();
        code.append("}\n");
        code = null;
    }

    @Override
    public void enterGenericConstructorDeclaration(JavaParser.GenericConstructorDeclarationContext ctx) {
        isConstructorMethod = true;
    }

    @Override
    public void exitGenericConstructorDeclaration(JavaParser.GenericConstructorDeclarationContext ctx) {
        isConstructorMethod = false;
    }

    @Override
    public void enterConstructorDeclaration(JavaParser.ConstructorDeclarationContext ctx) {
        isConstructorMethod = true;
    }

    @Override
    public void exitConstructorDeclaration(JavaParser.ConstructorDeclarationContext ctx) {
        isConstructorMethod = false;
    }

    @Override
    public void enterModifier(JavaParser.ModifierContext ctx) {
        collectComments(ctx);
    }

    @Override
    public void exitMemberDeclaration(JavaParser.MemberDeclarationContext ctx) {
        comments.clear();
    }

    @Override
    public void enterMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
        indentLevel = 1;
        appendComments();
        appendIndent();
        final String methodName = ctx.IDENTIFIER().getSymbol().getText();
        code.append(methodName);
    }

    @Override
    public void exitMethodDeclaration(JavaParser.MethodDeclarationContext ctx) {
        indentLevel = 0;
        final TokenStream tokenStream = parser.getTokenStream();
        String returnType = "void";
        if (ctx.typeTypeOrVoid() != null) {
            returnType = tokenStream.getText(ctx.typeTypeOrVoid());
        }
        code.append(": ").append(TypeMappingUtils.getType(returnType)).append(";\n");
    }

    @Override
    public void enterFormalParameters(JavaParser.FormalParametersContext ctx) {
        if (isConstructorMethod) {
            return;
        }
        code.append("(");
    }

    @Override
    public void exitFormalParameters(JavaParser.FormalParametersContext ctx) {
        if (isConstructorMethod) {
            return;
        }
        code.append(")");
    }

    @Override
    public void enterFormalParameter(JavaParser.FormalParameterContext ctx) {
        final TokenStream tokenStream = parser.getTokenStream();
        final String type = TypeMappingUtils.getType(tokenStream.getText(ctx.typeType()));
        final String variable = tokenStream.getText(ctx.variableDeclaratorId());
        if (code.charAt(code.length() - 1) != '(') {
            code.append(", ");
        }
        code.append(variable).append(": ").append(type);
    }

    @Override
    public void enterLastFormalParameter(JavaParser.LastFormalParameterContext ctx) {
        final TokenStream tokenStream = parser.getTokenStream();
        final String type = TypeMappingUtils.getType(tokenStream.getText(ctx.typeType()));
        final String variable = tokenStream.getText(ctx.variableDeclaratorId()) + "[]";
        if (code.charAt(code.length() - 1) != '(') {
            code.append(", ");
        }
        code.append("...").append(variable).append(": ").append(type);
    }
}
