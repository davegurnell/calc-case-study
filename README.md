# Calculator Case Study

A Scala / functional programming case study based.

## Overview

1. Define a data type `Expr` representing
   expressions containing `+`, `-`, `*`, and `/`.

2. Define a method `eval` that:
    - accepts a parameter of type `Expr`;
    - returns the result of running the calculation.

3. Define a method `prettyPrint` that:
    - accepts a parameter of type `Expr`;
    - returns a pretty printed `String` representing `Expr`
      (make sure to add parentheses in the right places).

4. Update your `eval` method to account for division by zero:
    - return `Some(result)` if the calculation is successful;
    - return `None` if a division by zero occurs;
    - don't use exceptions or `nulls`!

5. Write a method `parsePostfix` that:
    - accepts a parameter of type `String` representing a *postfix* expression;
    - returns the expression as an `Expr`;
    - handles parse errors gracefully;
    - doesn't use exceptions or `nulls`!

Tips for the postfix parser:

- Postfix expressions put the parameters first and the operators last.
  They are interesting because you don't need parentheses to show precedence
  (like you do with normal infix expressions). For example:

~~~
POSTFIX                   INFIX
1 2 +                     1 + 2
1 2 * 3 4 * +             1 * 2 + 3 * 4
1 2 + 3 4 + *             (1 + 2) * (3 + 4)
~~~

- You can parse postfix expressions easily using two stacks:
  one of operands and one of operators.
  Use the `List` data type in Scala to represent stacks.
