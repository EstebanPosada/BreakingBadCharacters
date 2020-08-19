package com.estebanposada.breakingbadtestapp.data

import com.estebanposada.breakingbadtestapp.data.database.Character as DomainCharacter
import com.estebanposada.breakingbadtestapp.data.server.model.Character as ServerCharacter

fun DomainCharacter.toRoom(): ServerCharacter =
    ServerCharacter(
        char_id,
        name,
        birthday,
        occupation,
        img,
        status,
        nickname,
        appearance,
        portrayed,
        category,
        better_call_saul_appearance
    )

fun ServerCharacter.toDomain(): DomainCharacter =
    DomainCharacter(
        char_id,
        name,
        birthday,
        occupation,
        img,
        status,
        nickname,
        appearance,
        portrayed,
        category,
        better_call_saul_appearance,
        false
    )