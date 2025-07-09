package ir.amirroid.mafiauto.update_checker

import ir.amirroid.mafiauto.common.app.response.Response
import ir.amirroid.mafiauto.network.response.NetworkErrors
import ir.amirroid.mafiauto.update_checker.models.ReleaseInfo

interface UpdateChecker {
    suspend fun fetchLatestRelease(): Response<ReleaseInfo, NetworkErrors>
    fun isUpdateRequired(current: ReleaseInfo): Boolean
}