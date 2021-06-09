package app.rodrigonovoa.dogsrandomfacts

import android.app.Application
import android.app.Dialog
import android.provider.Settings
import android.view.Window
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import app.rodrigonovoa.dogsrandomfacts.common.Prefs
import app.rodrigonovoa.dogsrandomfacts.common.Singleton
import app.rodrigonovoa.dogsrandomfacts.database.FactRoomDb
import app.rodrigonovoa.dogsrandomfacts.database.FactsRepository
import kotlinx.coroutines.*
import java.util.concurrent.CountDownLatch

class DogsRandomFactsApp : Application() {

    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { FactRoomDb.getDatabase(this, applicationScope) }
    val repository by lazy { FactsRepository(database) }

    override fun onCreate() {
        super.onCreate()

        Singleton.setRepository(repository)

        val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

        applicationScope.launch {
            repository.addOpenedNum()
        }
    }
}