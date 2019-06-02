package gdg.aracaju.view.detail.map

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import gdg.aracaju.news.R
import kotlinx.android.synthetic.main.item_cabs.view.*

class CabTitleEntry : Item() {

    override fun getLayout() = R.layout.item_cabs_title

    override fun bind(viewHolder: ViewHolder, position: Int) = Unit
}

class CabItemEntry(private val item: CabItem, private val onClick: () -> Unit) : Item() {

    override fun getLayout() = R.layout.item_cabs

    override fun bind(viewHolder: ViewHolder, position: Int) {
        with(viewHolder.itemView) {
            imgApp.setImageResource(item.img)
            cabsName.text = item.title
            setOnClickListener { onClick() }
        }
    }
}


