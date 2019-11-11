package wang.relish.whatsnew

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlinx.android.synthetic.main.activity_main_main.*
import wang.relish.whatsnew.n.MainActivity
import wang.relish.whatsnew.q.CameraActivity
import wang.relish.whatsnew.q.StorageActivity

/**
 * @author wangxin
 * @since 20191111
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_main)

        rvSamples.adapter = SampleAdapter(CARDS)
        rvSamples.layoutManager = GridLayoutManager(this, 3)
    }


    companion object {
        val CARDS = listOf(
                Card("n", MainActivity::class.java),
                Card("storage", StorageActivity::class.java),
                Card("camera", CameraActivity::class.java)
        )
    }

    data class Card(
            val name: String,
            val aty: Class<*>
    )

    class SampleAdapter(
            private val mData: List<Card>
    ) : RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(
                    android.R.layout.activity_list_item, parent, false)
            return object : ViewHolder(v) {}
        }

        override fun getItemCount(): Int {
            return mData.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val icon = holder.itemView.findViewById(android.R.id.icon) as View
            icon.visibility = View.GONE
            val textView = holder.itemView.findViewById(android.R.id.text1) as TextView
            textView.text = mData[position].name
            textView.background = ContextCompat.getDrawable(textView.context, R.drawable.btn_bg)
            holder.itemView.setOnClickListener {
                val intent = Intent(it.context, mData[position].aty)
                it.context.startActivity(intent)
            }
        }

    }
}
