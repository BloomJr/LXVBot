package moderation

import LXVBot
import dev.kord.common.entity.Snowflake
import dev.kord.core.event.message.MessageCreateEvent

val LEVEL_ROLE_IDS = listOf(
    Snowflake(714402516015513691),
    Snowflake(714402563075604560),
    Snowflake(714402593735966770),
    Snowflake(714402631308804146),
    Snowflake(714402669254410240),
    Snowflake(806927052309659710),
    Snowflake(806927057845354496),
    Snowflake(806927061297004595),
    Snowflake(806927065022070835),
    Snowflake(806927068255879199),
)

const val CAMERA_ROLE_ID = 714185072911056956

suspend fun LXVBot.handleMee6LevelUpMessage(mCE: MessageCreateEvent) {
    val msg = mCE.message.content
    if (!msg.startsWith("GG!")) {
        println("Something wrong happened with reading mee6's level up message: gg")
        return
    }
    val args = msg.split(" ")
    val user = LXVBot.getUserIdFromString(args[1].dropLast(1))
    if (user == null) {
        println("Something wrong happened with reading mee6's level up message: user parsing")
        return
    }
    val level = args.last().dropLast(1).toIntOrNull()
    if (level == null) {
        println("Something wrong happened with reading mee6's level up message: level parsing")
        return
    }
    if (level >= 3) {
        val member = client.getUser(Snowflake(user))?.asMemberOrNull(mCE.guildId!!)
        if (member == null) {
            println("Could not find that user in this guild")
            return
        }
        val curRoles = member.roleIds

        if(Snowflake(CAMERA_ROLE_ID) !in curRoles){
            member.addRole(Snowflake(CAMERA_ROLE_ID), "They reached level 3 or higher in Mee6")
        }


        if (level >= 10) {
            val rank = LEVEL_ROLE_IDS[if (level >= 100) 9 else (level / 10) - 1]


            for (role in LEVEL_ROLE_IDS) {
                if (role == rank) {
                    if (role !in curRoles) {
                        member.addRole(role, "They reached the needed Mee6 level for this role")
                    }
                } else if (role in curRoles) {
                    member.removeRole(
                        role,
                        if (role < rank) "They were eligible for a higher rank role"
                        else "They didn't have the needed Mee6 level for this role"
                    )
                }
            }
        }
    }
}