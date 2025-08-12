package ir.amirroid.mafiauto.update_checker

import android.content.Context
import com.farsitel.bazaar.updater.BazaarUpdater
import com.farsitel.bazaar.updater.UpdateResult
import ir.amirroid.mafiauto.common.app.response.Response
import ir.amirroid.mafiauto.network.response.NetworkErrors
import ir.amirroid.mafiauto.update_checker.models.ReleaseInfo
import kotlinx.coroutines.suspendCancellableCoroutine

class BazaarUpdateCheckerImpl(private val context: Context) : UpdateChecker {
    var needToUpdate = false
    override suspend fun fetchLatestRelease(): Response<ReleaseInfo, NetworkErrors> {
        return suspendCancellableCoroutine { continuation ->
            BazaarUpdater.getLastUpdateState(context) { result ->
                if (continuation.isActive) {
                    val response: Response<ReleaseInfo, NetworkErrors> = when (result) {
                        is UpdateResult.NeedUpdate -> {
                            needToUpdate = true
                            Response.Success(ReleaseInfo.Empty)
                        }

                        is UpdateResult.AlreadyUpdated -> {
                            needToUpdate = false
                            Response.Success(ReleaseInfo.Empty)
                        }

                        else -> Response.Error(NetworkErrors.Unknown)
                    }
                    continuation.resume(response) { _, _, _ -> }
                }
            }
        }
    }

    override fun isUpdateRequired(target: ReleaseInfo): Boolean {
        return needToUpdate
    }
}