package expWator;

import core.misc.Environment;

public class WatorExpFactory {

    public static SymbolAgent newSymbol(Environment env, int x, int y, int symbol) {
        return new SymbolAgent(env, x, y, symbol);
    }
}
