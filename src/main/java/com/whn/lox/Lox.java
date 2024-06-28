package com.whn.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox {

    static boolean hadError = false;

    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: jlox [script]");
            System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    /**
     * 通过代码文件运行
     * @param path
     * @throws IOException
     */
    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));

        // 在退出码中表明有error
        if (hadError) System.exit(65);
    }

    /**
     * 通过终端输入运行
     * @throws IOException
     */
    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            System.out.println("> ");
            String line = reader.readLine();
            if (line == null) return;
            run(line);
            hadError = false;
        }
    }

    /**
     * 解析代码至Token列表
     * @param source
     */
    private static void run(String source) {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        // For now, just print the tokens
        for (Token token :
                tokens) {
            System.out.println(token);
        }
    }

    /**
     * 报告error
     * @param line
     * @param message
     */
    static void error(int line, String message) {
        report(line, "", message);
    }

    /**
     * 报告编译出错的行位置及信息
     * @param line
     * @param where
     * @param message
     */
    private static void report(int line, String where,
                               String message) {
        System.err.println(
                "[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }


}
