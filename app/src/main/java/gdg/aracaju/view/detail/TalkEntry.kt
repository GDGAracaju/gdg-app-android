package gdg.aracaju.view.detail

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import gdg.aracaju.domain.model.Talk
import gdg.aracaju.news.R
import kotlinx.android.synthetic.main.item_talk.view.*

class TalkEntry(private val talk: Talk, private val onClick: () -> Unit) : Item() {

    override fun getLayout() = R.layout.item_talk

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.author.text = talk.author
        viewHolder.itemView.title.text = talk.title

        viewHolder.itemView.container.setOnClickListener {
            onClick()
        }
    }
}