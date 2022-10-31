package com.company.Controller;

public class Patterns {
    public static final String DECIMAL_LITERAL = "\\b([0-9]*[.]?[0-9]+([eE][-+]?[0-9]+)?)";

    public static final String BOOL_LITERAL = "(?<!\\w)(false|true)(?!\\w)";

    public static final String DELIMITER = "[(){};,]";

    public static final String STRING_LITERAL = "\"(?:\\\\\"|[^\"])*?\"";

    public static final String COMMENT = "//.*|(\"(?:\\\\[^\"]|\\\\\"|.)*?\")|(?s)/\\*.*?\\*/";

    public static final String KEYWORD = "\\bif|\\bint|\\blong|\\bfloat|\\bdouble|\\bchar|\\bfor|\\belse|\\bprivate" +
            "|\\bpublic|\\bstatic|\\bvoid|\\bvolatile|\\bimport|\\bpackage|\\bthis|\\binstanceof|\\bnew|\\bclass|\\btry" +
            "|\\bcatch";

    public static final String IDENTIFIER = "\\b(?!" + KEYWORD + "|" + STRING_LITERAL + ")[_A-Za-z][0-9A-Za-z_]*";

    public static final String CHAR = "'(\\\\['\"tvrnafb\\\\]|[^'\\\\])'";

    public static final String OPERATORS = "<(?!=)|\\.|>(?!=)|<=|>=|!=|(?<![<>!=+-/%&^|])=|==|!(?!=)|\\+(?!\\+)|-(?!-)|\\*|/|%|\\+\\+|--|&&|(?!&)&|~|<<|>>(?!>)|>>>|\\[|]";
}
