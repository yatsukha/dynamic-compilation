object Main extends App {
    import dcompiler._
    import parser._

    print("Enter an expression: "); Console.flush()

    val strExpr = io.StdIn.readLine
    val tree = Parser().buildTree(strExpr)

    println(s"Expression evaluated to $tree")

    val symbols = collection.mutable.Map[String, Double]()
    val required = getSymbols(tree)

    if (required.size > 0)
        println("Enter the values of symbols:")

    for (s <- required) {
        print(s"$s = "); Console.flush()

        symbols += ((s, io.StdIn.readLine.toDouble))
    }

    println("Compiling the function...")

    val fcn = compile(embed(tree))

    println("Done.")
    println("Benchmarking...")

    var dIterations = 0;
    val t0 = System.nanoTime
    
    do {
        fcn(symbols)
        dIterations += 1
    } while ((System.nanoTime - t0) / 1000000000 < 1)

    val evaluator = Evaluator(symbols)
    var eIterations = 0;
    val t1 = System.nanoTime

    do {
        evaluator.eval(tree)
        eIterations += 1
    } while ((System.nanoTime - t1) / 1000000000 < 1)

    println("Iterations in 1 second:")
    println(f" - dynamically compiled: $dIterations%12d.")
    println(f" - tree evaluation:      $eIterations%12d.")
}