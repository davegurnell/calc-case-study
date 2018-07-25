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

## Tips for the postfix parser

Postfix expressions put the parameters first and the operators last.
They are interesting because you don't need parentheses to show precedence
(like you do with normal infix expressions). For example:

~~~
POSTFIX                   INFIX
1 2 +                     1 + 2
1 2 * 3 4 * +             1 * 2 + 3 * 4
1 2 + 3 4 + *             (1 + 2) * (3 + 4)
~~~

You can parse postfix expressions easily using an expression stack:

 - parse the text word-by-word from left to right;
 - every time you pass a number:
    - push it onto the stack;
 - every time you pass an operator:
    - pop two expressions from the stack;
    - combine them;
    - push the resulting expression onto the stack.
 - when you come to the end, you should have
   exactly one expression on the stack: your final result.
