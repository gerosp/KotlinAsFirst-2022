@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson9.task1

// Урок 9: проектирование классов
// Максимальное количество баллов = 40 (без очень трудных задач = 15)

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)

    fun clone(): Matrix<E> // Нужно для возмонжости копирования матрицы
}

/**
 * Простая (2 балла)
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> {
    if (height <= 0 || width <= 0) throw IllegalArgumentException()
    return MatrixImpl<E>(height, width, e)
}

/**
 * Средняя сложность (считается двумя задачами в 3 балла каждая)
 *
 * Реализация интерфейса "матрица"
 */

class MatrixImpl<E>(override val height: Int, override val width: Int, e: E) : Matrix<E> {

    private var cells: MutableList<MutableList<E>> = mutableListOf()

    init {
        if (height <= 0 || width <= 0) throw IllegalArgumentException()
        for (i in 0..height - 1) {
            cells.add(mutableListOf())
            for (j in 0..width - 1) {
                cells[i].add(e)
            }
        }
    }

    override fun get(row: Int, column: Int): E {
        if (row < 0 || row > height || column < 0 || column > width) throw IndexOutOfBoundsException()

        return cells[row][column]
    }

    override fun get(cell: Cell): E {
        if (cell.row < 0 || cell.row > height || cell.column < 0 || cell.column > width)
            throw IndexOutOfBoundsException()
        return cells[cell.row][cell.column]
    }

    override fun set(row: Int, column: Int, value: E) {
        if (row < 0 || row > height || column < 0 || column > width) throw IndexOutOfBoundsException()
        cells[row][column] =
            value
    }

    override fun set(cell: Cell, value: E) {
        if (cell.row < 0 || cell.row > height || cell.column < 0 || cell.column > width)
            throw IndexOutOfBoundsException()
        cells[cell.row][cell.column] = value
    }

    override fun equals(other: Any?): Boolean {
        var isEqual = false
        if (other is MatrixImpl<*> && other.height == height && other.width == width) {
            isEqual = true
            for (i in cells.indices) {
                for (j in cells[i].indices) {
                    if (cells[i][j] != other[i, j]) {
                        isEqual = false
                        break
                    }
                }
            }
        }
        return isEqual
    }

    override fun toString(): String {
        val sb = StringBuilder()
        for (i in cells.indices) {
            for (j in cells[i].indices) {
                if (j != cells.lastIndex) {
                    sb.append(cells[i][j].toString() + ", ")
                } else sb.append(cells[i][j].toString() + "\n")
            }
        }
        return sb.toString()
    }

    override fun hashCode(): Int {
        var result = height
        result = 31 * result + width
        return result
    }

    // Код ниже нужен для копирования матриц в решении пятнашек
    private constructor(newCells: List<MutableList<E>>, e: E) : this(newCells.size, newCells[0].size, e) {
        cells = newCells.toMutableList()
    }

    override fun clone(): MatrixImpl<E> = MatrixImpl<E>(cells.map { it.toMutableList() }, cells[0][0])

}

