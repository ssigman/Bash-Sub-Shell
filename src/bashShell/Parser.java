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

    }

    private void parseCommand() {

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
