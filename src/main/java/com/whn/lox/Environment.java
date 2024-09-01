package com.whn.lox;

import java.util.HashMap;
import java.util.Map;

/**
 * 变量环境类
 */
public class Environment {

    final Environment enclosing;

    private final Map<String, Object> values = new HashMap<>();

    Environment() {
        enclosing = null;
    }

    Environment(Environment enclosing) {
        this.enclosing = enclosing;
    }

    /**
     * 将定义的变量置入 Map 中
     * @param name
     * @param value
     */
    void define(String name, Object value) {
        values.put(name, value);
    }

    /**
     * 从 Map 中获取变量的值
     * @param name
     * @return
     */
    Object get(Token name) {
        // 先从当前 scope 获取变量值
        if (values.containsKey(name.lexeme)) {
            return values.get(name.lexeme);
        }

        // 如果当前 scope 不存在，则从封闭 scope 获取
        if (enclosing != null) {
            return enclosing.get(name);
        }

        throw new RuntimeError(name,
                "Undefined variable '" + name.lexeme + "'.");
    }

    /**
     * 对已存在的变量重新赋值
     * @param name
     * @param value
     */
    void assign(Token name, Object value) {
        if (values.containsKey(name.lexeme)) {
            values.put(name.lexeme, value);
            return;
        }

        if (enclosing != null) {
            enclosing.assign(name, value);
            return;
        }

        throw new RuntimeError(name,
                "Undefined variable '" + name.lexeme + "'.");
    }
}
