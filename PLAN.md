# Data Plan
## Colin Boccaccio

This is the link to the [assignment](http://www.cs.duke.edu/courses/compsci307/current/assign/01_data/):


### What is the answer to the two questions below for the data file yob1900.txt (pick a letter that makes it easy too answer)? 

#####Given a year, what is the top ranked name for each gender (M and F)?
M: John
F: Mary
#####Given a year, a gender, and a letter, how many names start with that letter and how many total babies were given those names?
1900, Male, 'Z' : 6 names starting with 'Z', 64 total babies given these names.

### Describe the algorithm you would use to answer each one.

Before answering any of the questions I would determine which names are female to male names and
create two different data sets for each.

For the first question I would take the male name data set and set the frequency of the first name to a variable called highestFreq, then iterate through the
data checking each names frequency against highestFreq, and if it is larger, set highestFreq to this new value. I would then repeat this proccess for the set of female names.

For the second question I would answer the first part of the question by determining the frequency in which a line of data begins with the given letter.
For the second part of the question I would have a loop iterate through the data set while adding the frequency associated with a name beginning with the given letter to a sum.

### Likely you may not even need a data structure to answer the questions below, but what about some of the questions in Part 2?

For the questions concerning rankings, I would put the data into a map for both male and female, with the key of each entry being
the name, and the value being the frequency of that name.

### What are some ways you could test your code to make sure it is giving the correct answer (think beyond just choosing "lucky" parameter values)?

Creating smaller subsets of the data to allow for easily verifiable results in addition to JUnit tests.

### What kinds of things make the second question harder to test?

The set not being in alphabetic order or there being a very large volume of unique names for each letter.

### What kind of errors might you expect to encounter based on the questions or in the dataset?

Every name that has a given letter being counted as opposed to only the names that begin with that letter.

### How would you detect those errors and what would a reasonable "answer" be in such cases?

These errors could be detected through creating a small subset of data that allows for easy manual counting of individual names.
A reasonable answer to this issue would be to ensure that there exists some way that checks that only the first letter is being
compared to the target letter.

### How would your algorithm and testing need to change (if at all) to handle multiple files (i.e., a range of years)?

If I wanted to compare the data between dfferent years, I would need to prevent bugs from occuring due to some names not being
present in both of the years being compared.