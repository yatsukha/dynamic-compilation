package object parser {
    def getSymbols(expr: Expression): Set[String] = expr match {
        case Symbol(s) => Set(s)
        case Operation(l, r, Lambda(_)) => getSymbols(l) ++ getSymbols(r)
        case _ => Set()
    }

    def embed(expr: Expression): String = {
        var code = ""

        for (s <- getSymbols(expr))
            code += s"""val $s = variables("$s")""" + '\n'

        code + '\n' + expr
    }
}