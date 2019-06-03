package gdg.aracaju.view.detail.detail

import android.content.Intent
import gdg.aracaju.domain.Date
import gdg.aracaju.domain.model.Detail
import gdg.aracaju.news.R

class Sharer(private val activity: DetailActivity) {

    fun share(detail: Detail) {
        val msg =
            "Olá, este é próximo evento do gdg:\n" +
                    "${detail.title}\n" +
                    "Data: ${Date.toCurrentFormat(detail.date)}\n" +
                    "Inscrições em: ${detail.subscriptionUrl}"

        Intent().apply {
            action = Intent.ACTION_SEND
            type = TYPE_TEXT
            putExtra(Intent.EXTRA_TEXT, msg)
            activity.startActivity(
                Intent.createChooser(this, activity.getString(R.string.share_info_title))
            )
        }
    }

    private companion object {
        const val TYPE_TEXT = "text/plain"
    }
}