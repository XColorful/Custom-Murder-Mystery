execute store result storage murdermystery:temp hasRole byte 1 run battleroyale api gameProcessManager murdermystery hasRole byPlayer @s
execute store result storage murdermystery:temp isMurderer byte 1 run battleroyale api gameProcessManager murdermystery isMurderer byPlayer @s

# --------Start--------

# 在设置阵营之前
# Before set role
execute if data storage murdermystery:temp {hasRole: 0b} run function murdermystery:sounds/countdown_sound

# 杀手专属
# Murderer only
execute if data storage murdermystery:temp {isMurderer: 1b} run function murdermystery:sounds/heartbeat_sound

# --------return--------

# 清理临时数据
# Clear temp data
data remove storage murdermystery:temp hasRole
data remove storage murdermystery:temp isMurderer

# 正常执行
# Command.SINGLE_SUCCESS
return 1