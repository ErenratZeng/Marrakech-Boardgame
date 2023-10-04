## Code Review

Reviewed by:

- name: Zhuiqi Lin
  uid: u7733924

Reviewing code written by:

- name: Qiutong Zeng
  uid: u7724723

Component:

- https://gitlab.cecs.anu.edu.au/u7733924/comp1110-ass2/-/blob/main/src/comp1110/ass2/gui/Viewer.java#L42-197

### Comments

- What are the best features of this code?
  <br/>Draw the game close to the final goal and can convert and visualise the input string into game state.
- Is the code well-documented?
  <br/>Sure, clearly state what the function does, and what the key steps and variables mean.
- Is the program decomposition (class and method structure) appropriate?
  <br/>Sure, break down the entire status display into data conversion and data display. The data transformation
  reasonably calls on the data model built by others to convert strings into game states in an easy-to-understand way,
  and then visualises the states through a reasonable UI design.
- Does it follow Java code conventions (for example, are methods and variables properly named), and is the style
  consistent throughout?
  <br/>Sure, ensure as much consistency as possible in naming variables with Camel case inside and outside the
  interface.
- If you suspect an error in the code, suggest a particular situation in which the program will not function correctly.
  <br/>Nothing wrong in fact.