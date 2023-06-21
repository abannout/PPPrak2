class Sorting {
    fun <A> sort(list: MutableList<A>, ord: Ordering<A>) {
        if (list.size <= 1) return

        var sorted = false
        var tmp: A?

        while (!sorted) {
            sorted = true
            for (i in 0 until list.lastIndex) {
                val left = list[i]
                val right = list[i + 1]
                // if (left > right)
                if (ord(left, right) == OrderResult.Higher) {
                    tmp = left
                    list[i] = right
                    list[i + 1] = tmp
                    sorted = false
                }
            }
        }
    }
    fun <A> sortingBonus(list: List<A>, ord: Ordering<A>):List<A> {
        if (list.size <= 1) return list

        var sorted = false
        var sortedList = list.toMutableList()

        var tmp: A?

        while (!sorted) {
            sorted = true
            for (i in 0 until list.lastIndex) {
                val left = list[i]
                val right = list[i + 1]
                // if (left > right)
                if (ord(left, right) == OrderResult.Higher) {
                    tmp = left
                    sortedList[i] = right
                    sortedList[i + 1] = tmp
                    sorted = false
                }
            }
        }
        return sortedList
    }
}