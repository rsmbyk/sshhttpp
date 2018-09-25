package com.rsmbyk.sshhttpp.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.rsmbyk.sshhttpp.util.nonNullText

data class Argument (val hyphen: Hyphen?, val option: String?, val parameter: String?) {

    enum class Hyphen (val text: String) {
        @JsonProperty ("single")
        SINGLE ("-"),
        @JsonProperty ("double")
        DOUBLE ("--")
    }

    override fun toString (): String {
        require ((option == null) == (hyphen == null))
        return "${hyphen?.text.nonNullText}${option.nonNullText} ${parameter.nonNullText}".trim ()
    }
}