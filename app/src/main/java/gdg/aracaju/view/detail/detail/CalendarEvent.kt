package gdg.aracaju.view.detail.detail

import android.content.Intent
import android.provider.CalendarContract.EXTRA_EVENT_BEGIN_TIME
import android.provider.CalendarContract.EXTRA_EVENT_END_TIME
import android.provider.CalendarContract.Events.ALL_DAY
import android.provider.CalendarContract.Events.TITLE
import gdg.aracaju.domain.Date
import gdg.aracaju.domain.model.Detail

class CalendarEvent(private val detailActivity: DetailActivity) {

    fun createNewEvent(detail: Detail) {
        Intent(Intent.ACTION_EDIT).apply {
            type = "vnd.android.cursor.item/event"
            putExtra(TITLE, detail.title)
            putExtra(EXTRA_EVENT_BEGIN_TIME, Date.getTimeInMillis(detail.date, detail.time.start))
            putExtra(EXTRA_EVENT_END_TIME, Date.getTimeInMillis(detail.date, detail.time.end))
            putExtra(ALL_DAY, false)
        }.apply {
            this.resolveActivity(detailActivity.packageManager)?.let {
                detailActivity.startActivity(this)
            }
        }
    }
}