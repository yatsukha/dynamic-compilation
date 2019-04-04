package parser

sealed abstract class Expression

case class Lambda(op: String) extends Expression {
    override
    def toString: String =
        op
}

case class Number(num: Double) extends Expression {
    override
    def toString: String =
        num.toString
}
case class Symbol(sym: String) extends Expression {
    override
    def toString: String =
        sym
}

case class Operation(first: Expression, second: Expression, op: Lambda) 
extends Expression {
    override
    def toString: String =
        s"($first $op $second)"
}

// TODO:
object Empty extends Expression