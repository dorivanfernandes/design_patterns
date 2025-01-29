package com.dorivan.patterns.behavioral


/*
Iterator can be used when you need to scroll through some collection.
This code is just to learn the pattern, because this kind of iterator can be made easily using filter or map functions
 */


interface BookIterator {
    fun next(): Book
    fun hasNext(): Boolean
}

data class Book(
    val title: String,
    val author: String,
    val publishedYear: String
)

class PublishedYearBookIterator(
    private val books: List<Book>,
    private val year: String
): BookIterator {
    private var index = 0
    override fun next(): Book {
        if (!hasNext()) throw NoSuchElementException()
        return books[index++]
    }

    override fun hasNext(): Boolean {
        while (index < books.size) {
            if (books[index].publishedYear == year) return true
            index++
        }
        return false
    }
}

class Library(private val books: List<Book>) {

    fun getAll(): BookIterator {
        return object : BookIterator {
            private var index = 0

            override fun hasNext(): Boolean = index < books.size
            override fun next(): Book = books[index++]
        }
    }

    fun getIteratorByPublishedYear(year: String): BookIterator {
        return PublishedYearBookIterator(books, year)
    }
}

fun main() {
    val books = listOf(
        Book("Book 1", "Author 1", "2021"),
        Book("Book 2", "Author 2", "2019"),
        Book("Book 3", "Author 3", "2025"),
        Book("Book 4", "Author 4", "2021")
    )

    val library = Library(books)

    println("All the books:")
    val allBooksIterator = library.getAll()
    while (allBooksIterator.hasNext()) {
        println(allBooksIterator.next())
    }

    println("\nBooks published at 2021:")
    val year2021Iterator = library.getIteratorByPublishedYear("2021")
    while (year2021Iterator.hasNext()) {
        println(year2021Iterator.next())
    }
}