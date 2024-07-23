package com.dnight.calinify.event.entity

import java.util.UUID

/**
 * event의 ics 규격 유일값인 uid를 생성한다.
 *
 * UUID + '@' + 도메인 네임으로 한다.
 */
class EventUID {
    companion object {
        fun genUID() : String {
            return UUID.randomUUID().toString() + "@calinify.com"
        }
    }
}