@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1

/**
 * Класс "хеш-таблица с открытой адресацией"
 *
 * Общая сложность задания -- сложная, общая ценность в баллах -- 20.
 * Объект класса хранит данные типа T в виде хеш-таблицы.
 * Хеш-таблица не может содержать равные по equals элементы.
 * Подробности по организации см. статью википедии "Хеш-таблица", раздел "Открытая адресация".
 * Методы: добавление элемента, проверка вхождения элемента, сравнение двух таблиц на равенство.
 * В этом задании не разрешается использовать библиотечные классы HashSet, HashMap и им подобные,
 * а также любые функции, создающие множества (mutableSetOf и пр.).
 *
 * В конструктор хеш-таблицы передаётся её вместимость (максимальное количество элементов)
 */
class OpenHashSet<T>(val capacity: Int) {
    fun hash(element: T) =
        element.hashCode() % capacity

    /**
     * Массив для хранения элементов хеш-таблицы
     */
    internal val elements = Array<Any?>(capacity) { UNINITIALIZED }

    /**
     * Число элементов в хеш-таблице
     */
    var size: Int = 0 
        private set

    /**
     * Признак пустоты
     */
    fun isEmpty(): Boolean = size == 0

    /**
     * Добавление элемента.
     * Вернуть true, если элемент был успешно добавлен,
     * или false, если такой элемент уже был в таблице, или превышена вместимость таблицы.
     */
    fun add(element: T): Boolean {
        if (size == capacity) return false
        val elementHashCode = element.hashCode() % capacity
        if (elements[elementHashCode] == UNINITIALIZED) {
            elements[elementHashCode] = element
        } else { // Коллизия
            for (i in elementHashCode..elementHashCode + capacity) {
                if (elements[i % capacity] == UNINITIALIZED) {
                    elements[i % capacity] = element
                    size++
                    return true
                } else if (elements[i % capacity] == element) return false // Нашелся такой же элемент
            }
        }
        size++
        return true
    }

    /**
     * Проверка, входит ли заданный элемент в хеш-таблицу
     */
    operator fun contains(element: T): Boolean {
        when {
            elements[element.hashCode() % capacity] == UNINITIALIZED -> return false
            elements[element.hashCode() % capacity] == element -> return true
            else -> { // При добавлении произошла
                for (i in element.hashCode() % capacity..element.hashCode() % capacity + capacity) {
                    if (elements[i % capacity] == element) return true
                }
                return false
            }
        }
    }

    /**
     * Таблицы равны, если в них одинаковое количество элементов,
     * и любой элемент из второй таблицы входит также и в первую
     */
    override fun equals(other: Any?): Boolean {
        if (other is OpenHashSet<*> && other.size == size) {
            for (element in other.elements) {
                if (element != UNINITIALIZED && !contains(element as T)) return false
            }
        } else return false
        return true
    }

    override fun hashCode(): Int {
        var result = 1
        for (i in elements) {
            result += if (i != UNINITIALIZED) i.hashCode() else 0
        }
        result = 31 * result + size
        return result
    }

    companion object {
        val UNINITIALIZED = Any()
    }

}