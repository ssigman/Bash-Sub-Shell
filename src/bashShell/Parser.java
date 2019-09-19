package bashShell;

public class Parser {
    private Token currentToken = null;
    private Scanner scanner = null;

    //------------- Utility Methods -------------

    /**
     * Accept a specified token if it matches the
     * current Token.  Acceptance entails setting
     * currentToken to the next token in the input
     * stream.
     *
     * @param expectedKind The expected type of token.
     */
    private void accept(byte expectedKind) {
        if (currentToken.kind == expectedKind)
            currentToken = scanner.scan();
        else
            writeError("Expected:  " + Token.kindString(expectedKind) +
                       "Found :" + Token.kindString(currentToken.kind));
    }

    /**
     * Accept the current token by setting currentToken
     * to the next token in the input stream.
     */
    private void acceptIt() {
        currentToken = scanner.scan();
    }

    private void writeError(String s) {
    }

    //---------------- Parsing Methods ---------------
    private void parseScript() {
         while (currentToken.kind == Token.FName
                || currentToken.kind == Token.VAR
                || currentToken.kind == Token.IF
                 || currentToken.kind == Token.FOR)
             parseCommand();
    }

    private void parseCommand() {
        switch (currentToken.kind) {
            case Token.FName: {
                acceptIt();
                //parseFileName();
                while (currentToken.kind == Token.FName
                      || currentToken.kind == Token.LIT
                      || currentToken.kind == Token.VAR)
                    parseArgument();
                accept(Token.EOL);
            }
            case Token.VAR: {
                acceptIt();
                accept(Token.ASSIGN);
                parseArgument();
                accept(Token.EOL);
            }
        }
    }

    private void parseArgument() {
    }

    private void parseFileName() {

    }

    private void parseLiteral() {

    }

    private void parseVariable() {

    }
}
