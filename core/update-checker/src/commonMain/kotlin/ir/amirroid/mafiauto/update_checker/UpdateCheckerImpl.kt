package ir.amirroid.mafiauto.update_checker

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import ir.amirroid.mafiauto.common.app.response.Response
import ir.amirroid.mafiauto.common.app.utils.AppInfo
import ir.amirroid.mafiauto.network.call.SafeApiCall
import ir.amirroid.mafiauto.network.response.NetworkErrors
import ir.amirroid.mafiauto.update_checker.models.ReleaseInfo

class UpdateCheckerImpl(
    private val httpClient: HttpClient
) : UpdateChecker {
    override suspend fun fetchLatestRelease(): Response<ReleaseInfo, NetworkErrors> {
        return SafeApiCall.launch { httpClient.get("https://api.github.com/repos/amirroid/mafiauto/releases/latest") }
    }

    override fun isUpdateRequired(current: ReleaseInfo): Boolean {
        return current.tag != AppInfo.version
    }
}