# 自定义谁是杀手 | Custom Murder Mystery

[中文](#自定义谁是杀手) | [English](#custom-murder-mystery)

![logo_400x400](pic/logo_400x400.png)

# 自定义谁是杀手

😎[wiki](https://github.com/XColorful/Custom-Murder-Mystery/wiki) | 📄[docs](https://github.com/XColorful/Custom-Murder-Mystery/tree/HEAD/docs)

本模组为[自定义大逃杀](https://github.com/XColorful/BattleRoyale)玩法扩展，[注册谁是杀手游戏进程管理器](https://github.com/XColorful/Custom-Murder-Mystery/wiki/Register-command#注册谁是杀手游戏进程管理器)以切换至谁是杀手游戏逻辑。

```mcfunction
/battleroyale register manager "murdermystery:MMGameProcessManager"
```

---

`该模组需要安装在服务端和客户端`

## 主要特色

###  定制游戏物品

- 复用[区域功能词条](https://github.com/XColorful/BattleRoyale/wiki/Zone-config#区域功能词条)，可在设置角色时自动[刷新背包](https://github.com/XColorful/BattleRoyale/wiki/Zone-simple-function#背包区)
- 复用[物资刷新配置](https://github.com/XColorful/BattleRoyale/wiki/General-loot-config)给予杀手、侦探**任意模组物品**，如[拔刀剑](https://github.com/flammpfeil/SlashBlade_2)、[TaCZ](https://github.com/MCModderAnchor/TACZ)
- [设置阵营物品标签](https://github.com/XColorful/Custom-Murder-Mystery/wiki/Gamerule-config#谁是杀手扩展规则配置)，自动过滤物品拾取
  
### API与数据包扩展

- **事件驱动型数据包**：新增谁是杀手事件钩子，监听角色设置事件、延迟角色击杀事件、倒计时事件
- 新增[谁是杀手API指令](https://github.com/XColorful/Custom-Murder-Mystery/wiki/GameProcessManager-API-command#游戏进程管理器谁是杀手)，高效获取游戏信息，支持通过指令[转换角色阵营](https://github.com/XColorful/Custom-Murder-Mystery/wiki/GameProcessManager-API-command#设置生存者)

# Custom Murder Mystery

😎[wiki](https://github.com/XColorful/Custom-Murder-Mystery/wiki#English) | 📄[docs](https://github.com/XColorful/Custom-Murder-Mystery/tree/HEAD/docs)

This mod functions as a gameplay extension for [Custom BattleRoyale](https://github.com/XColorful/BattleRoyale), [Register MurderMystery GameProcessManager](https://github.com/XColorful/Custom-Murder-Mystery/wiki/Register-command#Register-MurderMystery-GameProcessManager) to switch to Murder Mystery game logic.

```mcfunction
/battleroyale register manager "murdermystery:MMGameProcessManager"
```

---

`This mod needs to be installed on both the server and the client.`

## Main Features

### Custom game items

- Reuse [Zone function entry](https://github.com/XColorful/BattleRoyale/wiki/Zone-config#Zone-function-entry) to automatically [refresh inventory](https://github.com/XColorful/BattleRoyale/wiki/Zone-simple-function#Inventory-zone) when setting roles.
- Reuse [Loot config](https://github.com/XColorful/BattleRoyale/wiki/General-loot-config#English) to give murders and detectives **any modded items**, such as [SlashBlade](https://github.com/flammpfeil/SlashBlade_2) and [TaCZ](https://github.com/MCModderAnchor/TACZ).
- [Set team item tags](https://github.com/XColorful/Custom-Murder-Mystery/wiki/Gamerule-config#MurderMystery-extra-gamerule-config) to automatically filter item pickups.

### API and Datapack addon

- **Event-Driven Datapacks**: Add Murder Mystery event hooks to listen for role setting events, delayed role kill events, and countdown event
- Add [MurderMystery API command](https://github.com/XColorful/Custom-Murder-Mystery/wiki/GameProcessManager-API-command#Game-process-manager-MurderMystery) for efficient retrieval of game information and support for [transform role team](https://github.com/XColorful/Custom-Murder-Mystery/wiki/GameProcessManager-API-command#Set-survivor) via command.