# Bash-Sub-Shell
Example of a parser for a simple bash script recursive decent parser.  Grammar conforms to problem 4.10 from Programming Language Processors in Java by Watt and Brown.

### Grammar for a subset of the Bash shell scripting language
```
  Script  -> Command*
  Command -> Filename Argument* eol
           | Variable = Argument* eol
           | if Filename Arugment* then eol
                Command*
             else eol
                Command*
             fi eol
           | for Variable in Argument* eol
                 do
                   Command*
                 od eol
  Argument -> Filename | Literal | Variable
  Filename -> cat | ls | pwd | touch | cp | mv |n rm | chmod | man | ps | bg
  Variable -> Letter(Letter | Digit | _)*
  Literal -> -(ε|-)(Letter | Digit)* | Digit*
``` 
Since Filename &#8834 Variable, we will require that no variable be named the same as a Filename.
