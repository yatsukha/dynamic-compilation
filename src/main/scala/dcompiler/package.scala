package object dcompiler {
    import collection.mutable.{Map => MutableMap}

    def compile(code: String): (MutableMap[String, Double]) => Double = {
        import reflect.runtime.universe
        import tools.reflect.ToolBox

        val tb = universe.runtimeMirror(getClass.getClassLoader).mkToolBox()

        tb.compile(
            tb.parse(
                s"""
                    def lambda(variables: collection.mutable.Map[String, Double]): Double = {
                        $code
                    }

                    lambda _
                """.stripMargin
            )
        )().asInstanceOf[MutableMap[String, Double] => Double]
    }
}