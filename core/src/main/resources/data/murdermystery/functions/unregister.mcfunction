# --------Start--------

# 设置角色事件
# SetRoleEvent
battleroyale api functionManager unregisterEvent murdermystery:murdermystery/on_survivor_role true eventClass "xiao.murdermystery.api.event.custom.murdermystery.SetRoleEvent$SurvivorRoleEvent"
battleroyale api functionManager unregisterEvent murdermystery:murdermystery/on_survivor_role_finish true eventClass "xiao.murdermystery.api.event.custom.murdermystery.SetRoleEvent$SurvivorRoleFinishEvent"
battleroyale api functionManager unregisterEvent murdermystery:murdermystery/on_detective_role true eventClass "xiao.murdermystery.api.event.custom.murdermystery.SetRoleEvent$DetectiveRoleEvent"
battleroyale api functionManager unregisterEvent murdermystery:murdermystery/on_detective_role_finish true eventClass "xiao.murdermystery.api.event.custom.murdermystery.SetRoleEvent$DetectiveRoleFinishEvent"
battleroyale api functionManager unregisterEvent murdermystery:murdermystery/on_murderer_role true eventClass "xiao.murdermystery.api.event.custom.murdermystery.SetRoleEvent$MurdererRoleEvent"
battleroyale api functionManager unregisterEvent murdermystery:murdermystery/on_murderer_role_finish true eventClass "xiao.murdermystery.api.event.custom.murdermystery.SetRoleEvent$MurdererRoleFinishEvent"

# 延迟角色击杀事件
# DelayedRoleKillEvent
battleroyale api functionManager unregisterEvent murdermystery:murdermystery/on_survivor_kill true eventClass "xiao.murdermystery.api.event.custom.murdermystery.DelayedRoleKillEvent$SurvivorKillEvent"
battleroyale api functionManager unregisterEvent murdermystery:murdermystery/on_survivor_wrong_kill true eventClass "xiao.murdermystery.api.event.custom.murdermystery.DelayedRoleKillEvent$SurvivorWrongKillEvent"
battleroyale api functionManager unregisterEvent murdermystery:murdermystery/on_detective_kill true eventClass "xiao.murdermystery.api.event.custom.murdermystery.DelayedRoleKillEvent$DetectiveKillEvent"
battleroyale api functionManager unregisterEvent murdermystery:murdermystery/on_detective_wrong_kill true eventClass "xiao.murdermystery.api.event.custom.murdermystery.DelayedRoleKillEvent$DetectiveWrongKillEvent"
battleroyale api functionManager unregisterEvent murdermystery:murdermystery/on_murderer_kill true eventClass "xiao.murdermystery.api.event.custom.murdermystery.DelayedRoleKillEvent$MurdererKillEvent"
battleroyale api functionManager unregisterEvent murdermystery:murdermystery/on_murderer_wrong_kill true eventClass "xiao.murdermystery.api.event.custom.murdermystery.DelayedRoleKillEvent$MurdererWrongKillEvent"

# --------return--------

# 正常执行
# Command.SINGLE_SUCCESS
return 1