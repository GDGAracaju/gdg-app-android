package gdg.aracaju.view.detail.map

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import gdg.aracaju.domain.model.Location
import gdg.aracaju.news.R

object AppTransportManager {

    fun retrieveListOfTransportApps(context: Context, location: Location): List<CabItem> {
        val packageManager = context.packageManager

        val isUberInstalled = isPackageInstalled(uberPackage, packageManager)
        val isNineNineInstalled = isPackageInstalled(nineNinePackage, packageManager)
        val isCabifyInstalled = isPackageInstalled(cabifyPackage, packageManager)

        return mutableListOf<CabItem>().apply {
            if (isCabifyInstalled) add(
                CabItem(
                    title = "Cabify",
                    action = packageManager.getLaunchIntentForPackage(cabifyPackage)!!,
                    img = R.drawable.ic_cabify
                )
            )
            if (isNineNineInstalled) add(
                CabItem(
                    title = "99 Táxi",
                    action = packageManager.getLaunchIntentForPackage(nineNinePackage)!!,
                    img = R.drawable.ic_nine_nine
                )
            )

            if (isUberInstalled) {
                add(
                    CabItem(
                        title = " Uber ",
                        action = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("uber://?action=setPickup&pickup=my_location&dropoff[latitude]=${location.latitude}&dropoff[longitude]=${location.longitude}")
                        ),
                        img = R.drawable.ic_uber
                    )
                )
            }
        }
    }


    private fun isPackageInstalled(packageName: String, packageManager: PackageManager): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private val nineNinePackage = "com.taxis99"
    private val uberPackage = "com.ubercab"
    private val cabifyPackage = "com.cabify.rider"

}