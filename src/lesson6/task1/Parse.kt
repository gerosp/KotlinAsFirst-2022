@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import java.util.*
import javax.print.DocFlavor.STRING

// Урок 6: разбор строк, исключения
// Максимальное количество баллов = 13
// Рекомендуемое количество баллов = 11
// Вместе с предыдущими уроками (пять лучших, 2-6) = 40/54

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя (4 балла)
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String = TODO()

/**
 * Средняя (4 балла)
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String = TODO()

/**
 * Средняя (4 балла)
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String =
    if (Regex("^(\\+\\d+)?[\\s-]*(\\([\\d\\s-]+\\))?[\\d-\\s]+\$")
            .matches(phone)
    ) phone.filter { it -> it.isDigit() || it == '+' } else ""


/**
 * Средняя (5 баллов)
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int =
    if (!Regex("^[\\d\\-\\s%]+$").matches(jumps)) -1 else Regex("\\d+")
        .findAll(jumps)
        .maxOfOrNull { it.value.toInt() }
        ?: -1

/**
 * Сложная (6 баллов)
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int =
    if (!Regex("^[+\\d\\-\\s%]+$").matches(jumps)) -1
    else Regex("\\d+\\s%*\\+").findAll(jumps)
        .maxOfOrNull { it.value.split(" ")[0].toInt() }
        ?: -1

/**
 * Сложная (6 баллов)
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int = TODO()

/**
 * Сложная (6 баллов)
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int = TODO()

/**
 * Сложная (6 баллов)
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше нуля либо равны нулю.
 */
fun mostExpensive(description: String): String {
    var mostExpensiveItem = ""
    var maxPrice = -Double.MIN_VALUE
    if (description == "") return ""
    if (Regex("^([а-яА-я]+\\s\\d+(.\\w+)?(;\\s)|$)+$").matches(description)) return ""
    for (i in description.split("; ")) {
        if (i.split(" ")[1].toDouble() > maxPrice) {
            maxPrice = i.split(" ")[1].toDouble()
            mostExpensiveItem = i.split(" ")[0]
        }
    }
    return mostExpensiveItem
}

/**
 * Сложная (6 баллов)
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */


/**
 * Идем по одному символу
 * Если левый символ меньше следующего, то +=следующий - предыдущий
 * Используем тот же самый список из прямой задачи, только инвертированный
 */
fun fromRoman(roman: String): Int {
    var n = 0
    if (roman == "") return -1
    val reverseRomanTable = mutableMapOf<String, Int>()
    val romanTable = mapOf(
        0 to "",
        1 to "I",
        2 to "II",
        3 to "III",
        4 to "IV",
        5 to "V",
        6 to "VI",
        7 to "VII",
        8 to "VIII",
        9 to "IX",
        10 to "X",
        20 to "XX",
        30 to "XXX",
        40 to "XL",
        50 to "L",
        60 to "LX",
        70 to "LXX",
        80 to "LXXX",
        90 to "XC",
        100 to "C",
        200 to "CC",
        300 to "CCC",
        400 to "CD",
        500 to "D",
        600 to "DC",
        700 to "DCC",
        800 to "DCCC",
        900 to "CM",
        1000 to "M",
        2000 to "MM",
        3000 to "MMM",
        4000 to "MMMM"
    )
    for (i in romanTable.keys) {
        reverseRomanTable[romanTable[i]!!] = i
    }

    for (i in roman) {
        if (i.toString() !in reverseRomanTable.keys) return -1
    }
    var shouldSkipDigit = false
    for (i in roman.indices) {
        if (shouldSkipDigit) {
            shouldSkipDigit = false
            continue
        }
        if (i < roman.length - 1) {

            if (reverseRomanTable[roman[i].toString()]!! < reverseRomanTable[roman[i + 1].toString()]!!) {
                if (reverseRomanTable[roman[i + 1].toString()]!! / reverseRomanTable[roman[i].toString()]!! > 10) return -1
                n += reverseRomanTable[roman[i + 1].toString()]!! - reverseRomanTable[roman[i].toString()]!!
                shouldSkipDigit = true
            } else {
                n += reverseRomanTable[roman[i].toString()]!!
            }
        } else {
            n += reverseRomanTable[roman[i].toString()]!!
        }
    }
    return n
}

/**
 * Очень сложная (7 баллов)
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun parseLoops(commands: String): MutableList<Pair<Int, Int>> {
    val pairs = mutableListOf<Pair<Int, Int>>()
    val loopStack = ArrayDeque<Int>()
    for (commandIndex in commands.indices) {
        when (commands[commandIndex]) {
            '[' -> loopStack.push(commandIndex)
            ']' -> if (loopStack.isNotEmpty()) {
                pairs.add(Pair<Int, Int>(loopStack.pop(), commandIndex))

            } else {
                throw IllegalArgumentException()
            }
        }
    }
    if (loopStack.isNotEmpty()) throw IllegalArgumentException()
    return pairs
}

fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    var currentCell = cells / 2
    val cellsList = MutableList<Int>(cells) { 0 }
    val loops = parseLoops(commands)
    var operationCount = 0
    var operation = 0
    var loopCount = 0
    while (operation < commands.length) {
        operationCount++
        if (operationCount > limit) break
        when (commands[operation]) {
            '+' -> cellsList[currentCell] += 1
            '-' -> cellsList[currentCell] -= 1
            '>' -> if (currentCell + 1 < cells) {
                currentCell += 1
            } else {
                throw IllegalStateException()
            }
            '<' -> if (currentCell - 1 >= 0) {
                currentCell -= 1
            } else {
                throw IllegalStateException()
            }
            '[' -> if (cellsList[currentCell] == 0) {
                for ((first, second) in loops) {
                    if (first == operation) {
                        operation = second
                        break
                    }
                }
            }
            ']' -> if (cellsList[currentCell] != 0) {
                for ((first, second) in loops) {
                    if (second == operation) {
                        operation = first
                        break
                    }
                }
            }
            ' ' -> {}
            else -> throw IllegalArgumentException()
        }
        operation++
    }
    return cellsList
}
