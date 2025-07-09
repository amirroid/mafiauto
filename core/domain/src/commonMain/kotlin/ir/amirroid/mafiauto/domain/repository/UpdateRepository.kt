package ir.amirroid.mafiauto.domain.repository

import ir.amirroid.mafiauto.common.app.error.ErrorI
import ir.amirroid.mafiauto.common.app.response.Response
import ir.amirroid.mafiauto.domain.model.UpdateInfo

interface UpdateRepository {
    suspend fun getLatestUpdateInfo(): Response<UpdateInfo, ErrorI>
}