package parser

import scala.util.parsing.combinator._

class Parser private extends RegexParsers {
    val double = """(\d+(\.\d+)?)""".r
    val symbol = "[a-zA-Z]+".r

    def factor: Parser[Expression] =
        double ^^ { d => Number(d.toDouble) } |
        symbol ^^ { s => Symbol(s) } |
        "(" ~> termExpr <~ ")"

    def term: Parser[Expression] =
        (factor ~ opt(("*" | "/") ~ term)) ^^ {
            case f ~ None => f
            case f ~ Some("*" ~ t) => Operation(f, t, Lambda("*"))
            case f ~ Some("/" ~ t) => Operation(f, t, Lambda("/"))
            case _ => throw new IllegalStateException
        }

    def exprExpr: Parser[Expression] =
        (termExpr ~ opt(("+" | "-") ~ termExpr)) ^^ {
            case e ~ None => e
            case e ~ Some("+" ~ t) => Operation(e, t, Lambda("+"))
            case e ~ Some("-" ~ t) => Operation(e, t, Lambda("-"))
            case _ => throw new IllegalStateException
        }

    def termExpr: Parser[Expression] =
        (term ~ opt(("+" | "-") ~ term)) ^^ {
            case u ~ None => u
            case u ~ Some("+" ~ v) => Operation(u, v, Lambda("+"))
            case u ~ Some("-" ~ v) => Operation(u, v, Lambda("-"))
            case _ => throw new IllegalStateException
        }

    def buildTree(expr: String): Expression = 
        parse(exprExpr, expr).get
}

object Parser {
    def apply(): Parser = 
        new Parser()
}