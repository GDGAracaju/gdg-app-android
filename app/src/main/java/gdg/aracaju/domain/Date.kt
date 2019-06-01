package gdg.aracaju.domain

import org.threeten.bp.LocalDateTime.parse
import org.threeten.bp.ZoneId

object Date {

    fun getTimeInMillis(date: String, time: String): Long {

        val l = parse("${date}T$time")
        return l.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
}