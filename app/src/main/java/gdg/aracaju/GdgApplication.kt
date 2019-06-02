package gdg.aracaju

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class GdgApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this);
    }
}