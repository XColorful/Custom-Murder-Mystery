# --------Start--------

# 设置角色事件
# SetRoleEvent
battleroyale api functionManager unregisterEvent murdermystery:murdermystery/on_survivor_role true eventClass "xiao.murdermystery.api.event.custom.murdermystery.SetRoleEvent$SurvivorRoleEvent"
battleroyale api functionManager unregisterEvent murdermystery:murdermystery/on_survivor_role_finish true eventClass "xiao.murdermystery.api.event.custom.murdermystery.SetRoleEvent$SurvivorRoleFinishEvent"
battleroyale api functionManager unregisterEvent murdermystery:murdermystery/on_detective_role true eventClass "xiao.murdermystery.api.event.custom.murdermystery.SetRoleEvent$DetectiveRoleEvent"
battleroyale api functionManager unregisterEvent murdermystery:murdermystery/on_detective_role_finish true eventClass "xiao.murdermystery.api.event.custom.murdermystery.SetRoleEvent$DetectiveRoleFinishEvent"
battleroyale api functionManager unregisterEvent murdermystery:murdermystery/on_murder_role true eventClass "xiao.murdermystery.api.event.custom.murdermystery.SetRoleEvent$MurderRoleEvent"
battleroyale api functionManager unregisterEvent murdermystery:murdermystery/on_murder_role_finish true eventClass "xiao.murdermystery.api.event.custom.murdermystery.SetRoleEvent$MurderRoleFinishEvent"

# 延迟角色击杀事件
# DelayedRoleKillEvent
battleroyale api functionManager unregisterEvent murdermystery:murdermystery/on_survivor_kill true eventClass "xiao.murdermystery.api.event.custom.murdermystery.DelayedRoleKillEvent$SurvivorKillEvent"
battleroyale api functionManager unregisterEvent murdermystery:murdermystery/on_survivor_wrong_kill true eventClass "xiao.murdermystery.api.event.custom.murdermystery.DelayedRoleKillEvent$SurvivorWrongKillEvent"
battleroyale api functionManager unregisterEvent murdermystery:murdermystery/on_detective_kill true eventClass "xiao.murdermystery.api.event.custom.murdermystery.DelayedRoleKillEvent$DetectiveKillEvent"
battleroyale api functionManager unregisterEvent murdermystery:murdermystery/on_detective_wrong_kill true eventClass "xiao.murdermystery.api.event.custom.murdermystery.DelayedRoleKillEvent$DetectiveWrongKillEvent"
battleroyale api functionManager unregisterEvent murdermystery:murdermystery/on_murder_kill true eventClass "xiao.murdermystery.api.event.custom.murdermystery.DelayedRoleKillEvent$MurderKillEvent"
battleroyale api functionManager unregisterEvent murdermystery:murdermystery/on_murder_wrong_kill true eventClass "xiao.murdermystery.api.event.custom.murdermystery.DelayedRoleKillEvent$MurderWrongKillEvent"

# --------return--------

# 正常执行
# Command.SINGLE_SUCCESS
return 1