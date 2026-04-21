execute store result storage murdermystery:temp hasRole byte 1 run battleroyale api gameProcessManager murdermystery hasRole byPlayer @s

# --------Start--------

execute if data storage murdermystery:temp {hasRole: 1b} run function murdermystery:on_game_player_death_finish/role_death

# --------return--------

# 清理临时数据
# Clear temp data
data remove storage murdermystery:temp hasRole

# 正常执行
# Command.SINGLE_SUCCESS
return 1