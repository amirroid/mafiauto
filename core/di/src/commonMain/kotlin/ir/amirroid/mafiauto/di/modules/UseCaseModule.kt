package ir.amirroid.mafiauto.di.modules

import ir.amirroid.mafiauto.domain.usecase.role.GetAllRoleDescriptorsUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::GetAllRoleDescriptorsUseCase)
}