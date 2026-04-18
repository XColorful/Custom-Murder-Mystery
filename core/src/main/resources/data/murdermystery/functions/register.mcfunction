# --------Start--------

# 设置角色事件
# SetRoleEvent
battleroyale api functionManager registerEvent murdermystery:murdermystery/on_survivor_role true eventClass "xiao.murdermystery.api.event.custom.murdermystery.SetRoleEvent$SurvivorRoleEvent" NORMAL false
battleroyale api functionManager registerEvent murdermystery:murdermystery/on_survivor_role_finish true eventClass "xiao.murdermystery.api.event.custom.murdermystery.SetRoleEvent$SurvivorRoleFinishEvent" NORMAL false
battleroyale api functionManager registerEvent murdermystery:murdermystery/on_detective_role true eventClass "xiao.murdermystery.api.event.custom.murdermystery.SetRoleEvent$DetectiveRoleEvent" NORMAL false
battleroyale api functionManager registerEvent murdermystery:murdermystery/on_detective_role_finish true eventClass "xiao.murdermystery.api.event.custom.murdermystery.SetRoleEvent$DetectiveRoleFinishEvent" NORMAL false
battleroyale api functionManager registerEvent murdermystery:murdermystery/on_murder_role true eventClass "xiao.murdermystery.api.event.custom.murdermystery.SetRoleEvent$MurderRoleEvent" NORMAL false
battleroyale api functionManager registerEvent murdermystery:murdermystery/on_murder_role_finish true eventClass "xiao.murdermystery.api.event.custom.murdermystery.SetRoleEvent$MurderRoleFinishEvent" NORMAL false

# 延迟角色击杀事件
# DelayedRoleKillEvent
battleroyale api functionManager registerEvent murdermystery:murdermystery/on_survivor_kill true eventClass "xiao.murdermystery.api.event.custom.murdermystery.DelayedRoleKillEvent$SurvivorKillEvent" NORMAL false
battleroyale api functionManager registerEvent murdermystery:murdermystery/on_survivor_wrong_kill true eventClass "xiao.murdermystery.api.event.custom.murdermystery.DelayedRoleKillEvent$SurvivorWrongKillEvent" NORMAL false
battleroyale api functionManager registerEvent murdermystery:murdermystery/on_detective_kill true eventClass "xiao.murdermystery.api.event.custom.murdermystery.DelayedRoleKillEvent$DetectiveKillEvent" NORMAL false
battleroyale api functionManager registerEvent murdermystery:murdermystery/on_detective_wrong_kill true eventClass "xiao.murdermystery.api.event.custom.murdermystery.DelayedRoleKillEvent$DetectiveWrongKillEvent" NORMAL false
battleroyale api functionManager registerEvent murdermystery:murdermystery/on_murder_kill true eventClass "xiao.murdermystery.api.event.custom.murdermystery.DelayedRoleKillEvent$MurderKillEvent" NORMAL false
battleroyale api functionManager registerEvent murdermystery:murdermystery/on_murder_wrong_kill true eventClass "xiao.murdermystery.api.event.custom.murdermystery.DelayedRoleKillEvent$MurderWrongKillEvent" NORMAL false

# --------return--------

# 正常执行
# Command.SINGLE_SUCCESS
return 1