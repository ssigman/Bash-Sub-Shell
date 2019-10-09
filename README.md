# Bash-Sub-Shell
Example of a parser for a simple bash script recursive decent parser.  Grammar conforms to problem 4.10 from Programming Language Processors in Java by Watt and Brown.

### Grammar for a subset of the Bash shell scripting language
```
  Script  -> Command*                        ( Script )
  Command -> Filename Argument* eol          ( Exec-Cmd )
           | Variable = Argument eol         ( Assign-Cmd )
           | if Filename Arugment* then eol  ( if-Cmd )
                Command*
             else eol
                Command*
             fi eol
           | for Variable in Argument* eol   ( for-Cmd )
                 do eol
                   Command*
                 od eol
  Argument -> Filename                       ( FName-Arg )
            | Literal                        ( Literal-Arg )
            | Variable                       ( Var-Arg )
  Filename -> cat | ls | pwd | touch | cp | mv | rm | chmod | man | ps | bg | mkdir | test | cd
  Variable -> Letter(Letter | Digit | _ | .)*
  Literal -> -(ε|-)(Letter | Digit)* | Digit*
``` 
Since Filename &#8834; Variable, we will require that no variable be named the same as a Filename.  We can follow the Watt's and Brown's advice for handling the keywords from Filename.  The lexical scanner should identify any terminal that matches a Variable and create a Token for a Variable. The constructor for Token should check the spelling of the Variable and if it is a member of Filename, change the kind of the token to the approptiate value.  *See* pages 123 and 124 for a discussion of this strategy.  

**Note 1:**  9/24/2019 - The Kleene star has been removed from the Argument non-terminal in the assignment statement.  Reomving the ability to assign a vairable a list of Arguments simplifies the language. 

**Note 2:**  9/26/2019 -  Names for productions are listed in parentheses following the rule.

### Abstract Syntax Grammar for a subset of the Bash shell scripting language
```
  Script   -> Command                         ( Script )
  Command   -> Filename Argument  eol         ( Exec-Cmd )
             | Variable = Single-Arg eol      ( Assign-Cmd )
             | if Filename Arugment then eol  ( if-Cmd )
                  Command
               else eol
                  Command
               fi eol
             | for Variable in Argument eol   ( for-Cmd )
                 do eol
                   Command
                 od eol
             | Command Command                 ( Seq-Cmd)
             | ε                               ( Empty-Cmd )
           
  Argument   -> Filename                       ( FName-Arg )
              | Literal                        ( Literal-Arg )
              | Variable                       ( Var-Arg )
              | Argument Argument              ( Seq-Argument )
            
   Single-Arg -> Filename                      ( FName-Arg )
               | Literal                       ( Literal-Arg )
               | Variable                      ( Var-Arg )
            
  Filename -> cat | ls | pwd | touch | cp | mv | rm | chmod | man | ps | bg | mkdir | test | cd
  Variable -> Letter(Letter | Digit | _ | .)*
  Literal -> -(ε|-)(Letter | Digit)* | Digit*
``` 
### Abstract Syntax Trees

#### Script AST (S)
```
    Script
      |
      C
```
#### Command AST (C)
```
    Seq-Cmd         Exec-Cmd          Assign-Cmd    
       |               |                  |   
    ------       -------------       ------------ 
   |      |     |             |     |            |
   C1     C2  FName-Arg       A   Var-Arg   Single-Arg
 
             if-Cmd                for-Cmd        Empty-Cmd
               |                      |               |
      -------------------        -----------          ε
     |         |   |    |       |     |     |
   FName-Arg   A   Ct   Ce   Var-Arg  A    C
```

#### Single-Arg AST
```
FName-Arg   Var-Arg  Literal-Arg
```

#### Argument AST (A)
```
 FName-Arg    Literal-Arg    Var-Arg    Seq-Arg      
     |             |           |           |         
  spelling      spelling    spelling    -------     
                                       |       |
                                       A1      A2
```

### Examples
#### Script: bckup.sh
```
file1 = stuff1.txt
file2 = stuff2.c
file3 = stuff3.java
for file in file1 file2 file3 
  do
    if test -e file then
       cp file bck
    else
    fi
  od  
```
#### AST for bckup.sh *(Indention style)*
```
Script
  Seq-Cmd
    Assign-Cmd
      Var-Arg (file1)
      Single-Arg
        Var-Arg (stuff1.txt)
    Seq-Cmd
        Assign-Cmd
          Var-Arg (file2)
          Single-Arg
            Var-Arg (stuff2.c)
         Seq-Cmd
           Assign-Cmd
             Var-Arg (file3)
              Single-Arg
              Var-Arg (stuff3.java)
         for-Cmd
           Var-Arg (file)
           Seq-Arg
             Var-Arg (file1)
             Seq-Arg
               Var-Arg (file2)
               Var-Arg (file3)
           if-Cmd
             FName-Arg (test)
             Seq-Arg
             	 Literal-Arg (-e)
               Var-Arg (file)
             Exec-Cmd
             	 FName-Arg (cp)
               Seq-Arg
               	 Var-Arg (file)
                 Var-Arg (bck) 
               Empty-Cmd
```
