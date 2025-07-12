package ir.amirroid.mafiauto.data.repository.update

import ir.amirroid.mafiauto.common.app.error.ErrorI
import ir.amirroid.mafiauto.common.app.response.Response
import ir.amirroid.mafiauto.common.app.response.map
import ir.amirroid.mafiauto.data.mappers.release.toDomain
import ir.amirroid.mafiauto.domain.model.settings.UpdateInfo
import ir.amirroid.mafiauto.domain.repository.UpdateRepository
import ir.amirroid.mafiauto.update_checker.UpdateChecker

class UpdateRepositoryImpl(
    private val updateChecker: UpdateChecker
) : UpdateRepository {
    override suspend fun getLatestUpdateInfo(): Response<UpdateInfo, ErrorI> {
        return updateChecker.fetchLatestRelease().map {
            UpdateInfo(
                needToUpdate = updateChecker.isUpdateRequired(it),
                release = it.toDomain()
            )
        }
    }
}