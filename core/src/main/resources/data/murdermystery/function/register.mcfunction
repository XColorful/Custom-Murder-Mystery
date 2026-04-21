# --------Start--------

# 自定义事件类型
# CustomEventType
battleroyale api functionManager registerEvent murdermystery:on_game_player_death_finish true customEventType GAME_PLAYER_DEATH_FINISH_EVENT HIGH false

# 倒计时事件
# CountdownEvent
battleroyale api functionManager registerEvent murdermystery:murdermystery/on_countdown true eventClass "xiao.murdermystery.api.event.custom.murdermystery.CountdownEvent" NORMAL false

# 设置角色事件
# SetRoleEvent
battleroyale api functionManager registerEvent murdermystery:murdermystery/on_survivor_role true eventClass "xiao.murdermystery.api.event.custom.murdermystery.SetRoleEvent$SurvivorRoleEvent" NORMAL false
battleroyale api functionManager registerEvent murdermystery:murdermystery/on_survivor_role_finish true eventClass "xiao.murdermystery.api.event.custom.murdermystery.SetRoleEvent$SurvivorRoleFinishEvent" NORMAL false
battleroyale api functionManager registerEvent murdermystery:murdermystery/on_detective_role true eventClass "xiao.murdermystery.api.event.custom.murdermystery.SetRoleEvent$DetectiveRoleEvent" NORMAL false
battleroyale api functionManager registerEvent murdermystery:murdermystery/on_detective_role_finish true eventClass "xiao.murdermystery.api.event.custom.murdermystery.SetRoleEvent$DetectiveRoleFinishEvent" NORMAL false
battleroyale api functionManager registerEvent murdermystery:murdermystery/on_murderer_role true eventClass "xiao.murdermystery.api.event.custom.murdermystery.SetRoleEvent$MurdererRoleEvent" NORMAL false
battleroyale api functionManager registerEvent murdermystery:murdermystery/on_murderer_role_finish true eventClass "xiao.murdermystery.api.event.custom.murdermystery.SetRoleEvent$MurdererRoleFinishEvent" NORMAL false

# 延迟角色击杀事件
# DelayedRoleKillEvent
battleroyale api functionManager registerEvent murdermystery:murdermystery/on_survivor_kill true eventClass "xiao.murdermystery.api.event.custom.murdermystery.DelayedRoleKillEvent$SurvivorKillEvent" NORMAL false
battleroyale api functionManager registerEvent murdermystery:murdermystery/on_survivor_wrong_kill true eventClass "xiao.murdermystery.api.event.custom.murdermystery.DelayedRoleKillEvent$SurvivorWrongKillEvent" NORMAL false
battleroyale api functionManager registerEvent murdermystery:murdermystery/on_detective_kill true eventClass "xiao.murdermystery.api.event.custom.murdermystery.DelayedRoleKillEvent$DetectiveKillEvent" NORMAL false
battleroyale api functionManager registerEvent murdermystery:murdermystery/on_detective_wrong_kill true eventClass "xiao.murdermystery.api.event.custom.murdermystery.DelayedRoleKillEvent$DetectiveWrongKillEvent" NORMAL false
battleroyale api functionManager registerEvent murdermystery:murdermystery/on_murderer_kill true eventClass "xiao.murdermystery.api.event.custom.murdermystery.DelayedRoleKillEvent$MurdererKillEvent" NORMAL false
battleroyale api functionManager registerEvent murdermystery:murdermystery/on_murderer_wrong_kill true eventClass "xiao.murdermystery.api.event.custom.murdermystery.DelayedRoleKillEvent$MurdererWrongKillEvent" NORMAL false

# --------return--------

# 正常执行
# Command.SINGLE_SUCCESS
return 1