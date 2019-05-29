package gdg.aracaju.view.news

import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import gdg.aracaju.domain.model.Event
import gdg.aracaju.news.R
import kotlinx.android.synthetic.main.item_news.view.*

internal class NewsEntry(private val event: Event, private val onClick: () -> Unit) : Item() {

    override fun getLayout() = R.layout.item_news

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.titleEvent.text = event.nameEvent
        viewHolder.itemView.eventDate.text = event.date
        viewHolder.itemView.eventTime.text = event.time
        Picasso.get().load(event.imgUrl).into(viewHolder.itemView.imageEvent)

        viewHolder.itemView.card.setOnClickListener {
            onClick()
        }
    }
}