package com.sdbfof.yugiohdeckbuilder.utils

enum class CardType {
    NAME,
    MONSTER,
    EXTRA,
    SPELL,
    TRAP,
    NO_TYPE
}

enum class DeckType {
    MAIN,
    EXTRA
}

enum class AccountStatus {
    EXISTS,
    SUBMITTING,
    SUBMITTED,
    CANCELED,
    CREATION_ERROR,
    SIGN_IN_ERROR,
    EMAIL_EXISTS,
    USERNAME_EXISTS,
    SIGNED_IN,
    SIGNED_OUT
}