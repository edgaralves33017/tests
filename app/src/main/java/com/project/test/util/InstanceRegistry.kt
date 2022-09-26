package com.project.test.util

import kotlin.reflect.KClass

/**
 * Saves instances in a map with their [KClass] as key so that they can be accessed and used throughout
 * the application.
 */
object InstanceRegistry {
    private val instances = mutableMapOf<KClass<*>, Any>()

    inline fun <reified T : Any> register(instance: T) = register(T::class, instance)

    fun <T : Any> register(kClass: KClass<T>, instance: T) {
        instances[kClass] = instance
    }

    fun <T : Any> get(kClass: KClass<T>): T = instances[kClass] as T
}

inline fun <reified T : Any> locate() = InstanceRegistry.get(T::class)