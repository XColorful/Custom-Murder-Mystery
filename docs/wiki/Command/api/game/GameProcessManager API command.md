[English](#English)

### 游戏进程管理器
> _IGameProcessManager_

> _/battleroyale api gameProcessManager [...]_

### 游戏进程管理器（谁是杀手）
> _IMurderMysteryProcessManager_

> _/battleroyale api gameProcessManager murdermystery [...]_

- 若未[注册谁是杀手游戏进程管理器](https://github.com/XColorful/Custom-Murder-Mystery/wiki/Register-command#注册谁是杀手游戏进程管理器)，`返回值`为 -1

#### 谁是杀手游戏管理
> _IMurderMysteryGameManagement_

##### 设置生存者
> _/battleroyale api gameProcessManager murdermystery setSurvivor byPlayer [player]_
> 
> _/battleroyale api gameProcessManager murdermystery setSurvivor byId [id]_

- player：用实体选择器选中并获取游戏玩家
- id：用游戏玩家ID获取游戏玩家
- 若不存在对应的游戏玩家或游戏队伍，`返回值`为 -2
- `返回值`：是否设置成功

##### 设置侦探
> _/battleroyale api gameProcessManager murdermystery setDetective byPlayer [player]_
> 
> _/battleroyale api gameProcessManager murdermystery setDetective byId [id]_

侦探同属于生存者阵营：
- player：用实体选择器选中并获取游戏玩家
- id：用游戏玩家ID获取游戏玩家
- 若不存在对应的游戏玩家或游戏队伍，`返回值`为 -2
- `返回值`：是否设置成功

##### 设置杀手
> _/battleroyale api gameProcessManager murdermystery setMurder byPlayer [player]_
> 
> _/battleroyale api gameProcessManager murdermystery setMurder byId [id]_

- player：用实体选择器选中并获取游戏玩家
- id：用游戏玩家ID获取游戏玩家
- 若不存在对应的游戏玩家或游戏队伍，`返回值`为 -2
- `返回值`：是否设置成功

#### 谁是杀手信息获取
> _IMurderMysteryInfoGetter_

##### 获取阵营是否被淘汰
> _/battleroyale api gameProcessManager murdermystery isTeamEliminated byPlayer [player]_
> 
> _/battleroyale api gameProcessManager murdermystery isTeamEliminated byId [id]_

- player：用实体选择器选中并获取游戏玩家
- id：用游戏玩家ID获取游戏玩家
- 若不存在对应的游戏玩家或游戏队伍，`返回值`为 -2
- `返回值`：该游戏玩家所属阵营是否被淘汰

##### 是否已有角色
> _/battleroyale api gameProcessManager murdermystery hasRole byPlayer [player]_
> 
> _/battleroyale api gameProcessManager murdermystery hasRole byId [id]_

- player：用实体选择器选中并获取游戏玩家
- id：用游戏玩家ID获取游戏玩家
- 若不存在对应的游戏玩家或游戏队伍，`返回值`为 -2
- `返回值`：是否属于任一阵营

###### 是否为生存者
> _/battleroyale api gameProcessManager murdermystery isSurvivor byPlayer [player]_
> 
> _/battleroyale api gameProcessManager murdermystery isSurvivor byId [id]_

侦探同属于生存者阵营：
- player：用实体选择器选中并获取游戏玩家
- id：用游戏玩家ID获取游戏玩家
- 若不存在对应的游戏玩家或游戏队伍，`返回值`为 -2
- `返回值`：是否属于生存者阵营

###### 是否为侦探
> _/battleroyale api gameProcessManager murdermystery isDetective byPlayer [player]_
> 
> _/battleroyale api gameProcessManager murdermystery isDetective byId [id]_

- player：用实体选择器选中并获取游戏玩家
- id：用游戏玩家ID获取游戏玩家
- 若不存在对应的游戏玩家或游戏队伍，`返回值`为 -2
- `返回值`：是否属于侦探阵营

###### 是否为杀手
> _/battleroyale api gameProcessManager murdermystery isMurder byPlayer [player]_
> 
> _/battleroyale api gameProcessManager murdermystery isMurder byId [id]_

- player：用实体选择器选中并获取游戏玩家
- id：用游戏玩家ID获取游戏玩家
- 若不存在对应的游戏玩家或游戏队伍，`返回值`为 -2
- `返回值`：是否属于杀手阵营

##### 获取生存者总数
> _/battleroyale api gameProcessManager murdermystery getSurvivorSize_

侦探同属于生存者阵营：
- `返回值`：当前生存者总数

##### 获取侦探总数
> _/battleroyale api gameProcessManager murdermystery getDetectiveSize_

- `返回值`：当前侦探总数

##### 获取杀手总数
> _/battleroyale api gameProcessManager murdermystery getMurderSize_

- `返回值`：当前杀手总数

##### 获取未被淘汰的生存者数量
> _/battleroyale api gameProcessManager murdermystery getStandingSurvivorSize_

侦探同属于生存者阵营：
- `返回值`：当前未被淘汰的生存者数量

##### 获取未被淘汰的侦探数量
> _/battleroyale api gameProcessManager murdermystery getStandingDetectiveSize_

- `返回值`：当前未被淘汰的侦探数量

##### 获取未被淘汰的杀手数量
> _/battleroyale api gameProcessManager murdermystery getStandingMurderSize_

- `返回值`：当前未被淘汰的杀手数量

# English

### Game process manager
> _IGameProcessManager_

> _/battleroyale api IGameProcessManager [...]_

### Game process manager (MurderMystery)
> _IDeathMatchProcessManager_

> _/battleroyale api gameProcessManager murdermystery [...]_

- If not [Register MurderMystery GameProcessManager](https://github.com/XColorful/Custom-Murder-Mystery/wiki/Register-command#Register-MurderMystery-GameProcessManager), the `return value` is -1.

#### MurderMystery game management
> _IMurderMysteryGameManagement_

##### Set survivor
> _/battleroyale api gameProcessManager murdermystery setSurvivor byPlayer [player]_
> 
> _/battleroyale api gameProcessManager murdermystery setSurvivor byId [id]_

- player: selects game player using an entity selector
- id: selects game player using a game player ID
- If the game player does not exist, the `return value` is -2.
- `return value`: whether the set was successful

##### Set detective
> _/battleroyale api gameProcessManager murdermystery setDetective byPlayer [player]_
> 
> _/battleroyale api gameProcessManager murdermystery setDetective byId [id]_

The detective also belongs to the survivor team:
- player: selects game player using an entity selector
- id: selects game player using a game player ID
- If the game player does not exist, the `return value` is -2.
- `return value`: whether the set was successful

##### Set murder
> _/battleroyale api gameProcessManager murdermystery setMurder byPlayer [player]_
> 
> _/battleroyale api gameProcessManager murdermystery setMurder byId [id]_

- player: selects game player using an entity selector
- id: selects game player using a game player ID
- If the game player does not exist, the `return value` is -2.
- `return value`: whether the set was successful

#### MurderMystery info getter
> _IMurderMysteryInfoGetter_

##### Is team eliminated
> _/battleroyale api gameProcessManager murdermystery isTeamEliminated byPlayer [player]_
> 
> _/battleroyale api gameProcessManager murdermystery isTeamEliminated byId [id]_

- player: selects game player using an entity selector
- id: selects game player using a game player ID
- If the game player does not exist, the `return value` is -2.
- `return value`: whether the team the game player belongs to is eliminated

##### Has role
> _/battleroyale api gameProcessManager murdermystery hasRole byPlayer [player]_
> 
> _/battleroyale api gameProcessManager murdermystery hasRole byId [id]_

The detective also belongs to the survivor team:
- player: selects game player using an entity selector
- id: selects game player using a game player ID
- If the game player does not exist, the `return value` is -2.
- `return value`: whether the player belongs to any team

###### Is survivor
> _/battleroyale api gameProcessManager murdermystery isSurvivor byPlayer [player]_
> 
> _/battleroyale api gameProcessManager murdermystery isSurvivor byId [id]_

The detective also belongs to the survivor team:
- player: selects game player using an entity selector
- id: selects game player using a game player ID
- If the game player does not exist, the `return value` is -2.
- `return value`: whether the player belongs to the survivor team

###### Is detective
> _/battleroyale api gameProcessManager murdermystery isDetective byPlayer [player]_
> 
> _/battleroyale api gameProcessManager murdermystery isDetective byId [id]_

- player: selects game player using an entity selector
- id: selects game player using a game player ID
- If the game player does not exist, the `return value` is -2.
- `return value`: whether the player belongs to the detective team

###### Is murder
> _/battleroyale api gameProcessManager murdermystery isMurder byPlayer [player]_
> 
> _/battleroyale api gameProcessManager murdermystery isMurder byId [id]_

- player: selects game player using an entity selector
- id: selects game player using a game player ID
- If the game player does not exist, the `return value` is -2.
- `return value`: whether the player belongs to the murder team

##### Get survivor size
> _/battleroyale api gameProcessManager murdermystery getSurvivorSize_

The detective also belongs to the survivor team:
- `return value`: the current total number of survivors

##### Get detective size
> _/battleroyale api gameProcessManager murdermystery getDetectiveSize_

- `return value`: the current total number of detectives

##### Get murder size
> _/battleroyale api gameProcessManager murdermystery getMurderSize_

- `return value`: the current total number of murders

##### Get standing survivor size
> _/battleroyale api gameProcessManager murdermystery getStandingSurvivorSize_

The detective also belongs to the survivor team:
- `return value`: the current total number of non-eliminated survivors

##### Get standing detective size
> _/battleroyale api gameProcessManager murdermystery getStandingDetectiveSize_

- `return value`: the current total number of non-eliminated detectives

##### Get standing murder size
> _/battleroyale api gameProcessManager murdermystery getStandingMurderSize_

- `return value`: the current total number of non-eliminated murders