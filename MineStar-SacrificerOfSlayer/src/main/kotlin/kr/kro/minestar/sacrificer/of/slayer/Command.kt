package kr.kro.minestar.sacrificer.of.slayer

import kr.kro.minestar.sacrificer.of.slayer.Main.Companion.prefix
import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Sacrificer
import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Slayer
import kr.kro.minestar.sacrificer.of.slayer.functions.WorldClass
import kr.kro.minestar.utility.command.Argument
import kr.kro.minestar.utility.command.FunctionalCommand
import kr.kro.minestar.utility.string.removeUnderBar
import kr.kro.minestar.utility.string.toPlayer
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.io.File

object Command : FunctionalCommand {
    private enum class Arg(override val howToUse: String) : Argument {
    }

    private enum class OpArg(override val howToUse: String) : Argument {
        creature("[slayer/sacrificer] {CreatureName} {PlayerName}"),
        world("[create/open/save] <WorldName>"),
        start("<WorldName>"),
        test(""),
    }

    override fun commanding(player: CommandSender, cmd: Command, label: String, args: Array<out String>) {
        if (player !is Player) return

        if (args.isEmpty()) return "$prefix $label".toPlayer(player)

        val arg = argument(Arg.values(), args) ?: if (player.isOp) argument(OpArg.values(), args) ?: return else return

        if (!arg.isValid(args)) return "$prefix §c${arg.howToUse(label)}".toPlayer(player)

        when (arg) {
            OpArg.test -> {
            }
            OpArg.creature -> {
                val target = if (args.lastIndex == 3) Bukkit.getPlayer(args.last())
                    ?: return "$prefix §c플레이어를 찾을 수 없습니다.".toPlayer(player)
                else player

                val designWorld = WorldClass.getDesignWorld(target.world)
                    ?: return "$prefix §c대상이 디자인 월드에 있지 않습니다.".toPlayer(player)

                if (args.lastIndex == 1) return when (args[1]) {
                    "slayer" -> designWorld.setSlayer(player, null)
                    "sacrificer" -> designWorld.setSacrificer(player, null)
                    else -> "$prefix §c알 수 없는 크리처 타입입니다.".toPlayer(player)
                }

                when (args[1]) {
                    "slayer" -> {
                        try {
                            designWorld.setSlayer(target, Slayer.valueOf(args[2]))
                        } catch (_: Exception) {
                            return "$prefix §c알 수 없는 슬레이어입니다.".toPlayer(player)
                        }
                    }
                    "sacrificer" -> {
                        try {
                            designWorld.setSacrificer(target, Sacrificer.valueOf(args[2]))
                        } catch (_: Exception) {
                            return "$prefix §c알 수 없는 세크리파이서입니다.".toPlayer(player)
                        }
                    }
                    else -> return "$prefix §c알 수 없는 크리처 타입입니다.".toPlayer(player)
                }
            }
            OpArg.world -> {
                when (args[1]) {
                    "open", "create" -> {
                        val worldName = WorldClass.convertUnicode(args.last())
                        val designWorld = WorldClass.enableDesignWorld(worldName)
                            ?: return "$prefix §c월드 생성에 실패 하였습니다.".toPlayer(player)

                        player.gameMode = GameMode.CREATIVE
                        player.isFlying = true
                        player.teleport(designWorld.world.spawnLocation)
                        if (args[1] == "create") "$prefix §e${args.last().removeUnderBar()} §a월드를 생성 하였습니다.".toPlayer(player)
                        else "$prefix §e${args.last().removeUnderBar()} §a월드를 열었습니다.".toPlayer(player)
                        "[§a월드 코드§f] §e$worldName".toPlayer(player)
                    }
                    "save" -> {
                        val worldName = WorldClass.convertUnicode(args.last())
                        val world = Bukkit.getWorld(worldName)
                            ?: return "$prefix §c활성화 되어 있지 않거나 존재하지 않는 월드입니다.".toPlayer(player)
                        val designWorld = WorldClass.getDesignWorld(world)
                            ?: return "$prefix §c해당 월드는 디자인월드가 아닙니다.".toPlayer(player)
                        designWorld.save()
                        "$prefix §e${args.last().removeUnderBar()} §a을/를 저장하였습니다.".toPlayer(player)
                        "[§a월드 코드§f] §e$worldName".toPlayer(player)
                    }
                    else -> return "$prefix §c${arg.howToUse(label)}".toPlayer(player)
                }
            }

            OpArg.start -> {
                val worldName = WorldClass.convertUnicode(args.last())
                WorldClass.enableRacingWorld(worldName) ?: "$prefix §c존재하지 않는 월드이거나 레이싱이 진행 중입니다.".toPlayer(player)
            }
        }
        return
    }

    override fun onTabComplete(player: CommandSender, cmd: Command, alias: String, args: Array<out String>): List<String> {
        if (player !is Player) return listOf()

        val list = mutableListOf<String>()

        val arg = argument(Arg.values(), args) ?: if (player.isOp) argument(OpArg.values(), args) else null
        val lastIndex = args.lastIndex
        val last = args.lastOrNull() ?: ""

        fun List<String>.add() {
            for (s in this) if (s.contains(last)) list.add(s)
        }

        fun Array<out Enum<*>>.add() {
            for (s in this) if (s.name.lowercase().contains(last)) list.add(s.name)
        }

        fun List<File>.add() {
            for (s in this) if (WorldClass.readUnicode(s.name).contains(last)) list.add(WorldClass.readUnicode(s.name))
        }

        fun playerAdd() {
            for (s in Bukkit.getOnlinePlayers()) if (s.name.contains(last)) list.add(s.name)
        }

        if (arg == null) {
            Arg.values().add()
            if (player.isOp) OpArg.values().add()
        } else when (arg) {
            OpArg.creature -> when (lastIndex) {
                1 -> arg.argList(lastIndex).add()
                2 -> when(args[1]) {
                    "slayer" -> Slayer.values().add()
                    "sacrificer" -> Sacrificer.values().add()
                }
                3 -> playerAdd()
            }

            OpArg.world -> when (lastIndex) {
                1 -> arg.argList(lastIndex).add()
                2 -> {
                    if (args[1] == "save") WorldClass.worldList().add()
                    if (args[1] == "open") WorldClass.worldList().add()
                }
            }
            OpArg.start -> when (lastIndex) {
                1 -> WorldClass.worldList().add()
            }

            OpArg.test -> {}
        }
        return list
    }
}