## Lab Discussion
### Colin Boccaccio and Luisa Silva


### Issues in Current Code

 * What pieces of code help versus obscure your understanding?

  Short methods help for understanding, in general naming was ok, but some variables could be named better.

 * What names in the code are helpful and what makes other names less useful?

 Longer names that explain what the code does are useful, shorting names that use acronyms are less useful.

 * What additional methods might be helpful to make the code more readable or usable?

Possibly add methods to handle exceptions.

 * What assumptions does this code have?

 The assumtion that text files have the format "yobYEAR".  

 * What comments might be helpful within the code?

 Descriptions before loops explaining what they do.

 * WhatÂ Code SmellsÂ did you find?

 Some places where code could be more concise.

### Refactoring Plan

 * What are the code's biggest issues?

 I need to cut the number of lines in my main and add in exceptions.

 * Which issues are easy to fix and which are hard?

 Naming of variables and is easier to fix and making methods shorting is much more difficult

 * What are good ways to implement the changes "in place"?

 Look at my code more in depth to look for code smells.

### Refactoring Work

 * Issue chosen: Fix and Alternatives

 Issue: I have all of my methods in one class

 Solution: I will go through my code to see what methods are dependant on each other to determine where I should split up my class.

 * Issue chosen: Fix and Alternatives

Issue: I do not have exceptions thrown in my code.

Solution: I will go through my code to see what will make it fail and throw expectations when needed.
