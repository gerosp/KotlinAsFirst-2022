package lesson11.task1

import jdk.jfr.Unsigned
import ru.spbstu.wheels.defaultCopy
import kotlin.math.pow
import kotlin.math.max

/**
 * Класс "беззнаковое большое целое число".
 *
 * Общая сложность задания -- очень сложная, общая ценность в баллах -- 32.
 * Объект класса содержит целое число без знака произвольного размера
 * и поддерживает основные операции над такими числами, а именно:
 * сложение, вычитание (при вычитании большего числа из меньшего бросается исключение),
 * умножение, деление, остаток от деления,
 * преобразование в строку/из строки, преобразование в целое/из целого,
 * сравнение на равенство и неравенство
 */
class UnsignedBigInteger : Comparable<UnsignedBigInteger> {

    private val numbers: IntArray

    /**
     * Конструктор из строки
     */
    constructor(s: String) {
        val filteredString = s.filter { it.isDigit() }

        var power = UnsignedBigInteger(1)
        var n = UnsignedBigInteger(0)
        for (i in filteredString.indices) {

            n += UnsignedBigInteger(filteredString[filteredString.length - i - 1].code - '0'.code) * power

            power *= 10
        }

        numbers = n.numbers
    }


    /**
     * Конструктор из целого
     */
    constructor(i: Int) {
        numbers = IntArray(1)
        numbers[0] = i
    }

    private constructor(otherNumbers: IntArray) {
        numbers = otherNumbers
    }

    /**
     * Сложение
     */
    operator fun plus(other: UnsignedBigInteger): UnsignedBigInteger {
        val result = IntArray(max(numbers.size, other.numbers.size) + 1)
        var remainder = 0
        for (i in result.indices) {
            result[i] =
                (((if (numbers.lastIndex >= i) numbers[i].toLong() else 0) +
                        (if (other.numbers.lastIndex >= i) other.numbers[i].toLong() else 0)
                        + remainder) % 2147483648).toInt()

            if (((if (numbers.lastIndex >= i) numbers[i].toLong() else 0) +
                        (if (other.numbers.lastIndex >= i) other.numbers[i].toLong() else 0)
                        + remainder) >= 2147483648
            ) {
                remainder = 1
            } else {
                remainder = 0
            }

        }
        result[result.lastIndex] += remainder
        return UnsignedBigInteger(removeLeadingZeros(result))
    }

    operator fun plus(other: Int): UnsignedBigInteger =
        this + UnsignedBigInteger(other)

    operator fun inc(): UnsignedBigInteger = this + 1

    /**
     * Вычитание (бросить ArithmeticException, если this < other)
     */
    operator fun minus(other: UnsignedBigInteger): UnsignedBigInteger {
        var borrowIndex = -1
        var n = numbers.clone()
        val result = IntArray(max(this.numbers.size, other.numbers.size))// this.numbers.size должен быть всегда больше
        if (this < other) throw ArithmeticException()
        for (i in n.indices) {
            if ((n[i] >= if (i < other.numbers.size) other.numbers[i] else 0) || (borrowIndex > i)) {
                if (borrowIndex > i) {
                    result[i] =
                        2147483648 - 1 - if (i < other.numbers.size) other.numbers[i] else 0 + n[i] // Если было занято 2^31-1
                } else {
                    result[i] = n[i] - if (i < other.numbers.size) other.numbers[i] else 0
                }
            } else {
                borrowIndex = i + 1
                while (n[borrowIndex] < if (borrowIndex < other.numbers.size) other.numbers[borrowIndex] else 0) borrowIndex++ // Ищем где занять разряд
                n[borrowIndex] -= 1 // Занимаем единицу
                result[i] = (2147483648 - other.numbers[i] + n[i]).toInt()

            }
        }
        return UnsignedBigInteger(removeLeadingZeros(result))
    }

    /**
     * Умножение
     */
    operator fun times(other: UnsignedBigInteger): UnsignedBigInteger {
        val result = IntArray(this.numbers.size * other.numbers.size + 1)
        var remainder = 0
        var offset = 0
        var maxOffset = 0
        for (i in numbers.indices) {
            for (j in other.numbers.indices) {
                var temp: Long = 0
                temp = (numbers[i].toLong() * other.numbers[j].toLong() + remainder)
                result[j + offset] = (temp % (2147483648).toLong()).toInt()
                if (j + offset > maxOffset) maxOffset = j + offset
                remainder = (temp / (2147483648).toLong()).toInt()
            }
            offset++
        }
        result[maxOffset + 1] += remainder
        return UnsignedBigInteger(removeLeadingZeros(result))
    }

    operator fun times(other: Int) = this * UnsignedBigInteger(other)

    /**
     * Деление
     */
    fun divShort(other: Int): UnsignedBigInteger {
        val result = IntArray(this.numbers.size)
        var remainder = 0
        for (i in numbers.lastIndex downTo 0) {
            result[i] = ((numbers[i].toLong() + remainder * 2147483648) / other.toLong()).toInt()
            remainder = ((numbers[i].toLong() + remainder * 2147483648) % other.toLong()).toInt()
        }
        return UnsignedBigInteger(removeLeadingZeros(result))
    }

    operator fun div(other: UnsignedBigInteger): UnsignedBigInteger {

        if (this < other) return UnsignedBigInteger(0)
        if (this == UnsignedBigInteger(0)) throw ArithmeticException()

        if (other < Int.MAX_VALUE) { // Если число маленькое, то можно воспользоваться функцией для маленьких чисел
            return divShort(other.numbers[0])
        }


        var current = UnsignedBigInteger(0)
        val result = IntArray(max(this.numbers.size, other.numbers.size)) // this всегда больше
        var pos = this.numbers.size - 1
        var remainder = UnsignedBigInteger(0)
        var multiplier: UnsignedBigInteger
        while (pos >= 0) {
            var slicePos = pos
            if (numbers[pos] != 0) {
                while (current < other) { // Находим часть числа, большую делителя
                    current =
                        if (remainder != UnsignedBigInteger(0))
                            remainder + UnsignedBigInteger(
                                this.numbers.slice(
                                    slicePos..pos
                                ).toIntArray()
                            )
                        else UnsignedBigInteger(
                            this.numbers.slice(slicePos..pos)
                                .toIntArray()
                        )

                    if (current < other) slicePos--
                    if (slicePos < 0) return UnsignedBigInteger(removeLeadingZeros(result)) // Остался неделимый остаток
                }
                multiplier = UnsignedBigInteger(0)
                while (current >= other) {
                    multiplier++
                    current -= other

                }
                result[slicePos] =
                    multiplier.numbers[0]
                remainder = current

                pos = slicePos
            } else pos--
        }
        return UnsignedBigInteger(removeLeadingZeros(result))
    }

    operator fun div(other: Int): UnsignedBigInteger = div(UnsignedBigInteger(other))

    /**
     * Взятие остатка
     */
    operator fun rem(other: UnsignedBigInteger): UnsignedBigInteger =
        if (this >= other) this - other * (this / other) else this

    operator fun rem(other: Int): UnsignedBigInteger = rem(UnsignedBigInteger(other))

    /**
     * Сравнение на равенство (по контракту Any.equals)
     */
    override fun equals(other: Any?): Boolean =
        if (other is UnsignedBigInteger) {
            numbers.contentEquals(other.numbers)
        } else false


    /**
     * Сравнение на больше/меньше (по контракту Comparable.compareTo)
     */
    override fun compareTo(other: UnsignedBigInteger): Int {
        if (this == other) return 0
        if (numbers.size < other.numbers.size) return -1
        if (numbers.size > other.numbers.size) return 1
        for (i in numbers.indices) {
            if (numbers[numbers.lastIndex - i] < other.numbers[numbers.lastIndex - i]) return -1 else return 1
        }
        return 1
    }

    operator fun compareTo(other: Int) = compareTo(UnsignedBigInteger(other))

    /**
     * Преобразование в строку
     */
    override fun toString(): String {

        val s = StringBuilder()
        var n = UnsignedBigInteger(this.numbers.clone())
        if (n == UnsignedBigInteger(0)) return "0"
        while (n > 0) {
            s.append((n % 10).toInt())

            n /= 10
        }
        s.reverse()
        return s.toString()
    }

    /**
     * Преобразование в целое
     * Если число не влезает в диапазон Int, бросить ArithmeticException
     */
    fun toInt(): Int {
        if (this > Int.MAX_VALUE) throw ArithmeticException()
        return this.numbers[0]
    }

    private fun removeLeadingZeros(array: IntArray): IntArray =
        if (array.dropLastWhile { it == 0 }.isNotEmpty()) array.dropLastWhile { it == 0 }
            .toIntArray() else IntArray(1) { 0 }

}