package com.rsmbyk.sshhttpp.model

import com.rsmbyk.sshhttpp.util.nonNullText

class Argument (private val hyphen: Hyphen?, private val option: String?, private val parameter: String?) {

    enum class Hyphen (val text: String) {
        SINGLE ("-"),
        DOUBLE ("--")
    }

    override fun toString (): String {
        require ((option == null) == (hyphen == null))
        return "${hyphen?.text.nonNullText}${option.nonNullText} ${parameter.nonNullText}".trim ()
    }
}
