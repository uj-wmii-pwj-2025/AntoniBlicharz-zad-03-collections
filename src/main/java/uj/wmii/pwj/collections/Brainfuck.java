package uj.wmii.pwj.collections;

import java.io.InputStream;
import java.io.PrintStream;

public interface Brainfuck {

    /**
     * Executes uploaded program.
     */
    void execute();

    /**
     * Creates a new instance of Brainfuck interpreter with given program, using standard IO and stack of 1024 size.
     * @param program brainfuck program to interpret
     * @return new instance of the interpreter
     * @throws IllegalArgumentException if program is null or empty
     */
    static Brainfuck createInstance(String program) {
        if (program == null)
            throw new IllegalArgumentException("Program cannot be null");

        return createInstance(program, System.out, System.in, 1024);
    }

    /**
     * Creates a new instance of Brainfuck interpreter with given parameters.
     * @param program brainfuck program to interpret
     * @param out output stream to be used by interpreter implementation
     * @param in input stream to be used by interpreter implementation
     * @param stackSize maximum stack size, that is allowed for this interpreter
     * @return new instance of the interpreter
     * @throws IllegalArgumentException if: program is null or empty, OR out is null, OR in is null, OR stackSize is below 1.
     */
    static Brainfuck createInstance(String program, PrintStream out, InputStream in, int stackSize) {
        if (stackSize <= 0)
            throw new IllegalArgumentException("Stack size must be greater than 0");
        if (in == null)
            throw new IllegalArgumentException("Input cannot be null");
        if (out == null)
            throw new IllegalArgumentException("Output cannot be null");
        return BrainfuckInterpreter.createInstance(program, out, in, stackSize);
    }

}
