package com.nodesagency.formvalidator.base


interface Validatable<T> {

    var formErrorMessageResolver: FormErrorMessageResolver

    var formErrorMessageHandler: FormErrorMessageHandler?


    var isRequired: Boolean

    /**
     * Validates the the field
     * @return true if field is valid, false otherwise
     * @param showError indicates whether field should display the error in case of the bad input
     */
    fun validate(showError: Boolean = false): Boolean

    /**
     * Adds a listener for this field
     * @param listenerValidatable listener to add
     */
    fun addFieldValidListener(listenerValidatable: ValidatableFieldListener)


    /**
     * Displays a field error message
     * @param message - error message to be displayed
     */
    fun showError(message: String)

    /**
     * Removes error message
     */
    fun clearError()

    /**
     * Clears the field input
     */
    fun clear()


    /**
     * Retrieve validatable field value
     * @return  Pair with id of the view and value
     */
    fun getInputValue() : Pair<Int, T?>

    /**
     * Set Input value for validatable field
     * @param value - value of type T to be set
     */
    fun setInputValue(value: T?)

}