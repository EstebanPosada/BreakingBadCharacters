package com.estebanposada.breakingbadtestapp

import com.estebanposada.breakingbadtestapp.data.database.Character as DomainCharacter
import com.estebanposada.breakingbadtestapp.data.server.model.Character as ServerCharacter

val mockedCharacter = ServerCharacter(
    1,
    "mock",
    "09-07-1958",
    listOf("Occupation1"),
    "image",
    "status",
    "nick",
    listOf(1),
    "portrayed",
    "category",
    listOf(1)
)

val mockedLocalCharacter = DomainCharacter(
    1,
    "mock",
    "09-07-1958",
    listOf("Occupation1"),
    "image",
    "status",
    "nick",
    listOf(1),
    "portrayed",
    "category",
    listOf(1),
    true
)