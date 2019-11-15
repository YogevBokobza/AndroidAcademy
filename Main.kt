package demo

import java.math.BigDecimal
import java.time.DayOfWeek
import java.time.LocalDateTime


fun main(args : Array<String>) {
    var report = OrdersAnalyzer();
    var orders = initData()
    var sorted = sortRes(report.totalDailySales(orders))
    printRes(sorted)
}

fun initData() : List<OrdersAnalyzer.Order>
{
    return listOf<OrdersAnalyzer.Order> (
        OrdersAnalyzer.Order
            (554, LocalDateTime.parse ("2017-03-25T10:35:20"),
            listOf<OrdersAnalyzer.OrderLine>
                (
                OrdersAnalyzer.OrderLine (9872,"Pencil",3, 3.00.toBigDecimal())
            )
        ),
        OrdersAnalyzer.Order
            (555, LocalDateTime.parse ("2017-03-25T11:24:20"),
            listOf<OrdersAnalyzer.OrderLine>
                (
                OrdersAnalyzer.OrderLine (9872,"Pencil",2, 3.00.toBigDecimal()),
                OrdersAnalyzer.OrderLine (1746,"Eraser",1, 1.00.toBigDecimal())
            )
        ),
        OrdersAnalyzer.Order
            (453, LocalDateTime.parse ("2017-03-27T14:53:12"),
            listOf<OrdersAnalyzer.OrderLine>
                (
                OrdersAnalyzer.OrderLine (5723,"Pen",4, 4.22.toBigDecimal()),
                OrdersAnalyzer.OrderLine (9872,"Pencil",3, 3.12.toBigDecimal()),
                OrdersAnalyzer.OrderLine (3433,"Erasers Set",1, 6.15.toBigDecimal())
            )
        ),
        OrdersAnalyzer.Order
            (431, LocalDateTime.parse ("2017-03-20T12:15:02"),
            listOf<OrdersAnalyzer.OrderLine>
                (
                OrdersAnalyzer.OrderLine (5723,"Pen",7, 4.22.toBigDecimal()),
                OrdersAnalyzer.OrderLine (3433,"Erasers Set",2, 6.15.toBigDecimal())
            )
        ),
        OrdersAnalyzer.Order
            (690, LocalDateTime.parse ("2017-03-26T11:14:00"),
            listOf<OrdersAnalyzer.OrderLine>
                (
                OrdersAnalyzer.OrderLine (9872,"Pencil",4, 3.12.toBigDecimal()),
                OrdersAnalyzer.OrderLine (4098,"Marker",5, 4.50.toBigDecimal())
            )
        )
    )
}

fun printRes(res : Map<DayOfWeek, Int>) : Unit
{
    var i = 0
    println ("{")
    res.forEach { k, v ->
        print("   \"DayOfWeek.$k\" : $v")
        i++
        if (i < res.size)
            println (",")
        else
            println ("")
    }
    println("}")
}

fun sortRes(res : Map<DayOfWeek, Int>): Map<DayOfWeek, Int>
{
    var newRes = mutableMapOf<DayOfWeek,Int>()
    val days = listOf<DayOfWeek>(DayOfWeek.SUNDAY, DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY)
    days.forEach {
        if (res.containsKey(it))
            newRes.put(it,res.getValue(it))
    }

    return newRes
}

class OrdersAnalyzer {

    data class Order(val orderId: Int, val creationDate: LocalDateTime, val orderLines: List<OrderLine>)

    data class OrderLine(val productId: Int, val name: String, val quantity: Int, val unitPrice: BigDecimal)

    fun totalDailySales(orders: List<Order>): Map<DayOfWeek, Int> {
        var res = mutableMapOf<DayOfWeek,Int>()
        orders.forEach { o ->
            o.orderLines.forEach { ol ->
                if (res.containsKey(o.creationDate.dayOfWeek))
                    res[o.creationDate.dayOfWeek] = res.getValue(o.creationDate.dayOfWeek) + ol.quantity
                else
                    res[o.creationDate.dayOfWeek] = ol.quantity
            }
        }

        return  res
    }
}
