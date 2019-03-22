package object measure {
    def elapsed[R](code: => R): (Long, R) = {
        val start = System.nanoTime
        val retval = code

        (System.nanoTime - start, retval)
    }
}