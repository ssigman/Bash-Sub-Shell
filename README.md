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
                 do eol
                   Command*
                 od eol
  Argument -> Filename | Literal | Variable
  Filename -> cat | ls | pwd | touch | cp | mv | rm | chmod | man | ps | bg | mkdir | test | cd
  Variable -> Letter(Letter | Digit | _ | .)*
  Literal -> -(ε|-)(Letter | Digit)* | Digit*
``` 
Since Filename &#8834; Variable, we will require that no variable be named the same as a Filename.  We can follow the Watt's and Brown's advice for handling the keywords from Filename.  The lexical scanner should identify any terminal that matches a Variable and create a Token for a Variable. The constructor for Token should check the spelling of the Variable and if it is a member of Filename, change the kind of the token to the approptiate value.  *See* pages 123 and 124 for a discussion of this strategy.  
