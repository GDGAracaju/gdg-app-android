package gdg.aracaju.domain

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime.parse
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

object Date {

    private val formatter by lazy { DateTimeFormatter.ofPattern("dd-MM-yyyy") }

    fun getTimeInMillis(date: String, time: String): Long {

        val l = parse("${date}T$time")
        return l.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }


    fun toCurrentFormat(dateString: String): String {
        val date = LocalDate.parse("$dateString")
        return date.format(formatter)
    }
}