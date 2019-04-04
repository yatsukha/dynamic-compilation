package parser

class Evaluator private(val symbols: collection.mutable.Map[String, Double]) {
    def eval(expr: Expression): Double = expr match {
        case Symbol(s) => symbols(s)
        case Number(n) => n
        case Operation(l, r, Lambda(op)) => op match { // seems like a bad lambda :^)
            case "+" => eval(l) + eval(r)
            case "-" => eval(l) - eval(r)
            case "/" => eval(l) / eval(r)
            case "*" => eval(l) * eval(r)
        }
        case _ => throw new IllegalStateException
    }
}

object Evaluator {
    def apply(symbols: collection.mutable.Map[String, Double]): Evaluator =
        new Evaluator(symbols)
}