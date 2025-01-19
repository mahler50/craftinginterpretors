package com.whn.lox;

import java.util.List;

public interface LoxCallable {

    // return number of arguments this callable take
    int arity();

    // call this callable
    Object call(Interpreter interpreter, List<Object> arguments);
}
