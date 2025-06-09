package ir.amirroid.mafiauto.di.modules

import ir.amirroid.mafiauto.data.repository.RoleRepositoryImpl
import ir.amirroid.mafiauto.domain.repository.RoleRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    factoryOf(::RoleRepositoryImpl).bind<RoleRepository>()
}