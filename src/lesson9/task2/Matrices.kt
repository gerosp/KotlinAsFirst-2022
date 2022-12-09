@file:Suppress("UNUSED_PARAMETER")

package lesson9.task2

import lesson9.task1.Matrix
import lesson9.task1.MatrixImpl
import lesson9.task1.createMatrix
import java.util.*
import kotlin.math.abs
import kotlin.math.min

// Все задачи в этом файле требуют наличия реализации интерфейса "Матрица" в Matrix.kt

/**
 * Пример
 *
 * Транспонировать заданную матрицу matrix.
 * При транспонировании строки матрицы становятся столбцами и наоборот:
 *
 * 1 2 3      1 4 6 3
 * 4 5 6  ==> 2 5 5 2
 * 6 5 4      3 6 4 1
 * 3 2 1
 */
fun <E> transpose(matrix: Matrix<E>): Matrix<E> {
    if (matrix.width < 1 || matrix.height < 1) return matrix
    val result = createMatrix(height = matrix.width, width = matrix.height, e = matrix[0, 0])
    for (i in 0 until matrix.width) {
        for (j in 0 until matrix.height) {
            result[i, j] = matrix[j, i]
        }
    }
    return result
}

/**
 * Пример
 *
 * Сложить две заданные матрицы друг с другом.
 * Складывать можно только матрицы совпадающего размера -- в противном случае бросить IllegalArgumentException.
 * При сложении попарно складываются соответствующие элементы матриц
 */
operator fun Matrix<Int>.plus(other: Matrix<Int>): Matrix<Int> {
    require(!(width != other.width || height != other.height))
    if (width < 1 || height < 1) return this
    val result = createMatrix(height, width, this[0, 0])
    for (i in 0 until height) {
        for (j in 0 until width) {
            result[i, j] = this[i, j] + other[i, j]
        }
    }
    return result
}

/**
 * Сложная (5 баллов)
 *
 * Заполнить матрицу заданной высоты height и ширины width
 * натуральными числами от 1 до m*n по спирали,
 * начинающейся в левом верхнем углу и закрученной по часовой стрелке.
 *
 * Пример для height = 3, width = 4:
 *  1  2  3  4
 * 10 11 12  5
 *  9  8  7  6
 */
fun generateSpiral(height: Int, width: Int): Matrix<Int> = TODO()

/**
 * Сложная (5 баллов)
 *
 * Заполнить матрицу заданной высоты height и ширины width следующим образом.
 * Элементам, находящимся на периферии (по периметру матрицы), присвоить значение 1;
 * периметру оставшейся подматрицы – значение 2 и так далее до заполнения всей матрицы.
 *
 * Пример для height = 5, width = 6:
 *  1  1  1  1  1  1
 *  1  2  2  2  2  1
 *  1  2  3  3  2  1
 *  1  2  2  2  2  1
 *  1  1  1  1  1  1
 */
fun generateRectangles(height: Int, width: Int): Matrix<Int> = TODO()

/**
 * Сложная (5 баллов)
 *
 * Заполнить матрицу заданной высоты height и ширины width диагональной змейкой:
 * в левый верхний угол 1, во вторую от угла диагональ 2 и 3 сверху вниз, в третью 4-6 сверху вниз и так далее.
 *
 * Пример для height = 5, width = 4:
 *  1  2  4  7
 *  3  5  8 11
 *  6  9 12 15
 * 10 13 16 18
 * 14 17 19 20
 */
fun generateSnake(height: Int, width: Int): Matrix<Int> = TODO()

/**
 * Средняя (3 балла)
 *
 * Содержимое квадратной матрицы matrix (с произвольным содержимым) повернуть на 90 градусов по часовой стрелке.
 * Если height != width, бросить IllegalArgumentException.
 *
 * Пример:    Станет:
 * 1 2 3      7 4 1
 * 4 5 6      8 5 2
 * 7 8 9      9 6 3
 */
fun <E> rotate(matrix: Matrix<E>): Matrix<E> = TODO()

/**
 * Сложная (5 баллов)
 *
 * Проверить, является ли квадратная целочисленная матрица matrix латинским квадратом.
 * Латинским квадратом называется матрица размером n x n,
 * каждая строка и каждый столбец которой содержат все числа от 1 до n.
 * Если height != width, вернуть false.
 *
 * Пример латинского квадрата 3х3:
 * 2 3 1
 * 1 2 3
 * 3 1 2
 */
fun isLatinSquare(matrix: Matrix<Int>): Boolean = TODO()

/**
 * Средняя (3 балла)
 *
 * В матрице matrix каждый элемент заменить суммой непосредственно примыкающих к нему
 * элементов по вертикали, горизонтали и диагоналям.
 *
 * Пример для матрицы 4 x 3: (11=2+4+5, 19=1+3+4+5+6, ...)
 * 1 2 3       11 19 13
 * 4 5 6  ===> 19 31 19
 * 6 5 4       19 31 19
 * 3 2 1       13 19 11
 *
 * Поскольку в матрице 1 х 1 примыкающие элементы отсутствуют,
 * для неё следует вернуть как результат нулевую матрицу:
 *
 * 42 ===> 0
 */
fun sumNeighbours(matrix: Matrix<Int>): Matrix<Int> = TODO()

/**
 * Средняя (4 балла)
 *
 * Целочисленная матрица matrix состоит из "дырок" (на их месте стоит 0) и "кирпичей" (на их месте стоит 1).
 * Найти в этой матрице все ряды и колонки, целиком состоящие из "дырок".
 * Результат вернуть в виде Holes(rows = список дырчатых рядов, columns = список дырчатых колонок).
 * Ряды и колонки нумеруются с нуля. Любой из спискоов rows / columns может оказаться пустым.
 *
 * Пример для матрицы 5 х 4:
 * 1 0 1 0
 * 0 0 1 0
 * 1 0 0 0 ==> результат: Holes(rows = listOf(4), columns = listOf(1, 3)): 4-й ряд, 1-я и 3-я колонки
 * 0 0 1 0
 * 0 0 0 0
 */
fun findHoles(matrix: Matrix<Int>): Holes = TODO()

/**
 * Класс для описания местонахождения "дырок" в матрице
 */
data class Holes(val rows: List<Int>, val columns: List<Int>)

/**
 * Средняя (3 балла)
 *
 * В целочисленной матрице matrix каждый элемент заменить суммой элементов подматрицы,
 * расположенной в левом верхнем углу матрицы matrix и ограниченной справа-снизу данным элементом.
 *
 * Пример для матрицы 3 х 3:
 *
 * 1  2  3      1  3  6
 * 4  5  6  =>  5 12 21
 * 7  8  9     12 27 45
 *
 * К примеру, центральный элемент 12 = 1 + 2 + 4 + 5, элемент в левом нижнем углу 12 = 1 + 4 + 7 и так далее.
 */
fun sumSubMatrix(matrix: Matrix<Int>): Matrix<Int> = TODO()

/**
 * Простая (2 балла)
 *
 * Инвертировать заданную матрицу.
 * При инвертировании знак каждого элемента матрицы следует заменить на обратный
 */
operator fun Matrix<Int>.unaryMinus(): Matrix<Int> = TODO(this.toString())

/**
 * Средняя (4 балла)
 *
 * Перемножить две заданные матрицы друг с другом.
 * Матрицы можно умножать, только если ширина первой матрицы совпадает с высотой второй матрицы.
 * В противном случае бросить IllegalArgumentException.
 * Подробно про порядок умножения см. статью Википедии "Умножение матриц".
 */
operator fun Matrix<Int>.times(other: Matrix<Int>): Matrix<Int> = TODO(this.toString())

/**
 * Сложная (7 баллов)
 *
 * Даны мозаичные изображения замочной скважины и ключа. Пройдет ли ключ в скважину?
 * То есть даны две матрицы key и lock, key.height <= lock.height, key.width <= lock.width, состоящие из нулей и единиц.
 *
 * Проверить, можно ли наложить матрицу key на матрицу lock (без поворота, разрешается только сдвиг) так,
 * чтобы каждой единице в матрице lock (штырь) соответствовал ноль в матрице key (прорезь),
 * а каждому нулю в матрице lock (дырка) соответствовала, наоборот, единица в матрице key (штырь).
 * Ключ при сдвиге не может выходить за пределы замка.
 *
 * Пример: ключ подойдёт, если его сдвинуть на 1 по ширине
 * lock    key
 * 1 0 1   1 0
 * 0 1 0   0 1
 * 1 1 1
 *
 * Вернуть тройку (Triple) -- (да/нет, требуемый сдвиг по высоте, требуемый сдвиг по ширине).
 * Если наложение невозможно, то первый элемент тройки "нет" и сдвиги могут быть любыми.
 */
fun canOpenLock(key: Matrix<Int>, lock: Matrix<Int>): Triple<Boolean, Int, Int> {
    for (xOffset in 0..lock.width - key.width) {
        for (yOffset in 0..lock.height - key.height) {
            var opens = true
            for (x in 0 until key.width) {
                for (y in 0 until key.height) {
                    if (lock[yOffset + y, xOffset + x] == key[y, x]) {
                        opens = false
                        break
                    }
                }
            }
            if (opens) return Triple(true, yOffset, xOffset)
        }
    }
    return Triple(false, -1, -1)
}

/**
 * Сложная (8 баллов)
 *
 * В матрице matrix размером 4х4 дана исходная позиция для игры в 15, например
 *  5  7  9  1
 *  2 12 14 15
 *  3  4  6  8
 * 10 11 13  0
 *
 * Здесь 0 обозначает пустую клетку, а 1-15 – фишки с соответствующими номерами.
 * Напомним, что "игра в 15" имеет квадратное поле 4х4, по которому двигается 15 фишек,
 * одна клетка всегда остаётся пустой. Цель игры -- упорядочить фишки на игровом поле.
 *
 * В списке moves задана последовательность ходов, например [8, 6, 13, 11, 10, 3].
 * Ход задаётся номером фишки, которая передвигается на пустое место (то есть, меняется местами с нулём).
 * Фишка должна примыкать к пустому месту по горизонтали или вертикали, иначе ход не будет возможным.
 * Все номера должны быть в пределах от 1 до 15.
 * Определить финальную позицию после выполнения всех ходов и вернуть её.
 * Если какой-либо ход является невозможным или список содержит неверные номера,
 * бросить IllegalStateException.
 *
 * В данном случае должно получиться
 * 5  7  9  1
 * 2 12 14 15
 * 0  4 13  6
 * 3 10 11  8
 */


fun findNeighbor(matrix: Matrix<Int>, cell: Pair<Int, Int>, value: Int): Pair<Int, Int>? {
    var result: Pair<Int, Int>? = null
    val maxCoord = 4
    if (cell.first + 1 < maxCoord && matrix[cell.second, cell.first + 1] == value) result =
        Pair(cell.first + 1, cell.second)
    if (cell.first - 1 >= 0 && matrix[cell.second, cell.first - 1] == value) result =
        Pair(cell.first - 1, cell.second)
    if (cell.second + 1 < maxCoord && matrix[cell.second + 1, cell.first] == value) result =
        Pair(cell.first, cell.second + 1)
    if (cell.second - 1 >= 0 && matrix[cell.second - 1, cell.first] == value) result =
        Pair(cell.first, cell.second - 1)
    return result
}

fun swap(matrix: Matrix<Int>, a: Pair<Int, Int>, b: Pair<Int, Int>) {
    var temp = 0
    temp = matrix[a.second, a.first]
    matrix[a.second, a.first] = matrix[b.second, b.first]
    matrix[b.second, b.first] = temp
}

fun fifteenGameMoves(matrix: Matrix<Int>, moves: List<Int>): Matrix<Int> {
    var zero = Pair(-1, -1)
    for (column in 0..3) {
        for (row in 0..3) {
            if (matrix[row, column] == 0) zero =
                Pair(column, row)
        }
    }
    if (zero.first == -1 || zero.second == -1) throw IllegalStateException()
    for (move in moves) {
        if (findNeighbor(matrix, zero, move) != null) {
            val swapTarget = findNeighbor(matrix, zero, move)!!
            swap(matrix, zero, swapTarget)
            zero = swapTarget
        } else throw IllegalStateException()
    }
    return matrix
}

/**
 * Очень сложная (32 балла)
 *
 * В матрице matrix размером 4х4 дана исходная позиция для игры в 15, например
 *  5  7  9  2
 *  1 12 14 15
 *  3  4  6  8
 * 10 11 13  0
 *
 * Здесь 0 обозначает пустую клетку, а 1-15 – фишки с соответствующими номерами.
 * Напомним, что "игра в 15" имеет квадратное поле 4х4, по которому двигается 15 фишек,
 * одна клетка всегда остаётся пустой.
 *
 * Цель игры -- упорядочить фишки на игровом поле, приведя позицию к одному из следующих двух состояний:
 *
 *  1  2  3  4          1  2  3  4
 *  5  6  7  8   ИЛИ    5  6  7  8
 *  9 10 11 12          9 10 11 12
 * 13 14 15  0         13 15 14  0
 *
 * Можно математически доказать, что РОВНО ОДНО из этих двух состояний достижимо из любой исходной позиции.
 *
 * Вернуть решение -- список ходов, приводящих исходную позицию к одной из двух упорядоченных.
 * Каждый ход -- это перемена мест фишки с заданным номером с пустой клеткой (0),
 * при этом заданная фишка должна по горизонтали или по вертикали примыкать к пустой клетке (но НЕ по диагонали).
 * К примеру, ход 13 в исходной позиции меняет местами 13 и 0, а ход 11 в той же позиции невозможен.
 *
 * Одно из решений исходной позиции:
 *
 * [8, 6, 14, 12, 4, 11, 13, 14, 12, 4,
 * 7, 5, 1, 3, 11, 7, 3, 11, 7, 12, 6,
 * 15, 4, 9, 2, 4, 9, 3, 5, 2, 3, 9,
 * 15, 8, 14, 13, 12, 7, 11, 5, 7, 6,
 * 9, 15, 8, 14, 13, 9, 15, 7, 6, 12,
 * 9, 13, 14, 15, 12, 11, 10, 9, 13, 14,
 * 15, 12, 11, 10, 9, 13, 14, 15]
 *
 * Перед решением этой задачи НЕОБХОДИМО решить предыдущую
 */
class GameState(
    val field: Matrix<Int>, // Поле игры
    val turns: Int, // Количество ходов от начала игры до этого состояния
    val path: IntArray, // Путь до состояния
    var distance: Int = -1, // Сумма манхэттанских расстояний состояния
    var zeroPosition: Pair<Int, Int> = Pair(-1, -1) // Позиция нуля на игровом поле
) {

    init {
        if (distance == -1) distance = calculateDistance()
        if (zeroPosition == Pair(-1, -1)) zeroPosition = findZero()
    }

    val weight = distance// Можно было бы использовать distance + turns, как предполагается в A*

    // Поиск нуля на игровом поле
    private fun findZero(): Pair<Int, Int> {
        var zero = Pair(-1, -1)
        for (column in 0 until field.width) {
            for (row in 0 until field.height) {
                if (field[row, column] == 0) zero = Pair(column, row) // я без понятия почему так
            }
        }
        return zero
    }

    // Рассчет суммы манхэттанских расстойний в данном состоянии
    private fun calculateDistance(): Int {
        var fieldDistance = 0
        for (column in 0 until field.width) {
            for (row in 0 until field.height) {
                if (field[row, column] !in setOf(0, 14, 15)) { // не учитываем 0
                    fieldDistance += abs(row - (field[row, column] - 1) / 4) + abs(column - (field[row, column] - 1) % 4)
                } else if (field[row, column] == 14 || field[row, column] == 15) fieldDistance += min( // берем минимальную дистанцию для 14 и 15 из двух вариантов
                    abs(row - 3) + abs(
                        column - 2
                    ), abs(row - 3) + abs(column - 1)
                )
            }
        }
        return fieldDistance
    }

    // Дистанция для одной точки в текущей позиции
    fun calculateDistanceSingle(pos: Pair<Int, Int>): Int {
        var newDistance = 0
        if (field[pos.second, pos.first] !in setOf(0, 14, 15)) { // не учитываем 0
            newDistance =
                abs(pos.second - (field[pos.second, pos.first] - 1) / 4) + abs(pos.first - (field[pos.second, pos.first] - 1) % 4)
        } else if (field[pos.second, pos.first] == 14 || field[pos.second, pos.first] == 15) newDistance =
            min( // берем минимальную дистанцию для 14 и 15 из двух вариантов
                abs(pos.second - 3) + abs(
                    pos.first - 2
                ), abs(pos.second - 3) + abs(pos.first - 1)
            )
        return newDistance
    }

    // Дистанция для данного значения в данной позиции
    fun calculateDistanceSingle(pos: Pair<Int, Int>, value: Int): Int {
        var newDistance = 0
        if (value !in setOf(0, 14, 15)) { // не учитываем 0
            newDistance =
                abs(pos.second - (value - 1) / 4) + abs(pos.first - (value - 1) % 4)
        } else if (value == 14 || value == 15) newDistance =
            min( // берем минимальную дистанцию для 14 и 15 из двух вариантов
                abs(pos.second - 3) + abs(
                    pos.first - 2
                ), abs(pos.second - 3) + abs(pos.first - 1)
            )
        return newDistance
    }

    override fun equals(other: Any?): Boolean { // Чтобы не сравнивало объекты по ссылке
        if (other is GameState) {
            return field == other.field
        }
        return false
    }

    override fun hashCode(): Int {
        var result = field.hashCode()
        result = 31 * result + distance
        result = 31 * result + zeroPosition.hashCode()
        return result
    }
}

// Функция меняет местами два числа на игровом поле, отдает копию игрового поля
fun swapCopy(inputMatrix: Matrix<Int>, a: Pair<Int, Int>, b: Pair<Int, Int>): Matrix<Int> {
    val matrix = (inputMatrix as MatrixImpl<Int>).clone() // Этот костыль тут потому, что нельзя изменять интерфейс в задании
    val temp = matrix[a.second, a.first]
    matrix[a.second, a.first] = matrix[b.second, b.first]
    matrix[b.second, b.first] = temp
    return matrix
}

// Функция нахождения игровых состояний, в которые можно попасть из текущего
fun getNeighbours(current: GameState): Set<GameState> {
    val pos = current.zeroPosition
    val outcomes = mutableSetOf<GameState>()
    val maxCoord = 4
    var previousDistance: Int
    if (pos.first + 1 < maxCoord) {
        previousDistance = current.calculateDistanceSingle(Pair(pos.first + 1, pos.second))
        outcomes.add(
            GameState(
                swapCopy(current.field, pos, Pair(pos.first + 1, pos.second)),
                current.turns + 1,
                current.path + current.field[pos.second, pos.first + 1],
                zeroPosition = Pair(pos.first + 1, pos.second),
//                distance = current.distance + previousDistance - current.calculateDistanceSingle( // Эта оптимизация не работает, пока отключил
//                    pos,
//                    current.field[pos.second, pos.first + 1]
//                )
            )
        )
    }
    if (pos.first - 1 >= 0) {
        previousDistance = current.calculateDistanceSingle(Pair(pos.first - 1, pos.second))
        outcomes.add(
            GameState(
                swapCopy(current.field, pos, Pair(pos.first - 1, pos.second)),
                current.turns + 1,
                current.path + current.field[pos.second, pos.first - 1],
                zeroPosition = Pair(pos.first - 1, pos.second),
//                distance = current.distance + previousDistance - current.calculateDistanceSingle(
//                    pos,
//                    current.field[pos.second, pos.first - 1]
//                )
            )
        )
    }
    if (pos.second + 1 < maxCoord) {
        previousDistance = current.calculateDistanceSingle(Pair(pos.first, pos.second + 1))
        outcomes.add(
            GameState(
                swapCopy(current.field, pos, Pair(pos.first, pos.second + 1)),
                current.turns + 1,
                current.path + current.field[pos.second + 1, pos.first],
                zeroPosition = Pair(pos.first, pos.second + 1),
//                distance = current.distance + previousDistance - current.calculateDistanceSingle(
//                    pos,
//                    current.field[pos.second + 1, pos.first]
//                )
            )
        )
    }
    if (pos.second - 1 >= 0) {
        previousDistance = current.calculateDistanceSingle(Pair(pos.first, pos.second - 1))
        outcomes.add(
            GameState(
                swapCopy(current.field, pos, Pair(pos.first, pos.second - 1)),
                current.turns + 1,
                current.path + current.field[pos.second - 1, pos.first],
                zeroPosition = Pair(pos.first, pos.second - 1),
//                distance = current.distance + previousDistance - current.calculateDistanceSingle(
//                    pos,
//                    current.field[pos.second - 1, pos.first]
//                )
            )
        )
    }
    return outcomes
}

fun fifteenGameSolution(matrix: Matrix<Int>): List<Int> {
    val weightComparator = compareBy<GameState> { it.weight }
    val q = PriorityQueue(weightComparator)
    q.add(GameState(matrix, 0, IntArray(0))) // Вес рассчитывается самостоятельно функцией
    val visited = mutableSetOf<GameState>()
    var current: GameState
    while (q.isNotEmpty()) {
        current = q.poll()
        if (current in visited) continue

        visited.add(current)
        if (current.distance == 0) return current.path.toList()

        for (nextMove in getNeighbours(current)) {
            q.add(nextMove)
        }
    }
    return listOf()
}
