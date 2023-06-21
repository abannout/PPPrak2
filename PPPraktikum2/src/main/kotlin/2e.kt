fun main(args: Array<String>) {
    var either: Either<String,Int > = makeEither("Ahmad", 23)
    println(either)
    either = makeEither("Ahmad", 23)
    println(either)

}
sealed interface Either <out A, out B>
data class Left <A >( val a: A): Either <A, Nothing >{
    override fun toString(): String {
        return "$a"
    }
}
data class Right <B >( val b: B): Either < Nothing , B>{
    override fun toString(): String {
        return "$b"
    }
}

//fun <A, B> makeEitherName (a: A, b: B) : Either <A, B> = Left(a)
fun <A, B> makeEither (a: A, b: B) : Either <A, B> = Right(b)

//f ) Warum ist die Implementierung von makeEither nicht 100 %ig valide? Begründe Sie ihre
//Antwort entweder mit der Verwendung von Algebra oder durch logische Argumente.

/*
weil sie immer eine Right-Variante von Either erzeugt.
Das bedeutet, die Funktion kann nur Werte vom Typ Either<A, B> erzeugen,
bei denen A auf Nothing festgelegt ist und B beliebigen Typ haben kann.
 */