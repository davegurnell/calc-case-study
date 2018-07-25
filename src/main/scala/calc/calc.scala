package calc

sealed trait Expr {
  def precedence: Int =
    this match {
      case _: Add => 1
      case _: Sub => 1
      case _: Mul => 2
      case _: Div => 2
      case _: Num => 3
    }
}
final case class Add(a: Expr, b: Expr) extends Expr
final case class Sub(a: Expr, b: Expr) extends Expr
final case class Mul(a: Expr, b: Expr) extends Expr
final case class Div(a: Expr, b: Expr) extends Expr
final case class Num(n: Int) extends Expr

object Expr {
  def eval(expr: Expr): Int =
    expr match {
      case Add(a, b) => eval(a) + eval(b)
      case Sub(a, b) => eval(a) - eval(b)
      case Mul(a, b) => eval(a) * eval(b)
      case Div(a, b) => eval(a) / eval(b)
      case Num(n)    => n
    }

  def safeEval(expr: Expr): Either[String, Int] =
    expr match {
      case Add(a, b) =>
        for {
          x <- safeEval(a)
          y <- safeEval(b)
        } yield x + y

      case Sub(a, b) =>
        for {
          x <- safeEval(a)
          y <- safeEval(b)
        } yield x - y

      case Mul(a, b) =>
        for {
          x <- safeEval(a)
          y <- safeEval(b)
        } yield x * y

      case Div(a, b) =>
        for {
          x <- safeEval(a)
          y <- safeEval(b)
          r <- if(y == 0) Left("Division by zero") else Right(x / y)
        } yield r

      case Num(n) => Right(n)
    }

  // Mul(Add(Num(1), Num(2)), Num(3))
  // (1 + 2) * 3

  def print(expr: Expr): String = {
    val p = expr.precedence
    expr match {
      case Add(a, b) => s"${printParen(p, a)} + ${printParen(p, b)}"
      case Sub(a, b) => s"${printParen(p, a)} - ${printParen(p, b)}"
      case Mul(a, b) => s"${printParen(p, a)} * ${printParen(p, b)}"
      case Div(a, b) => s"${printParen(p, a)} / ${printParen(p, b)}"
      case Num(n)    => n.toString
    }
  }

  def printParen(p1: Int, expr: Expr): String = {
    val p2 = expr.precedence
    if(p1 > p2) s"(${print(expr)})" else print(expr)
  }

  def parse(str: String): Either[String, Expr] =
    parse(str.split(" ").toList, Nil)

  def parse(items: List[String], stack: List[Expr]): Either[String, Expr] =
    items match {
      case (op @ ("+" | "-" | "*" | "/")) :: t => parseOp(op, t, stack)
      case str                            :: t => parseNum(str, t, stack)
      case Nil                                 => finish(stack)
    }

  def parseNum(head: String, tail: List[String], stack: List[Expr]): Either[String, Expr] =
    try {
      parse(tail, Num(head.toInt) :: stack)
    } catch {
      case _: NumberFormatException =>
        Left(s"Invalid term: $head")
    }

  def parseOp(head: String, tail: List[String], stack: List[Expr]): Either[String, Expr] = {
    stack match {
      case b :: a :: rest =>
        head match {
          case "+" => parse(tail, Add(a, b) :: rest)
          case "-" => parse(tail, Sub(a, b) :: rest)
          case "*" => parse(tail, Mul(a, b) :: rest)
          case "/" => parse(tail, Div(a, b) :: rest)
          case _   => Left(s"Bad operator: $head")
        }
      case _ =>
        Left("Not enough items on stack")
    }
  }

  def finish(stack: List[Expr]): Either[String, Expr] =
    stack match {
      case List(expr) => Right(expr)
      case _          => Left(s"Ended up with an incorrect stack: $stack")
    }
}

























