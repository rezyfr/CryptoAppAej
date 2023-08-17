package com.rezyfr.cryptoapp.domain.di

import javax.inject.Qualifier

/**
 * We can use this to differentiate multiple implementation of an interface
 * we can also used Named annotation from dagger.hilt.android.qualifiers.Named
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CompositeLoader

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteLoader


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalLoader
