[English](#English)

# 游戏规则配置

## 单个配置

### 扩展规则配置

#### 谁是杀手扩展规则配置

- protocol："murdermystery:murdermystery"或"cmm:murdermystery"均可
- gameStartTick：游戏开始的时间，单位 tick
- countdownSeconds：游戏开始前的倒数，单位秒
- surviveTimeGoal：生存者目标生存时长
- sendGamePlayerNotificationMessage：是否发送游戏玩家相关的通知消息
- filterItemPickup：是否过滤物品拾取
- survivorItemTag：生存者阵营物品标签
- murdererItemTag：杀手阵营物品标签
> 当物品包含`survivorItemTag`或`murdererItemTag`标签即生效，无关值是什么
- survivorDelay：自动[设置生存者](https://github.com/XColorful/Custom-Murder-Mystery/wiki/GameProcessManager-API-command#设置生存者)的延迟
- survivorFuncs：[区域配置](https://github.com/XColorful/BattleRoyale/wiki/Zone-config#单个配置)唯一id列表；成功[设置生存者](https://github.com/XColorful/Custom-Murder-Mystery/wiki/GameProcessManager-API-command#设置生存者)后立即对其执行其[区域功能词条](https://github.com/XColorful/BattleRoyale/wiki/Zone-config#区域功能词条)
- detectiveDelay：自动[设置侦探](https://github.com/XColorful/Custom-Murder-Mystery/wiki/GameProcessManager-API-command#设置侦探)的延迟
- detectiveFuncs：[区域配置](https://github.com/XColorful/BattleRoyale/wiki/Zone-config#单个配置)唯一id列表；成功[设置侦探](https://github.com/XColorful/Custom-Murder-Mystery/wiki/GameProcessManager-API-command#设置侦探)后立即对其执行其[区域功能词条](https://github.com/XColorful/BattleRoyale/wiki/Zone-config#区域功能词条)
- murdererDelay：自动[设置杀手](https://github.com/XColorful/Custom-Murder-Mystery/wiki/GameProcessManager-API-command#设置杀手)的延迟
- murdererFuncs：[区域配置](https://github.com/XColorful/BattleRoyale/wiki/Zone-config#单个配置)唯一id列表；成功[设置杀手](https://github.com/XColorful/Custom-Murder-Mystery/wiki/GameProcessManager-API-command#设置杀手)后立即对其执行其[区域功能词条](https://github.com/XColorful/BattleRoyale/wiki/Zone-config#区域功能词条)
- apiFunctionRegister：函数标识符，在游戏开始时自动尝试执行一次
> 等价于在[注册词条](https://github.com/XColorful/BattleRoyale/wiki/Function-config#注册词条)添加[自定义事件](https://github.com/XColorful/BattleRoyale/blob/HEAD/docs/api/event/CustomEventType.md)`GAME_START_FINISH_EVENT`
- apiFunctionUnregister：函数标识符，在游戏结束时自动尝试执行一次
> 等价于在[注册词条](https://github.com/XColorful/BattleRoyale/wiki/Function-config#注册词条)添加[自定义事件](https://github.com/XColorful/BattleRoyale/blob/HEAD/docs/api/event/CustomEventType.md)`GAME_STOP_FINISH_EVENT`
```json
"extraRule": {
	"protocol": "murdermystery:murdermystery",
	"jsonTag": {
		"gameStartTick": 300,
		"countdownSeconds": 10,
		"surviveTimeGoal": 12000,
		"sendGamePlayerNotificationMessage": false,
		"filterItemPickup": true,
		"survivorItemTag": "survivorItem",
		"murdererItemTag": "murdererItem",
		"survivorDelay": 0,
		"survivorFuncs": [],
		"detectiveDelay": 200,
		"detectiveFuncs": [],
		"murdererDelay": 200,
		"murdererFuncs": [],
		"apiFunctionRegister": "murdermystery:register",
		"apiFunctionUnregister": "murdermystery:unregister"
	}
}
```

# English

## Single gamerule config

### Extra gamerule config

#### MurderMystery extra gamerule config

- protocol: "murdermystery:murdermystery" or "cmm:murdermystery"
- gameStartTick: the time when the game starts, in ticks
- countdownSeconds: the countdown before the game starts, in seconds
- surviveTimeGoal: the target survival duration for survivors
- sendGamePlayerNotificationMessage: whether to send game player related notification messages
- filterItemPickup: whether to filter item pickup
- survivorItemTag: item tag for the survivor team
- murdererItemTag: item tag for the murderer team
> It takes effect when an item contains the `survivorItemTag` or `murdererItemTag` tag, regardless of the value.
- survivorDelay: the delay for automatically [Set survivor](https://github.com/XColorful/Custom-Murder-Mystery/wiki/GameProcessManager-API-command#Set-survivor)
- survivorFuncs: A list of [Zone config](https://github.com/XColorful/BattleRoyale/wiki/Zone-config#Single-zone-config) IDs; the [Zone function entry](https://github.com/XColorful/BattleRoyale/wiki/Zone-config#Zone-function-entry) is executed immediately for players upon successful [Set survivor](https://github.com/XColorful/Custom-Murder-Mystery/wiki/GameProcessManager-API-command#Set-survivor)
- detectiveDelay: the delay for automatically [Set detective](https://github.com/XColorful/Custom-Murder-Mystery/wiki/GameProcessManager-API-command#Set-detective)
- detectiveFuncs: A list of [Zone config](https://github.com/XColorful/BattleRoyale/wiki/Zone-config#Single-zone-config) IDs; the [Zone function entry](https://github.com/XColorful/BattleRoyale/wiki/Zone-config#Zone-function-entry) is executed immediately for players upon successful [Set detective](https://github.com/XColorful/Custom-Murder-Mystery/wiki/GameProcessManager-API-command#Set-detective)
- murdererDelay: the delay for automatically [Set murderer](https://github.com/XColorful/Custom-Murder-Mystery/wiki/GameProcessManager-API-command#Set-murder)
- murdererFuncs: A list of [Zone config](https://github.com/XColorful/BattleRoyale/wiki/Zone-config#Single-zone-config) IDs; the [Zone function entry](https://github.com/XColorful/BattleRoyale/wiki/Zone-config#Zone-function-entry) is executed immediately for players upon successful [Set murder](https://github.com/XColorful/Custom-Murder-Mystery/wiki/GameProcessManager-API-command#Set-murder)
- apiFunctionRegister: function identifier, automatically attempts to execute once when the game starts
> It is equivalent to add [CustomEventType](https://github.com/XColorful/BattleRoyale/blob/HEAD/docs/api/event/CustomEventType.md) `GAME_START_FINISH_EVENT` to [Register entry](https://github.com/XColorful/BattleRoyale/wiki/Function-config#Register-entry)
- apiFunctionUnregister: function identifier, automatically attempts to execute once when the game stops
> It is equivalent to add [CustomEventType](https://github.com/XColorful/BattleRoyale/blob/HEAD/docs/api/event/CustomEventType.md) `GAME_STOP_FINISH_EVENT` to [Register entry](https://github.com/XColorful/BattleRoyale/wiki/Function-config#Register-entry)
```json
"extraRule": {
	"protocol": "murdermystery:murdermystery",
	"jsonTag": {
		"gameStartTick": 300,
		"countdownSeconds": 10,
		"surviveTimeGoal": 12000,
		"sendGamePlayerNotificationMessage": false,
		"filterItemPickup": true,
		"survivorItemTag": "survivorItem",
		"murdererItemTag": "murdererItem",
		"survivorDelay": 0,
		"survivorFuncs": [],
		"detectiveDelay": 200,
		"detectiveFuncs": [],
		"murdererDelay": 200,
		"murdererFuncs": [],
		"apiFunctionRegister": "murdermystery:register",
		"apiFunctionUnregister": "murdermystery:unregister"
	}
}
```