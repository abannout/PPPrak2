typealias Ordering <A> = (A, A) -> OrderResult

fun main(args: Array<String>) {
    //Aufgabe 1
    val intOrd: Ordering<Int> = { left, right ->
        if (left < right) OrderResult.Lower
        else if (left > right) OrderResult.Higher
        else OrderResult.Equal
    }
    //Ordering<String>
    val stringOrd: Ordering<String> = { left, right ->
        //-1 für lower und +1 für großer
        val result = left.compareTo(right)
        if (result < 0) OrderResult.Lower
        else if (result > 0) OrderResult.Higher
        else OrderResult.Equal
    }

    //Ordering<Double>
    val doubleOrd: Ordering<Double> = { left, right ->
        if (left < right) OrderResult.Lower
        else if (left > right) OrderResult.Higher
        else OrderResult.Equal
    }

    //Ordering<Boolean>
    /* Drei Fälle
    left = false && right = true
    left = true && right = false
    left == right (false or true)

     */
    val booleanOrd: Ordering<Boolean> = { left, right ->
        if (!left && right) OrderResult.Lower
        else if (left && !right) OrderResult.Higher
        else OrderResult.Equal
    }
    /*
    1.2 a)
     */
    /*
        val intDesc = reversed(intOrd)
        val string = debug(stringOrd)
        val doubleDesc = debug(reversed(doubleOrd))
        string("ich", "du")


        1.3 c)
    */
    // A ist String und B ist Person
    /* val person1: Ordering<Person> = contraMap(stringOrd) { pers -> pers.name };
     val secondPerson: Ordering<Person> = contraMap(intOrd) { pers -> pers.alter };

    */
    /*
    1.4 b)
     */
    /*
        val people = mutableListOf(
            Person(" Nathalie ", 25),
            Person(" Alex ", 33),
            Person("Zah ", 28),
            Person(" Alex ", 18),
            Person(" Jens ", 33),
        )
        val personOrdering: Ordering<Person> = contraMap(zip(stringOrd, intOrd)) { person -> Pair(person.name, person.alter) }
        Sorting().sort(people, personOrdering)
        people.forEach(::println)
    */
    val people = mutableListOf(
        Person(" Nathalie ", 25, 172.5),
        Person(" Alex ", 33, 186.0),
        Person("Zah ", 28, 158.3),
        Person(" Alex ", 18, 183.0),
        Person(" Jens ", 33, 168.5),
    )
    val personOrd: Ordering<Person> =
        stringOrd
            .zip(intOrd.reversed())
            .zip(doubleOrd)
            .contraMap { person ->
                person.name to person.alter to person.height // kürzere Schreibweise für Pair ( Pair ( person .name , person . age ), person . height )
            }
    Sorting().sort(people, personOrd)
    people.forEach(::println)

    //1.5 Bonus b
    val people2 = mutableListOf(
        Person(" Nathalie ", 25, 172.5),
        Person(" Alex ", 33, 186.0),
        Person("Zah ", 28, 158.3),
        Person(" Alex ", 18, 183.0),
        Person(" Jens ", 33, 168.5),
    )
    val personOrd2: Ordering<Person> = stringOrd
        .zip(intOrd.reversed())
        .zip(doubleOrd)
        .contraMap { person ->
            person.name to person.alter to person.height // kürzere Schreibweise für Pair ( Pair ( person .name , person . age ), person . height )
        }

    people.orderBy(personOrd2).forEach(::println)

}


//Aufgabe 1.2
// a)
/*
fun <A> reversed(ordering: Ordering<A>): Ordering<A> = { left, right ->
    when (ordering(left, right)) {
        OrderResult.Lower -> OrderResult.Higher
        OrderResult.Higher -> OrderResult.Lower
        else -> OrderResult.Equal
    }
}

fun <A> debug(ordering: Ordering<A>): Ordering<A> = { left, right ->
    val result = ordering(left, right)
    println("$left is $result than $right")
    result
}

// Aufgabe 1.3 b)
*/
/*fun <A, B> contraMap(ordering: Ordering<A>, transform: (B) -> A): Ordering<B> {
    return { left, right -> ordering(transform(left), transform(right)) }
}*/
/*

//Aufbage 1.4 a)
fun <A, B> zip(orderingA: Ordering<A>, orderingB: Ordering<B>): Ordering<Pair<A, B>> {
    return { left, right ->
        val resultA = orderingA(left.first, right.first)
        if (resultA != OrderResult.Equal) {
            resultA
        } else {
            orderingB(left.second, right.second)
        }
    }
}
*/
//1.5 a)

fun <A> Ordering<A>.reversed(): Ordering<A> = { left, right ->
    when (this(left, right)) {
        OrderResult.Lower -> OrderResult.Higher
        OrderResult.Higher -> OrderResult.Lower
        else -> OrderResult.Equal
    }
}

fun <A> Ordering<A>.debug(): Ordering<A> = { left, right ->
    val result = this(left, right)
    println("$left is $result than $right")
    result
}


fun <A, B> Ordering<A>.contraMap(transform: (B) -> A): Ordering<B> {
    return { left, right -> this(transform(left), transform(right)) }
}

fun <A, B> Ordering<A>.zip(orderingB: Ordering<B>): Ordering<Pair<A, B>> {
    return { left, right ->
        val resultA = this(left.first, right.first)
        if (resultA != OrderResult.Equal) {
            resultA
        } else {
            orderingB(left.second, right.second)
        }
    }
}

//1.5 b)
fun <A> List<A>.orderBy(ordering: Ordering<A>): List<A> = Sorting().sortingBonus(this, ordering)

/*1.2
b)
• Warum sind reversed und debug Funktionen höherer Ordnung?
Die Funktionen reversed und debug werden als Funktionen höherer Ordnung betrachtet,
weil sie andere Funktionen als Parameter akzeptieren und/oder Funktionen als
Rückgabewert liefern.

Welches Entwurfsmuster wurde durch die Verwendung von Funktionen höherer Ordnung
realisiert?
ermöglicht die Implementierung des Entwurfsmusters "Strategy"
In diesem Fall werden die Funktionen reversed und debug als Strategien betrachtet,
die auf eine gegebene Funktion ordering angewendet werden können.

Warum kann das Entwurfsmuster dadurch implementiert werden? Was ist die Grundlegende Struktur des Entwurfsmusters und inwiefern korreliert diese Struktur mit der von
Funktionen höherer Ordnung?
Die Ordering-Typalias definiert das abstrakte Strategie-Interface,
das eine Funktion mit zwei Parametern (left und right) und einem Rückgabewert
(OrderResult) darstellt.
Die verschiedenen Ordering-Implementierungen (intOrd, stringOrd, doubleOrd, booleanOrd)
 stellen die konkreten Strategien dar,
 die jeweils eine spezifische Vergleichslogik für den entsprechenden Typ implementieren.
Die Funktionen reversed und debug sind Helferfunktionen,
die Funktionen höherer Ordnung zurückgeben.
 Diese Funktionen akzeptieren eine Ordering-Strategie als Parameter und
  wenden eine zusätzliche Funktionalität auf diese Strategie an.
  reversed kehrt die Reihenfolge der Vergleichsergebnisse um, und debug fügt
   Debugging-Code hinzu, um den Vergleichsprozess zu protokollieren.
 */
/*
2
 */

// a) Welcher Typ in Kotlin ist äquivalent zur 1 in der Algebra?
/*
In Kotlin ist der äquivalente Typ zur 1 in der Algebra der Typ Unit.
 Der Typ Unit repräsentiert einen Wert, der keine Information enthält.
 */

//Zeigen Sie, ob Either<Option<A>, B> äquivalent bzw. isomorph zu Option<Either<A, B>>
//ist. Überführen Sie dazu die Typen in Algebra. Hinweis: Option ist der nullfähige Typ in Kotlin.
/*
Either<Option<A>, B>:

Either repräsentiert eine Alternative zwischen zwei Typen. Es kann entweder den Typ Option<A> (eine Option mit einem möglichen Wert vom Typ A) oder den Typ B enthalten.
Option ist der nullfähige Typ in Kotlin, der entweder einen Wert vom Typ A enthält (Some) oder keinen Wert (None).
Option<Either<A, B>>:

Option repräsentiert eine Option, die entweder einen Wert vom Typ Either<A, B> enthält (Some) oder keinen Wert (None).
Either repräsentiert eine Alternative zwischen zwei Typen und kann entweder den Typ A oder den Typ B enthalten.
Daher sie sind äquivalent zueinander
 */

//c) Überführen Sie das Potenzgesetz a^0 = 1 in Typen. Implementieren Sie auch die jeweilige
//to- und from-Funktion. Die Funktionen sind:
//fun <A> to(f: ( Nothing ) -> A): Unit = TODO ()
//fun <A> from ( unit : Unit ) : ( Nothing ) -> A = TODO ()

/*
typealias PowZero<A> = (Nothing) -> A
fun <A> to(f: ( Nothing ) -> A): Unit = Unit //==1  Funktion f kann nicht aufgerufen werden, weil type Nothing
fun <A> from ( unit : Unit ) : ( Nothing ) -> A = //wirft Error, weil Nothing kann keine Instanz haben
 */
//d) Warum kann die from-Funktion implementiert werden,
// obwohl nur ein Nothing zur Verfügung steht,
// aber ein Wert vom Typ A zurückgegeben werden muss? Hinweis: Die Antwort liegt
//im Subtyping-System von Kotlin.

/*
In Kotlin ist Nothing der Bottom-Typ,
 der in der Typhierarchie ganz unten steht und keine Instanzen hat.
  Das bedeutet, dass Nothing ein Subtyp aller anderen Typen ist.
  Jeder beliebige Typ kann also in Nothing umgewandelt werden,
 da es keine Instanzen von Nothing gibt und die Funktion niemals aufgerufen wird.
Da Nothing ein Subtyp von A ist und in Kotlin das Subtyping-Konzept gilt,
kann die from-Funktion implementiert werden
 */


