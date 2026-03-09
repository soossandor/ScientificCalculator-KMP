package hu.calculator.core.types

interface MathValue<T> {
    fun getValue(): T
    fun add(other: MathValue<*>): MathValue<*>
    fun subtract(other: MathValue<*>): MathValue<*>
    fun multiply(other: MathValue<*>): MathValue<*>
    fun divide(other: MathValue<*>): MathValue<*>
}