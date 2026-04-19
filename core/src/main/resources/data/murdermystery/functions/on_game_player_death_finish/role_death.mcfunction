execute store result storage murdermystery:temp isMurderer byte 1 run battleroyale api gameProcessManager murdermystery isMurderer byPlayer @s

# --------Start--------

# 杀手专属
# Murderer only
execute if data storage murdermystery:temp {isMurderer: 1b} run function murdermystery:on_game_player_death_finish/murderer_death

# 生存者&侦探
# Survivor & Detective
execute unless data storage murdermystery:temp {isMurderer: 1b} run function murdermystery:on_game_player_death_finish/survivor_death

# --------return--------

# 清理临时数据
# Clear temp data
data remove storage murdermystery:temp isMurderer

# 正常执行
# Command.SINGLE_SUCCESS
return 1