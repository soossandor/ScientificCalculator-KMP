package hu.calculator.core.types

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import com.ionspin.kotlin.bignum.decimal.DecimalMode
import com.ionspin.kotlin.bignum.decimal.RoundingMode

class RealNumber(val value: BigDecimal) : MathValue<RealNumber> {

    companion object {
        val MC = DecimalMode(100L, RoundingMode.ROUND_HALF_AWAY_FROM_ZERO)
    }

    // JAVÍTVA: A parseString 2. paramétere a számrendszer (10)
    constructor(valString: String) : this(BigDecimal.parseString(valString, 10))
    constructor(doubleVal: Double) : this(BigDecimal.fromDouble(doubleVal, MC))

    override fun getValue(): RealNumber = this

    fun asDouble(): Double = value.toStringExpanded().toDouble()

    override fun add(other: MathValue<*>): MathValue<*> = RealNumber(this.value.add((other as RealNumber).value, MC))
    override fun subtract(other: MathValue<*>): MathValue<*> = RealNumber(this.value.subtract((other as RealNumber).value, MC))
    override fun multiply(other: MathValue<*>): MathValue<*> = RealNumber(this.value.multiply((other as RealNumber).value, MC))
    override fun divide(other: MathValue<*>): MathValue<*> = RealNumber(this.value.divide((other as RealNumber).value, MC))

    override fun toString(): String = value.toStringExpanded()
}