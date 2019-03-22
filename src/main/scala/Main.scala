object Main extends App {
    def compile[R](code: String): (Map[String, Any]) => R = {
        import reflect.runtime.universe
        import tools.reflect.ToolBox

        val tb = universe.runtimeMirror(getClass.getClassLoader).mkToolBox()

        //this actually ended up way cleaner than i anticipated
        tb.compile(
            tb.parse(
                s"""
                    def lambda(variables: Map[String, Any]): Any = {
                        $code
                    }

                    lambda _
                """.stripMargin
            )
        )().asInstanceOf[Map[String, Any] => R]
    }

    import measure.elapsed

    println("started compilation")

    val (time, fcn) = 
        elapsed(
            compile[Int](
                """
                    def multiply(x: Int, n: Int): Int = {
                        if (n == 0)
                            1
                        else
                            if (n % 2 == 0)
                                { val res = multiply(x, n / 2); res * res }
                            else
                                { val res = multiply(x, n / 2); res * res * x }
                    }

                    multiply(
                        variables("x").asInstanceOf[Int],
                        variables("n").asInstanceOf[Int]
                    )
                """.stripMargin
            )
        )

    println(s"compile time: ${time.toDouble / 1000000000.toDouble} seconds")
    println(s"result: ${fcn(Map("x" -> 5, "n" -> 3))}")
}