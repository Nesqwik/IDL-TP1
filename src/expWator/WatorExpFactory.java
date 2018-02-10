package expWator;

import core.misc.Environment;

/**
 * Création des agents SymbolAgent
 */
public class WatorExpFactory {

    /**
     * Crée un objet SymbolAgent
     * 
     * @param env
     * @param x
     * @param y
     * @param symbol
     * @return l'agent symbole
     */
    public static SymbolAgent newSymbol(Environment env, int x, int y, int symbol) {
        return new SymbolAgent(env, x, y, symbol);
    }
}
