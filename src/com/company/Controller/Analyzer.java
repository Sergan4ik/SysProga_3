package com.company.Controller;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analyzer {

    public record PatternWithName(Pattern pattern, String name) {
    }

    public record Token(Integer start, String value, String type) {
    }

    public void analyze(String text, List<PatternWithName> patterns) throws IOException {
        CheckForErrors(text);

        text = text.replaceAll(Patterns.COMMENT, " ");

        List<Token> tokens = new ArrayList<>();
        boolean[] matched = new boolean[text.length()];

        for (PatternWithName pattern : patterns) {
            Matcher m = pattern.pattern.matcher(text);
            while (m.find()) {
                if (match(matched, m.start(), m.end() - 1)) {
                    tokens.add(new Token(m.start(), m.group(0), pattern.name));
                }
            }
        }

        tokens.sort(Comparator.comparingInt(t -> t.start));
        tokens.forEach(token -> PrintToken(token));

        String fileName = "result.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        tokens.forEach(token -> {
            try {
                TokenToFile(token , writer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        writer.close();
    }

    private void CheckForErrors(String text) throws IOException {
        BufferedReader bufReader = new BufferedReader(new StringReader(text));
        String line = null;
        int lineNum = 0;
        while( (line=bufReader.readLine()) != null ) {
            lineNum++;

            if (line.length() == 0)
                continue;
            if (line.chars().filter(c -> c == '/').count() == 2)
                continue;

            if ((line.charAt(line.length() - 1) != '{' && line.charAt(line.length() - 1) != '}') &&  line.charAt(line.length() - 1) !=';'){
                System.out.println("Error in " + lineNum + " line , expression doesn't contain end delimiter ;");
            }

            var words = line.split(" ");
            for (String word: words) {
                if (word.length() == 0)
                    continue;
                char s = word.charAt(0);
                if (!(s >= '0' && s <= '9'))
                    continue;
                for (var c : word.toCharArray()) {
                    if (c >= '0' && c <= '9')
                        continue;
                    System.out.println("Error in " + lineNum + " line , numeric word \"" + word + "\" contains symbols");
                    break;
                }
            }

            int bracketsCount = 0;
            for (char a : line.toCharArray()) {
                if (bracketsCount == -1){
                    System.out.println("Error in " + lineNum + " line , brackets error");
                    bracketsCount = 0;
                    break;
                }
                if (a == '(')
                    bracketsCount++;
                if (a == ')')
                    bracketsCount--;
            }
            if (bracketsCount != 0){
                System.out.println("Error in " + lineNum + " line , brackets error");
            }

        }
    }

    private static void PrintToken(Token token) {
        System.out.println(token.value + " - " + token.type);
    }
    private static void TokenToFile(Token token , BufferedWriter writer) throws IOException {
        writer.write(token.value + " - " + token.type + "\n");
    }

    private boolean match(boolean[] matched, int l, int r) {
        assert l <= r;
        for (int i = l; i <= r; i++) {
            if (matched[i]) {
                return false;
            }
            matched[i] = true;
        }
        return true;
    }


}