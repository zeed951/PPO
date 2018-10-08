package Route_GPX;

import java.util.Stack;

public class CmdManager {
    private Stack<Command> doneStack = new Stack<Command>();
    private Stack<Command> undoneStack = new Stack<Command>();

    public Stack<Command> getDoneStack() {
        return doneStack;
    }

    public Stack<Command> getUndoneStack() {
        return undoneStack;
    }

    public boolean add(Command cmd) {
        if (cmd == null) {
            return false;
        }
        if (cmd.execute() == false) {
            return false;
        }
        if (doneStack.add(cmd) == false) {
            return false;
        }
        undoneStack.clear();
        return true;
    }

    public boolean undo() {
        Command cmd = doneStack.pop();
        if (cmd == null) {
            return false;
        }
        if (cmd.cancel() == false) {
            return false;
        }
        if (undoneStack.add(cmd) == false) {
            return false;
        }
        return true;
    }

    public boolean redo() {
        Command cmd = undoneStack.pop();
        if (cmd == null) {
            return false;
        }
        if (cmd.execute() == false) {
            return false;
        }
        if (doneStack.add(cmd) == false) {
            return false;
        }
        return true;
    }
}
