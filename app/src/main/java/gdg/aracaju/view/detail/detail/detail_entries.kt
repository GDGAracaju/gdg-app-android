package gdg.aracaju.view.detail.detail

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import gdg.aracaju.domain.model.Talk
import gdg.aracaju.news.R
import gdg.aracaju.view.bold
import gdg.aracaju.view.normal
import gdg.aracaju.view.plus
import kotlinx.android.synthetic.main.item_header_detail.view.*
import kotlinx.android.synthetic.main.item_talk.view.*

class TalkEntry(private val talk: Talk, private val onClick: () -> Unit) : Item() {

    override fun getLayout() = R.layout.item_talk

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.author.text = talk.author
        viewHolder.itemView.title.text = talk.title
        viewHolder.itemView.time.text = talk.time

        viewHolder.itemView.container.setOnClickListener {
            onClick()
        }
    }
}

class LabelEntry : Item() {

    override fun getLayout() = R.layout.item_talk_title

    override fun bind(viewHolder: ViewHolder, position: Int) = Unit
}


class HeaderEntry(private val header: Header) : Item() {

    override fun getLayout() = R.layout.item_header_detail

    override fun bind(viewHolder: ViewHolder, position: Int) {
        with(viewHolder.itemView) {
            val schedule = bold("${context.getString(R.string.label_date)} ") +
                    normal("${header.date} ") +
                    bold("${context.getString(R.string.label_hour)} ") +
                    normal("${header.time.start} - ${header.time.end}")

            eventTitle.text = header.title
            scheduleEvent.text = schedule
            addressEvent.text = header.address
            description.text = header.description
        }
    }
}