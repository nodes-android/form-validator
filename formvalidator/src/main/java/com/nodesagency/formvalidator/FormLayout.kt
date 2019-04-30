package com.nodesagency.formvalidator

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import com.nodesagency.formvalidator.utils.asSequence
import com.nodesagency.formvalidator.base.ValidatableFieldListener
import com.nodesagency.formvalidator.base.FormValidListener
import com.nodesagency.formvalidator.base.Validatable
import com.nodesagency.formvalidator.utils.Logger

class FormLayout @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0) : FrameLayout(context, attributeSet, defStyle), ValidatableFieldListener {



    /**
     * indicates whether form should automatically display all the errors
     */
    private var errorHandlerMode: ErrorHandlerMode = ErrorHandlerMode.Automatic
    private var listener: FormValidListener? = null
    private lateinit var validatableViews: List<Validatable>
    private var childrenResolved: Boolean = false



    init {
        init(attributeSet)
    }



    private fun init(attributeSet: AttributeSet?) {
        attributeSet?.let(this::initFromAttributes)
    }

    private fun initFromAttributes(attributeSet: AttributeSet) {
        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.FormLayout)
        val modeInt = attrs.getInt(R.styleable.FormLayout_errorHandling, 0)
        errorHandlerMode = ErrorHandlerMode.values()[modeInt]
        Logger.log("Erorr: $errorHandlerMode")
        attrs.recycle()
    }

    fun setFormValidListener(block: (Boolean) -> Unit) {
        listener = object : FormValidListener {
            override fun onFormValidityChanged(isValid: Boolean) {
                block.invoke(isValid)
            }
        }
    }



    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (!childrenResolved) {
            validatableViews = resolveValidatableChildren(this)
            validatableViews.forEach { it.addFieldValidListener(this) }
            childrenResolved = true
            Logger.log("Form Inputs count: ${validatableViews.count()}")
        }
    }

    override fun onFieldValidityChanged(validatable: Validatable, isValid: Boolean) {
        val allValid = validatableViews.all { it.validate() }
        listener?.onFormValidityChanged(allValid)
    }

    override fun onInputConfirmed(validatable: Validatable) {
        if (errorHandlerMode  == ErrorHandlerMode.Automatic) {
            validatable.validate(true)
        }
    }

    /**
     * Validates all children and displays the errors
     * @return true if all fields are valid
     */
    fun validateAll() : Boolean {
        return validatableViews.all { it.validate(true) }
    }

    /**
     * Finds all children in the hierarchy that implement Validatable
     * @return list of Validatable from this hierarchy
     */
    private fun resolveValidatableChildren(viewGroup: ViewGroup) : List<Validatable> {
        return viewGroup.asSequence().map {
            when(it) {
                is Validatable -> listOf<Validatable>(it)
                is ViewGroup -> resolveValidatableChildren(it)
                else -> listOf()
            }}.toList().flatten()
    }





}