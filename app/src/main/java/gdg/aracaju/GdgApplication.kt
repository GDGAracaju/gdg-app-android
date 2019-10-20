package gdg.aracaju

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import gdg.aracaju.data.di.dataModule
import gdg.aracaju.view.di.viewModule
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.conf.ConfigurableKodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

class GdgApplication : Application(), KodeinAware {

    private val appModule = Kodein.Module(name = "application"){
        bind() from provider {
            this@GdgApplication as Application
        }
    }

    override val kodein: Kodein = ConfigurableKodein(mutable = true).apply {
        addImport(appModule)
        addImport(dataModule)
        addImport(viewModule)
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}