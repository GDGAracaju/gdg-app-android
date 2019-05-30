package gdg.aracaju.view.detail

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import gdg.aracaju.domain.model.Location

object AppTransportManager {

    fun retrieveListOfTransportApps(context: Context, location: Location): List<Transport> {
        val packageManager = context.packageManager

        val isUberInstalled = isPackageInstalled(uberPackage, packageManager)
        val isNineNineInstalled = isPackageInstalled(nineNinePackage, packageManager)
        val isCabifyInstalled = isPackageInstalled(cabifyPackage, packageManager)

        return mutableListOf<Transport>().apply {
            if (isCabifyInstalled) add(
                Transport(
                    nameApp = "Cabify",
                    intent = packageManager.getLaunchIntentForPackage(cabifyPackage)!!
                )
            )
            if (isNineNineInstalled) add(
                Transport(
                    nameApp = "99 Táxi",
                    intent = packageManager.getLaunchIntentForPackage(nineNinePackage)!!
                )
            )

            if (isUberInstalled) {
                add(
                    Transport(
                        nameApp = " Uber ",
                        intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("uber://?action=setPickup&pickup=my_location&dropoff[latitude]=${location.latitude}&dropoff[longitude]=${location.longitude}")
                        )
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

    data class Transport(
        val nameApp: String,
        val intent: Intent
    )

    private val nineNinePackage = "com.taxis99"
    private val uberPackage = "com.ubercab"
    private val cabifyPackage = "com.cabify.rider"

}