package cmndtools.dipumba.com.di

import cmndtools.dipumba.com.dao.CustomerIssuesDao
import cmndtools.dipumba.com.dao.CustomerIssuesDaoImpl
import cmndtools.dipumba.com.repository.CustomerIssuesRepository
import org.koin.dsl.module

val appModule = module {
    single { CustomerIssuesRepository(get()) }
    single<CustomerIssuesDao> { CustomerIssuesDaoImpl() }
}