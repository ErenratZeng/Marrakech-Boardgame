## Code Review

Reviewed by: Qiutong Zeng, u7724723

Reviewing code written by: Ruize Luo, u7776709

Component: 
https://gitlab.cecs.anu.edu.au/u7733924/comp1110-ass2/-/blob/main/src/comp1110/ass2/Marrakech.java#L37-132

### Comments

#### What is the best feature of this code?
The code contains two main functions:
- isRugValid: Used to verify that the given game string and rug string are valid. It checks whether the length, ID, color, coordinates, etc. of the carpet string comply with the specification.
- rollDie: Simulates the rolling process of a special Marrakech die, where the distribution of 1 to 4 is not uniform, and the probability of 2 and 3 appearing is twice that of 1 and 4.
#### Is the code documented in detail?
- The rollDie method has a detailed comment explaining 
the purpose of the method and the special properties of the die. 
But the isRugValid method has fewer comments and does not fully explain 
the purpose of each check and validation step.

#### Is program decomposition (class and method structure) appropriate?
- The decomposition of the program is basically appropriate. 
Each method has a clear purpose. But the isRugValid method may be too long. 
Consider breaking down some validation steps into separate auxiliary methods
to improve the readability and maintainability of the code.

#### Does it follow Java coding conventions?
- The naming of variables and methods is basically clear
#### If you suspect there is a bug in the code, please suggest a specific situation where the program does not run properly.
- TwoRug currentRug = new TwoRug(rug); and TwoRug rugObj = new TwoRug(rug); create TwoRug objects repeatedly, which is unnecessary.

