package com.tpo_hr.tpohr.di.modules

import com.tpo_hr.tpohr.utils.Authorization
import com.tpo_hr.tpohr.utils.BasicAuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class BAInterceptor {
    @Reusable
    @Provides
    fun provideBasicAuthInterceptor(authorization: Authorization): BasicAuthInterceptor {
        return BasicAuthInterceptor("Bearer ${authorization.accessToken}")
    }


}