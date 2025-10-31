package uj.wmii.pwj.collections;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

public class BrainfuckInterpreter implements Brainfuck{
    int stack;
    int stackSize;
    List<Integer> Nums;
    int index;
    static String program;
    InputStream in;
    PrintStream out;
    Map <Character,Runnable> commands = new HashMap();

    BrainfuckInterpreter(String program, PrintStream out, InputStream in, int stackSize) {
        this.stackSize = stackSize;
        this.stack = 0;
        this.index = 0;
        this.out = out;
        this.in = in;
        this.Nums = new ArrayList();
        Nums.add(0);

        BrainfuckInterpreter.program = program;

        this.commands.put('>',() -> {index++; if (index == Nums.size()) Nums.add(0);});
        this.commands.put('<',() -> index--);
        this.commands.put('+',() -> Nums.set(index, Nums.get(index)+1));
        this.commands.put('-',() -> Nums.set(index, Nums.get(index)-1));
        this.commands.put('.',() -> out.print((char) Nums.get(index).intValue()));
        this.commands.put(',',() -> {
            try {
                Nums.set(index,in.read());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void execute(){
        program = program.replaceAll("[^><+\\-.,\\[\\]]","");

        for (int i = 0; i < program.length(); i++)
            if (Nums.size() > stackSize)
                break;
            else if (program.charAt(i) == '['){
                if (Nums.get(index) == 0){
                    int bracketCount = 0;
                    while (true){ i++;
                    if (program.charAt(i) == '[') bracketCount++;
                    else if (program.charAt(i) == ']') if (bracketCount == 0)
                        break;
                    else
                        bracketCount--;
                }}}
        else if (program.charAt(i) == ']'){
            if (Nums.get(index) != 0) {
                int bracketCount = 0;
                while (true){ i--;
                    if (program.charAt(i) == ']') bracketCount++;
                    else if (program.charAt(i) == '[') if (bracketCount == 0)
                        break;
                    else
                        bracketCount--;
                }
            }
            }
        else if (commands.get(program.charAt(i))!= null)
                commands.get(program.charAt(i)).run();

    }



    static Brainfuck createInstance(String program, PrintStream out, InputStream in, int stackSize) {
        return new BrainfuckInterpreter(program, out, in, stackSize);
    }


}
