package gdg.aracaju.view.news

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import gdg.aracaju.news.R
import kotlinx.android.synthetic.main.item_news.view.*

class NewsEntry(private val eventName: String) : Item() {

    override fun getLayout() = R.layout.item_news

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.titleEvent.text = eventName
    }
}